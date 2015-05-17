package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.INeighborNotify;
import mariculture.api.core.ISpecialPickblock;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFunctional;
import mariculture.core.events.MaricultureEvents;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.helpers.cofh.CoFhItemHelper;
import mariculture.core.items.ItemHammer;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.TileAirPump;
import mariculture.core.tile.TileAnvil;
import mariculture.core.tile.TileAutohammer;
import mariculture.core.tile.TileBlockCaster;
import mariculture.core.tile.TileCooling;
import mariculture.core.tile.TileIngotCaster;
import mariculture.core.tile.TileNuggetCaster;
import mariculture.factory.tile.TileGeyser;
import mariculture.fishery.tile.TileFeeder;
import mariculture.lib.helpers.DirectionHelper;
import mariculture.lib.helpers.ItemHelper;
import maritech.tile.TileRotor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRenderedMachine extends BlockFunctional {
    public BlockRenderedMachine() {
        super(Material.piston);
    }

    @Override
    public String getToolType(int meta) {
        String tooltype = meta == MachineRenderedMeta.FISH_FEEDER ? null : "pickaxe";
        return MaricultureEvents.getToolType(this, meta, tooltype);
    }

    @Override
    public int getToolLevel(int meta) {
        int level = 0;
        switch (meta) {
            case MachineRenderedMeta.AIR_PUMP:
            case MachineRenderedMeta.GEYSER:
                level = 1;
                break;
        }

        return MaricultureEvents.getToolLevel(this, meta, level);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        float hardness = 5F;
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case MachineRenderedMeta.AIR_PUMP:
                hardness = 4F;
                break;
            case MachineRenderedMeta.FISH_FEEDER:
                hardness = 0.5F;
                break;
            case MachineRenderedMeta.GEYSER:
                hardness = 0.85F;
                break;
            case MachineRenderedMeta.AUTO_HAMMER:
            case MachineRenderedMeta.INGOT_CASTER:
            case MachineRenderedMeta.BLOCK_CASTER:
            case MachineRenderedMeta.NUGGET_CASTER:
                hardness = 1.5F;
                break;
            case MachineRenderedMeta.ANVIL:
                hardness = 25F;
                break;
        }

        return MaricultureEvents.getBlockHardness(this, meta, hardness);
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        return world.getBlockMetadata(x, y, z) == MachineRenderedMeta.FISH_FEEDER ? 100F : super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return world.getBlockMetadata(x, y, z) == MachineRenderedMeta.FISH_FEEDER ? false : super.canEntityDestroy(world, x, y, z, entity);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.RENDER_ALL;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            int meta = world.getBlockMetadata(x, y, z);
            float damage = 0F;
            switch (meta) {
                case MachineRenderedMeta.ROTOR_COPPER:
                    damage = 1.25F;
                    break;
                case MachineRenderedMeta.ROTOR_ALUMINUM:
                    damage = 2F;
                    break;
                case MachineRenderedMeta.ROTOR_TITANIUM:
                    damage = 5F;
                    break;
                default:
                    damage = 0F;
            }

            if (damage > 0F) {
                TileRotor rotor = (TileRotor) world.getTileEntity(x, y, z);
                if (rotor.isAnimating > 0) {
                    entity.attackEntityFrom(MaricultureDamage.turbine, damage);
                }
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileGeyser) {
                ((TileGeyser) tile).setFacing(ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, entity)));
            } else if (tile instanceof TileAirPump) {
                ((TileAirPump) tile).setFacing(DirectionHelper.getFacingFromEntity(entity));
            } else if (tile instanceof TileAnvil) {
                ((TileAnvil) tile).setFacing(DirectionHelper.getFacingFromEntity(entity));
            }
        }

        MaricultureEvents.onBlockPlaced(stack, this, world, x, y, z, entity, tile);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null || player.isSneaking() && !world.isRemote) return false;

        //Add 100RF to the air pump on right click
        if (tile instanceof TileAirPump) {
            TileAirPump pump = (TileAirPump) tile;
            if (!world.isRemote) {
                if (!pump.isAnimating) { //Only add energy if not animating
                    pump.receiveEnergy(ForgeDirection.UP, 100, false); //Add 100 RF when right clicking
                }
            }

            pump.isAnimating = true;

            return true;
        }

        if (player.isSneaking()) return false;
        if (tile instanceof TileFeeder) {
            ((TileFeeder) tile).updateTankSize();
            player.openGui(Mariculture.instance, -1, world, x, y, z);
            return true;
        }

        //Update the anvil inventory
        if (tile instanceof TileAnvil) {
            if (PlayerHelper.isFake(player)) return false;
            TileAnvil anvil = (TileAnvil) tile;
            if (anvil.getStackInSlot(0) != null) {
                if (!world.isRemote) {
                    PacketHandler.syncInventory(anvil, anvil.getInventory());
                }

                ItemHelper.addToPlayerInventory(player, world, x, y + 1, z, anvil.getStackInSlot(0));
                anvil.setInventorySlotContents(0, null);
            } else if (player.getCurrentEquippedItem() != null) {
                ItemStack stack = player.getCurrentEquippedItem().copy();
                stack.stackSize = 1;
                anvil.setInventorySlotContents(0, stack);
                if (!player.capabilities.isCreativeMode) {
                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
                }
            }

            return true;
        }

        if (tile instanceof TileAutohammer && side > 1) {
            int slot = side - 2;
            TileAutohammer hammer = (TileAutohammer) tile;
            if (hammer.getStackInSlot(slot) != null) {
                if (!world.isRemote) {
                    PacketHandler.syncInventory(hammer, hammer.getInventory());
                }

                ItemHelper.addToPlayerInventory(player, world, x, y + 1, z, hammer.getStackInSlot(slot));
                hammer.setInventorySlotContents(slot, null);
            } else if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemHammer) {
                ItemStack stack = player.getCurrentEquippedItem().copy();
                hammer.setInventorySlotContents(slot, stack);
                if (!player.capabilities.isCreativeMode) {
                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
                }
            }

            return true;
        }

        //Update the ingot caster inventory
        if (tile instanceof TileCooling) {
            if (!world.isRemote) {
                TileCooling caster = (TileCooling) tile;
                for (int i = 0; i < caster.getSizeInventory(); i++)
                    if (caster.getStackInSlot(i) != null) {
                        ItemHelper.spawnItem(world, x, y + 1, z, caster.getStackInSlot(i));
                        caster.setInventorySlotContents(i, null);
                        caster.markDirty();
                    }
            }

            return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
        }

        //Fill the geyser
        if (tile instanceof TileGeyser) return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);

        //Return to doing the rest
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileAnvil && CoFhItemHelper.isPlayerHoldingItem(Core.hammer, player)) {
            if (player.getDisplayName().equals("[CoFH]")) return;
            if (player instanceof FakePlayer) return;
            ItemStack hammer = player.getCurrentEquippedItem();
            if (((TileAnvil) tile).workItem(player, hammer) > 0) {
                if (hammer.attemptDamageItem(1, world.rand)) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                }
            }
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
        int meta = block.getBlockMetadata(x, y, z);
        ForgeDirection facing;

        switch (meta) {
            case MachineRenderedMeta.AIR_PUMP:
                setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.9F, 0.8F);
                break;
            case MachineRenderedMeta.GEYSER:
                TileGeyser geyser = (TileGeyser) block.getTileEntity(x, y, z);
                if (geyser.orientation == ForgeDirection.UP) {
                    setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.25F, 0.9F);
                }
                if (geyser.orientation == ForgeDirection.DOWN) {
                    setBlockBounds(0.1F, 0.75F, 0.1F, 0.9F, 1.0F, 0.9F);
                }
                if (geyser.orientation == ForgeDirection.EAST) {
                    setBlockBounds(0.0F, 0.1F, 0.1F, 0.25F, 0.9F, 0.9F);
                }
                if (geyser.orientation == ForgeDirection.WEST) {
                    setBlockBounds(0.75F, 0.1F, 0.1F, 1F, 0.9F, 0.9F);
                }
                if (geyser.orientation == ForgeDirection.SOUTH) {
                    setBlockBounds(0.1F, 0.1F, 0.0F, 0.9F, 0.9F, 0.25F);
                }
                if (geyser.orientation == ForgeDirection.NORTH) {
                    setBlockBounds(0.1F, 0.1F, 0.75F, 0.9F, 0.9F, 1.0F);
                }
                break;
            case MachineRenderedMeta.ANVIL:
                TileAnvil anvil = (TileAnvil) block.getTileEntity(x, y, z);
                if (anvil.getFacing() == ForgeDirection.EAST) {
                    setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
                }
                if (anvil.getFacing() == ForgeDirection.NORTH) {
                    setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
                }
                if (anvil.getFacing() == ForgeDirection.WEST) {
                    setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
                }
                if (anvil.getFacing() == ForgeDirection.SOUTH) {
                    setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
                }
                break;
            case MachineRenderedMeta.NUGGET_CASTER:
                setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
                break;
            default:
                setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == MachineRenderedMeta.GEYSER || meta == MachineRenderedMeta.ANVIL || meta == MachineRenderedMeta.NUGGET_CASTER) {
            return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
        }

        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public boolean doesDrop(int meta) {
        if (meta >= MachineRenderedMeta.ROTOR_COPPER && meta <= MachineRenderedMeta.ROTOR_TITANIUM) return false;
        return meta != MachineRenderedMeta.FLUDD_STAND;
    }

    @Override
    public boolean destroyBlock(World world, int x, int y, int z) {
        if (MaricultureEvents.onBlockBroken(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z), world, x, y, z)) {
            return true;
        } else return super.destroyBlock(world, x, y, z);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof INeighborNotify) {
            ((INeighborNotify) tile).recheck();
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof ISpecialPickblock) {
            return ((ISpecialPickblock) tile).getDrop();
        } else return super.getPickBlock(target, world, x, y, z);
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        TileEntity tile = null;
        switch (meta) {
            case MachineRenderedMeta.AIR_PUMP:
                return new TileAirPump();
            case MachineRenderedMeta.FISH_FEEDER:
                return new TileFeeder();
            case MachineRenderedMeta.GEYSER:
                return new TileGeyser();
            case MachineRenderedMeta.INGOT_CASTER:
                return new TileIngotCaster();
            case MachineRenderedMeta.BLOCK_CASTER:
                return new TileBlockCaster();
            case MachineRenderedMeta.NUGGET_CASTER:
                return new TileNuggetCaster();
            case MachineRenderedMeta.AUTO_HAMMER:
                return new TileAutohammer();
            case MachineRenderedMeta.ANVIL:
                return new TileAnvil();
        }

        return MaricultureEvents.getTileEntity(this, meta, tile);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        IIcon icon = null;
        if (meta == MachineRenderedMeta.GEYSER) return Blocks.hopper.getIcon(0, 0);
        else if (meta == MachineRenderedMeta.INGOT_CASTER) return super.getIcon(side, meta);
        else if (meta >= MachineRenderedMeta.ANVIL) return super.getIcon(side, MachineRenderedMeta.INGOT_CASTER);
        else icon = icons[meta];

        return MaricultureEvents.getInventoryIcon(this, meta, side, icon);
    }

    @Override
    public boolean isActive(int meta) {
        boolean isActive = false;
        switch (meta) {
            case MachineRenderedMeta.FISH_FEEDER:
                return Modules.isActive(Modules.fishery);
            case MachineRenderedMeta.GEYSER:
                return Modules.isActive(Modules.factory);
            case MachineRenderedMeta.AIR_PUMP:
            case MachineRenderedMeta.ANVIL:
            case MachineRenderedMeta.AUTO_HAMMER:
            case MachineRenderedMeta.BLOCK_CASTER:
            case MachineRenderedMeta.INGOT_CASTER:
            case MachineRenderedMeta.NUGGET_CASTER:
                return true;
        }

        return MaricultureEvents.isActive(this, meta, isActive);
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        boolean isValid = meta == MachineRenderedMeta.FISH_FEEDER ? tab == MaricultureTab.tabFishery : tab == MaricultureTab.tabFactory;
        return MaricultureEvents.isValidTab(this, tab, meta, isValid);
    }

    @Override
    public int getMetaCount() {
        return MachineRenderedMeta.COUNT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        String name = prefix != null ? prefix : "";
        icons = new IIcon[getMetaCount() - 2];
        for (int i = 0; i < icons.length; i++) {
            if ((i < MachineRenderedMeta.ROTOR_COPPER || i == MachineRenderedMeta.INGOT_CASTER) && i != MachineRenderedMeta.AUTO_HAMMER) {
                icons[i] = iconRegister.registerIcon(MaricultureEvents.getMod(this, i, "mariculture") + ":" + name + getName(i));
            }
        }
    }
}
