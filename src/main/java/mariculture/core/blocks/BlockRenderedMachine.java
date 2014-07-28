package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFunctional;
import mariculture.core.helpers.DirectionHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.TileAirPump;
import mariculture.core.tile.TileAirPump.Type;
import mariculture.core.tile.TileAnvil;
import mariculture.core.tile.TileBlockCaster;
import mariculture.core.tile.TileCooling;
import mariculture.core.tile.TileIngotCaster;
import mariculture.core.tile.TileNuggetCaster;
import mariculture.core.util.Fluids;
import mariculture.core.util.Rand;
import mariculture.factory.Factory;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.tile.TileFLUDDStand;
import mariculture.factory.tile.TileGeyser;
import mariculture.fishery.tile.TileFeeder;
import mariculture.fishery.tile.TileHatchery;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
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
        return meta == MachineRenderedMeta.FISH_FEEDER || meta == MachineRenderedMeta.HATCHERY ? null : "pickaxe";
    }

    @Override
    public int getToolLevel(int meta) {
        switch (meta) {
            case MachineRenderedMeta.AIR_PUMP:
                return 1;
            case MachineRenderedMeta.GEYSER:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case MachineRenderedMeta.AIR_PUMP:
                return 4F;
            case MachineRenderedMeta.FISH_FEEDER:
                return 0.5F;
            case MachineRenderedMeta.FLUDD_STAND:
                return 1F;
            case MachineRenderedMeta.GEYSER:
                return 0.85F;
            case MachineRenderedMeta.INGOT_CASTER:
                return 1.5F;
            case MachineRenderedMeta.BLOCK_CASTER:
                return 1.5F;
            case MachineRenderedMeta.NUGGET_CASTER:
                return 1.5F;
            case MachineRenderedMeta.ANVIL:
                return 25F;
            case MachineRenderedMeta.HATCHERY:
                return 1.5F;
            default:
                return 1.5F;
        }
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileFLUDDStand) {
                TileFLUDDStand fludd = (TileFLUDDStand) tile;
                fludd.setFacing(ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, entity)));

                int water = 0;
                if (stack.hasTagCompound()) {
                    water = stack.stackTagCompound.getInteger("water");
                }

                fludd.tank.setCapacity(ItemArmorFLUDD.STORAGE);
                fludd.tank.setFluidID(Fluids.getFluidID("hp_water"));
                fludd.tank.setFluidAmount(water);
                PacketHandler.updateRender(fludd);
            }

            if (tile instanceof TileGeyser) {
                ((TileGeyser) tile).setFacing(ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, entity)));
            }
            if (tile instanceof TileAirPump) {
                ((TileAirPump) tile).setFacing(DirectionHelper.getFacingFromEntity(entity));
            }
            if (tile instanceof TileAnvil) {
                ((TileAnvil) tile).setFacing(DirectionHelper.getFacingFromEntity(entity));
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null || player.isSneaking() && !world.isRemote) return false;

        //Activate the air pump on right click
        if (tile instanceof TileAirPump) {
            TileAirPump pump = (TileAirPump) tile;
            if (pump.animate == false) {
                if (Modules.isActive(Modules.diving)) if (pump.updateAirArea(Type.CHECK)) {
                    if (!world.isRemote) {
                        pump.doPoweredPump(false);
                    }
                    pump.animate = true;
                }

                if (pump.suckUpGas(1024)) {
                    pump.animate = true;
                }
            }

            if (world.isRemote && player.isSneaking()) {
                ((TileAirPump) tile).updateAirArea(Type.DISPLAY);
            }
            return true;
        }

        if (player.isSneaking()) return false;
        if (tile instanceof TileFeeder) {
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

                SpawnItemHelper.addToPlayerInventory(player, world, x, y + 1, z, anvil.getStackInSlot(0));
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

        //Update the ingot caster inventory
        if (tile instanceof TileCooling) {
            if (!world.isRemote) {
                TileCooling caster = (TileCooling) tile;
                for (int i = 0; i < caster.getSizeInventory(); i++)
                    if (caster.getStackInSlot(i) != null) {
                        SpawnItemHelper.spawnItem(world, x, y + 1, z, caster.getStackInSlot(i));
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
        if (tile instanceof TileAnvil && ItemHelper.isPlayerHoldingItem(Core.hammer, player)) {
            if (player.getDisplayName().equals("[CoFH]")) return;
            if (player instanceof FakePlayer) return;
            ItemStack hammer = player.getCurrentEquippedItem();
            if (((TileAnvil) tile).workItem(player, hammer)) if (hammer.attemptDamageItem(1, Rand.rand)) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
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
        if (meta == MachineRenderedMeta.FLUDD_STAND) return false;
        return true;
    }

    @Override
    public boolean onBlockDropped(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileFLUDDStand) {
            TileFLUDDStand stand = (TileFLUDDStand) tile;
            ItemStack fludd = new ItemStack(Factory.fludd);
            fludd.setTagCompound(new NBTTagCompound());
            fludd.stackTagCompound.setInteger("water", stand.tank.getFluidAmount());
            SpawnItemHelper.spawnItem(world, x, y, z, fludd);

            return world.setBlockToAir(x, y, z);
        }

        return super.onBlockDropped(world, x, y, z);
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        switch (meta) {
            case MachineRenderedMeta.AIR_PUMP:
                return new TileAirPump();
            case MachineRenderedMeta.FISH_FEEDER:
                return new TileFeeder();
            case MachineRenderedMeta.FLUDD_STAND:
                return new TileFLUDDStand();
            case MachineRenderedMeta.GEYSER:
                return new TileGeyser();
            case MachineRenderedMeta.INGOT_CASTER:
                return new TileIngotCaster();
            case MachineRenderedMeta.BLOCK_CASTER:
                return new TileBlockCaster();
            case MachineRenderedMeta.NUGGET_CASTER:
                return new TileNuggetCaster();
            case MachineRenderedMeta.HATCHERY:
                return new TileHatchery();
            default:
                return new TileAnvil();
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == MachineRenderedMeta.GEYSER) return Blocks.hopper.getIcon(0, 0);
        if (meta == MachineRenderedMeta.INGOT_CASTER) return super.getIcon(side, meta);
        if (meta >= MachineRenderedMeta.ANVIL) return super.getIcon(side, MachineRenderedMeta.INGOT_CASTER);

        return icons[meta];
    }

    @Override
    public boolean isActive(int meta) {
        switch (meta) {
            case MachineRenderedMeta.FISH_FEEDER:
                return Modules.isActive(Modules.fishery);
            case MachineRenderedMeta.GEYSER:
                return Modules.isActive(Modules.factory);
            case MachineRenderedMeta.FLUDD_STAND:
                return false;
            case MachineRenderedMeta.HATCHERY:
                return Modules.isActive(Modules.fishery);
            default:
                return true;
        }
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        switch (meta) {
            case MachineRenderedMeta.FISH_FEEDER:
                return tab == MaricultureTab.tabFishery;
            case MachineRenderedMeta.HATCHERY:
                return tab == MaricultureTab.tabFishery;
            default:
                return tab == MaricultureTab.tabFactory;
        }
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
            if (i != MachineRenderedMeta.ANVIL && i != MachineRenderedMeta.BLOCK_CASTER && i < MachineRenderedMeta.REMOVED_1 && i > MachineRenderedMeta.REMOVED_3) {
                icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + name + getName(i));
            }
        }
    }
}
