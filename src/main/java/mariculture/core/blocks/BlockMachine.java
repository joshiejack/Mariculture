package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFunctional;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.WoodMeta;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketSponge;
import mariculture.core.tile.TileBookshelf;
import mariculture.factory.tile.TileDictionaryItem;
import mariculture.factory.tile.TileFishSorter;
import mariculture.factory.tile.TileGenerator;
import mariculture.factory.tile.TileSawmill;
import mariculture.factory.tile.TileSluice;
import mariculture.factory.tile.TileSluiceAdvanced;
import mariculture.factory.tile.TileSponge;
import mariculture.factory.tile.TileUnpacker;
import mariculture.fishery.tile.TileAutofisher;
import mariculture.fishery.tile.TileHatchery;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends BlockFunctional {
    private IIcon[] fishSorter;
    private IIcon sluiceAdvanced;
    private IIcon sluiceAdvancedBack;
    private IIcon sluiceAdvancedUp;
    private IIcon sluiceAdvancedDown;
    private IIcon sluiceBack;
    private IIcon sluiceUp;
    private IIcon sluiceDown;
    private IIcon unpacker;

    public BlockMachine() {
        super(Material.piston);
    }

    @Override
    public String getToolType(int meta) {
        switch (meta) {
            case MachineMeta.SLUICE:
                return "pickaxe";
            case MachineMeta.SPONGE:
                return "pickaxe";
            case MachineMeta.SLUICE_ADVANCED:
                return "pickaxe";
            case MachineMeta.GENERATOR:
                return "generator";
            default:
                return "axe";
        }
    }

    @Override
    public int getToolLevel(int meta) {
        switch (meta) {
            case MachineMeta.SLUICE:
                return 1;
            case MachineMeta.SPONGE:
                return 1;
            case MachineMeta.SLUICE_ADVANCED:
                return 2;
            case MachineMeta.GENERATOR:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case MachineMeta.BOOKSHELF:
                return 1F;
            case MachineMeta.DICTIONARY_ITEM:
                return 2F;
            case MachineMeta.SAWMILL:
                return 2F;
            case MachineMeta.SLUICE:
                return 5F;
            case MachineMeta.SPONGE:
                return 2.5F;
            case MachineMeta.AUTOFISHER:
                return 2F;
            case MachineMeta.FISH_SORTER:
                return 1.5F;
            case MachineMeta.UNPACKER:
                return 1.5F;
            case MachineMeta.SLUICE_ADVANCED:
                return 7.5F;
            case MachineMeta.GENERATOR:
                return 3.5F;
            case MachineMeta.HATCHERY:
                return 1.5F;
            default:
                return 1F;
        }
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == MachineMeta.BOOKSHELF ? 5 : 0;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == MachineMeta.FISH_SORTER) return fishSorter[side];
        if (meta == MachineMeta.SLUICE) return side == 3 ? icons[MachineMeta.SLUICE] : Core.metals.getIcon(side, MetalMeta.BASE_IRON);
        if (meta == MachineMeta.SLUICE_ADVANCED) return side == 3 ? icons[MachineMeta.SLUICE_ADVANCED] : sluiceAdvanced;
        if (side < 2) {
            if (meta == MachineMeta.BOOKSHELF) return Blocks.planks.getIcon(side, meta);
            if (meta == MachineMeta.SPONGE) return Core.metals.getIcon(side, MetalMeta.BASE_IRON);
            if (meta == MachineMeta.UNPACKER) return unpacker;
            return Core.woods.getIcon(side, WoodMeta.BASE_WOOD);
        }

        return super.getIcon(side, meta);
    }

    @Override
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
        TileEntity tile = block.getTileEntity(x, y, z);
        if (tile instanceof TileBookshelf) return Blocks.bookshelf.getIcon(side, 0);
        else if (tile instanceof TileSluice) {
            TileSluice sluice = (TileSluice) tile;
            if (tile instanceof TileSluiceAdvanced) {
                if (sluice.orientation.ordinal() == side) return side > 1 ? icons[MachineMeta.SLUICE_ADVANCED] : sluiceAdvancedUp;
                else if (sluice.orientation.getOpposite().ordinal() == side) return side > 1 ? sluiceAdvancedBack : sluiceAdvancedDown;
                else return sluiceAdvanced;
            } else {
                if (sluice.orientation.ordinal() == side) return side > 1 ? icons[MachineMeta.SLUICE] : sluiceUp;
                else if (sluice.orientation.getOpposite().ordinal() == side) return side > 1 ? sluiceBack : sluiceDown;
                else return Core.metals.getIcon(side, MetalMeta.BASE_IRON);
            }
        } else return super.getIcon(block, x, y, z, side);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        TileEntity tile = world.getTileEntity(x, y, z);
        int facing = BlockPistonBase.determineOrientation(world, x, y, z, entity);
        if (tile != null) if (tile instanceof TileSluice) {
            TileSluice sluice = (TileSluice) tile;
            sluice.orientation = ForgeDirection.getOrientation(facing);
            PacketHandler.updateOrientation(sluice);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null || player.isSneaking() || tile instanceof TileSluice) return false;

        if (tile instanceof TileSponge) {
            if (world.isRemote && player instanceof EntityClientPlayerMP) {
                PacketHandler.sendToServer(new PacketSponge(x, y, z, true));
            } else if (player.getCurrentEquippedItem() != null && !world.isRemote) {
                Item currentItem = player.getCurrentEquippedItem().getItem();
                if (currentItem instanceof IEnergyContainerItem && !world.isRemote) {
                    int powerAdd = ((IEnergyContainerItem) currentItem).extractEnergy(player.getCurrentEquippedItem(), 5000, true);
                    int reduce = ((IEnergyHandler) tile).receiveEnergy(ForgeDirection.UNKNOWN, powerAdd, false);
                    ((IEnergyContainerItem) currentItem).extractEnergy(player.getCurrentEquippedItem(), reduce, false);
                }
            }

            return true;
        }

        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    //Clears water blocks around the sluice
    private void clearWater(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial() == Material.water) {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);

        if (meta == MachineMeta.SLUICE) {
            clearWater(world, x + 1, y, z);
            clearWater(world, x - 1, y, z);
            clearWater(world, x, y, z + 1);
            clearWater(world, x, y, z - 1);
        }
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        switch (meta) {
            case MachineMeta.BOOKSHELF:
                return new TileBookshelf();
            case MachineMeta.DICTIONARY_ITEM:
                return new TileDictionaryItem();
            case MachineMeta.SAWMILL:
                return new TileSawmill();
            case MachineMeta.SLUICE:
                return new TileSluice();
            case MachineMeta.SPONGE:
                return new TileSponge();
            case MachineMeta.AUTOFISHER:
                return new TileAutofisher();
            case MachineMeta.FISH_SORTER:
                return new TileFishSorter();
            case MachineMeta.UNPACKER:
                return new TileUnpacker();
            case MachineMeta.SLUICE_ADVANCED:
                return new TileSluiceAdvanced();
            case MachineMeta.GENERATOR:
                return new TileGenerator();
            case MachineMeta.HATCHERY:
                return new TileHatchery();
            default:
                return null;
        }
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        if (world.getBlockMetadata(x, y, z) == MachineMeta.BOOKSHELF) return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
        else return super.getComparatorInputOverride(world, x, y, z, side);
    }

    @Override
    public boolean isActive(int meta) {
        switch (meta) {
            case MachineMeta.BOOKSHELF:
                return true;
            case MachineMeta.DICTIONARY_ITEM:
                return Modules.isActive(Modules.factory);
            case MachineMeta.SAWMILL:
                return Modules.isActive(Modules.factory);
            case MachineMeta.SLUICE:
                return Modules.isActive(Modules.factory);
            case MachineMeta.SPONGE:
                return Modules.isActive(Modules.factory);
            case MachineMeta.AUTOFISHER:
                return Modules.isActive(Modules.fishery);
            case MachineMeta.FISH_SORTER:
                return Modules.isActive(Modules.fishery);
            case MachineMeta.UNPACKER:
                return Modules.isActive(Modules.factory);
            case MachineMeta.SLUICE_ADVANCED:
                return Modules.isActive(Modules.factory);
            case MachineMeta.GENERATOR:
                return Modules.isActive(Modules.factory);
            case MachineMeta.HATCHERY:
                return Modules.isActive(Modules.fishery);
            default:
                return true;
        }
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        switch (meta) {
            case MachineMeta.AUTOFISHER:
                return tab == MaricultureTab.tabFishery;
            case MachineMeta.FISH_SORTER:
                return tab == MaricultureTab.tabFishery;
            case MachineMeta.HATCHERY:
                return tab == MaricultureTab.tabFishery;
            default:
                return tab == MaricultureTab.tabFactory;
        }
    }

    @Override
    public int getMetaCount() {
        return MachineMeta.COUNT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        //Register other icons
        fishSorter = new IIcon[6];
        for (int i = 0; i < 6; i++) {
            fishSorter[i] = iconRegister.registerIcon(Mariculture.modid + ":fishsorter" + (i + 1));
        }

        sluiceBack = iconRegister.registerIcon(Mariculture.modid + ":sluiceBack");
        sluiceUp = iconRegister.registerIcon(Mariculture.modid + ":sluiceUp");
        sluiceDown = iconRegister.registerIcon(Mariculture.modid + ":sluiceDown");
        sluiceAdvancedBack = iconRegister.registerIcon(Mariculture.modid + ":sluiceAdvancedBack");
        sluiceAdvancedUp = iconRegister.registerIcon(Mariculture.modid + ":sluiceAdvancedUp");
        sluiceAdvancedDown = iconRegister.registerIcon(Mariculture.modid + ":sluiceAdvancedDown");
        sluiceAdvanced = iconRegister.registerIcon(Mariculture.modid + ":sluiceAdvancedSide");
        unpacker = iconRegister.registerIcon(Mariculture.modid + ":unpackerTop");
    }
}
