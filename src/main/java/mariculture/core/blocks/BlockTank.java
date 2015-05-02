package mariculture.core.blocks;

import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockConnected;
import mariculture.core.handlers.FluidDicHandler;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.NBTHelper;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import mariculture.core.network.PacketFishTankSync;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.TileTankAluminum;
import mariculture.core.tile.TileTankBlock;
import mariculture.core.tile.TileTankTitanium;
import mariculture.core.tile.TileVoidBottle;
import mariculture.factory.tile.TileDictionaryFluid;
import mariculture.factory.tile.TileGasTank;
import mariculture.fishery.tile.TileFishTank;
import mariculture.fishery.tile.TileHatchery;
import mariculture.lib.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTank extends BlockConnected {
    private static IIcon[] fishTank = new IIcon[47];

    public BlockTank() {
        super(Material.piston);
    }

    @Override
    public String getToolType(int meta) {
        return meta == TankMeta.DIC ? "axe" : null;
    }

    @Override
    public int getToolLevel(int meta) {
        return meta == TankMeta.DIC ? 1 : 0;
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case TankMeta.BOTTLE:
                return 0.1F;
            case TankMeta.TANK:
                return 0.5F;
            case TankMeta.TANK_ALUMINUM:
                return 0.75F;
            case TankMeta.TANK_TITANIUM:
                return 1.5F;
            case TankMeta.TANK_GAS:
                return 3F;
            case TankMeta.FISH:
                return 1.0F;
            case TankMeta.DIC:
            case TankMeta.HATCHERY:
                return 1.5F;
            default:
                return 1.0F;
        }
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.RENDER_ALL;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null) return false;

        if (tile instanceof TileFishTank) if (player.isSneaking()) return false;
        else {
            player.openGui(Mariculture.instance, -1, world, x, y, z);
            return true;
        }

        if (tile instanceof TileDictionaryFluid) if (player.isSneaking()) return false;
        else {
            TileDictionaryFluid dic = (TileDictionaryFluid) tile;
            String next = FluidDicHandler.getNext(dic.getFluid());
            Fluid fluid = FluidRegistry.getFluid(next);
            if (fluid != null) {
                dic.tank.setFluid(new FluidStack(fluid, 1));
                PacketHandler.syncFluids(dic, dic.tank.getFluid());
            }
        }

        if (tile instanceof TileHatchery) {
            TileHatchery hatchery = (TileHatchery) tile;
            hatchery.updateSurrounding(); //Update info when stuff is 

            if (hatchery.getStackInSlot(0) != null && player.isSneaking()) {
                if (!world.isRemote) {
                    PacketHandler.syncInventory(hatchery, hatchery.getInventory());
                }

                ItemHelper.addToPlayerInventory(player, world, x, y + 1, z, hatchery.getStackInSlot(0));
                hatchery.setInventorySlotContents(0, null);
            } else if (player.getCurrentEquippedItem() != null && hatchery.getStackInSlot(0) == null) {
                ItemStack stack = player.getCurrentEquippedItem().copy();
                stack.stackSize = 1;
                hatchery.setInventorySlotContents(0, stack);
                if (!player.capabilities.isCreativeMode) {
                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
                }
            } else {
                for (int i = 1; i < hatchery.getInventory().length; i++) {
                    if (hatchery.getStackInSlot(i) != null) {
                        if (!world.isRemote) {
                            PacketHandler.syncInventory(hatchery, hatchery.getInventory());
                        }

                        ItemHelper.addToPlayerInventory(player, world, x, y + 1, z, hatchery.getStackInSlot(i));
                        hatchery.setInventorySlotContents(i, null);
                    }
                }
            }

            return true;
        }

        return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileHatchery) {
            TileHatchery hatchery = (TileHatchery) tile;
            hatchery.updateSurrounding(); //Update info when stuff is 
        }
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        switch (meta) {
            case TankMeta.BOTTLE:
                return new TileVoidBottle();
            case TankMeta.TANK:
                return new TileTankBlock();
            case TankMeta.FISH:
                return new TileFishTank();
            case TankMeta.DIC:
                return new TileDictionaryFluid();
            case TankMeta.HATCHERY:
                return new TileHatchery();
            case TankMeta.TANK_ALUMINUM:
                return new TileTankAluminum();
            case TankMeta.TANK_TITANIUM:
                return new TileTankTitanium();
            case TankMeta.TANK_GAS:
                return new TileGasTank();
            default:
                return null;
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileTankBlock) {
            TileTankBlock tank = (TileTankBlock) tile;
            FluidStack fluid = tank.getFluid();
            if (fluid != null) return fluid.getFluid().getLuminosity();
        }

        return 0;
    }

    @Override
    public int getMetaCount() {
        return TankMeta.COUNT;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
        if (block.getBlockMetadata(x, y, z) != TankMeta.BOTTLE) {
            setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        } else {
            setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == TankMeta.BOTTLE ? null : super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int j) {
        switch (meta) {
            case TankMeta.TANK_ALUMINUM:
            case TankMeta.TANK_GAS:
            case TankMeta.TANK_TITANIUM:
            case TankMeta.TANK:
            case TankMeta.FISH:
                return null;
            case TankMeta.BOTTLE:
                return Core.bottles;
            default:
                return super.getItemDropped(meta, random, j);
        }
    }

    @Override
    public int damageDropped(int meta) {
        switch (meta) {
            case TankMeta.BOTTLE:
                return BottleMeta.VOID;
            default:
                return meta;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int facing = BlockPistonBase.determineOrientation(world, x, y, z, entity);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null) return;
        if (stack.hasTagCompound()) {
            if (tile instanceof TileTankBlock) {
                TileTankBlock tank = (TileTankBlock) tile;
                tank.setFluid(FluidStack.loadFluidStackFromNBT(stack.stackTagCompound));
                PacketHandler.syncFluids(tank, tank.getFluid());
            } else if (tile instanceof TileFishTank) {
                TileFishTank tank = (TileFishTank) tile;
                tank.orientation = ForgeDirection.getOrientation(facing);
                NBTTagList tagList = stack.getTagCompound().getTagList("Inventory", 10);
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = tagList.getCompoundTagAt(i);
                    byte slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < tank.getInventory().length) {
                        tank.getInventory()[slot] = NBTHelper.getItemStackFromNBT(tag);
                    }
                }
                
                PacketHandler.sendAround(new PacketFishTankSync(tank.getStackInSlot(0), tank.getStackInSlot(1), tank.getStackInSlot(2), tank.xCoord, tank.yCoord, tank.zCoord, tank.orientation), tile);
            }
        }
    }

    //Change back to remove block block by player instead of getDrops for everything
    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileTankBlock && !player.capabilities.isCreativeMode) {
            ItemStack drop = new ItemStack(Core.tanks, 1, world.getBlockMetadata(x, y, z));
            TileTankBlock tank = (TileTankBlock) tile;
            if (tank != null && tank.getFluid() != null) {
                if (!drop.hasTagCompound()) {
                    drop.setTagCompound(new NBTTagCompound());
                }

                tank.getFluid().writeToNBT(drop.stackTagCompound);
            }

            ItemHelper.spawnItem(world, x, y, z, drop);
            return world.setBlockToAir(x, y, z);
        } else if (tile instanceof TileFishTank && !player.capabilities.isCreativeMode) {
            ItemStack drop = new ItemStack(Core.tanks, 1, world.getBlockMetadata(x, y, z));
            if (!drop.hasTagCompound()) {
                drop.setTagCompound(new NBTTagCompound());
            }

            TileFishTank tank = (TileFishTank) tile;
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < tank.getInventory().length; i++) {
                ItemStack stack = tank.getInventory()[i];
                if (stack != null) {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("Slot", (byte) i);
                    NBTHelper.writeItemStackToNBT(tag, stack);
                    itemList.appendTag(tag);
                }
            }

            drop.getTagCompound().setTag("Inventory", itemList);
            ItemHelper.spawnItem(world, x, y, z, drop);
            return world.setBlockToAir(x, y, z);
        } else return world.setBlockToAir(x, y, z);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == TankMeta.BOTTLE) {
            for (int j = 0; j < 2; ++j) {
                float f = rand.nextFloat() - rand.nextFloat();
                float f1 = rand.nextFloat() - rand.nextFloat();
                float f2 = rand.nextFloat() - rand.nextFloat();
                world.spawnParticle("magicCrit", x + 0.5D + f, y + 0.5D + f1, z + 0.5D + f2, 0, 0, 0);
                world.spawnParticle("witchMagic", x + 0.5D, y + 0.5D, z + 0.5D, 0, 0, 0);
            }
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        ItemStack drop = new ItemStack(Core.tanks, 1, meta);
        if (meta == TankMeta.TANK || meta == TankMeta.TANK_ALUMINUM || meta == TankMeta.TANK_TITANIUM || meta == TankMeta.TANK_GAS) {
            TileTankBlock tank = (TileTankBlock) world.getTileEntity(x, y, z);
            if (tank != null && tank.getFluid() != null) {
                if (!drop.hasTagCompound()) {
                    drop.setTagCompound(new NBTTagCompound());
                }

                tank.getFluid().writeToNBT(drop.stackTagCompound);
            }
        } else if (meta == TankMeta.BOTTLE) {
            drop = new ItemStack(Core.bottles, 1, BottleMeta.VOID);
        } else if (meta == TankMeta.FISH) {
            if (!drop.hasTagCompound()) {
                drop.setTagCompound(new NBTTagCompound());
            }

            TileFishTank tank = (TileFishTank) world.getTileEntity(x, y, z);
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < tank.getInventory().length; i++) {
                ItemStack stack = tank.getInventory()[i];
                if (stack != null) {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("Slot", (byte) i);
                    NBTHelper.writeItemStackToNBT(tag, stack);
                    itemList.appendTag(tag);
                }
            }

            drop.getTagCompound().setTag("Inventory", itemList);
        }

        return drop;
    }

    @Override
    public boolean isActive(int meta) {
        switch (meta) {
            case TankMeta.BOTTLE:
                return false;
            case TankMeta.FISH:
            case TankMeta.HATCHERY:
                return Modules.isActive(Modules.fishery);
            case TankMeta.DIC:
                return false;
            default:
                return true;
        }
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        switch (meta) {
            case TankMeta.HATCHERY:
            case TankMeta.FISH:
                return tab == MaricultureTab.tabFishery;
            default:
                return tab == MaricultureTab.tabFactory;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        icons = new IIcon[getMetaCount()];

        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(i) + "Tank");
        }

        registerConnectedTextures(iconRegister);
    }

    @Override
    public IIcon[] getTexture(int meta) {
        return meta == TankMeta.FISH ? fishTank : null;
    }

    @Override
    public void registerConnectedTextures(IIconRegister iconRegister) {
        for (int i = 0; i < 47; i++) {
            fishTank[i] = iconRegister.registerIcon(Mariculture.modid + ":fishTank/" + (i + 1));
        }
    }
}
