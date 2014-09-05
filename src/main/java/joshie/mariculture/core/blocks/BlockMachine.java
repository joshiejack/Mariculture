package joshie.mariculture.core.blocks;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.blocks.base.BlockFunctional;
import joshie.mariculture.core.events.IconRegisterEvent;
import joshie.mariculture.core.events.MaricultureEvents;
import joshie.mariculture.core.lib.MachineMeta;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.lib.WoodMeta;
import joshie.mariculture.core.tile.TileBookshelf;
import joshie.mariculture.factory.tile.TileDictionaryItem;
import joshie.mariculture.factory.tile.TileFishSorter;
import joshie.mariculture.factory.tile.TileSawmill;
import joshie.mariculture.factory.tile.TileUnpacker;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends BlockFunctional {
    private IIcon[] fishSorter;
    private IIcon unpacker;

    public BlockMachine() {
        super(Material.piston);
    }

    @Override
    public String getToolType(int meta) {
        return MaricultureEvents.getToolType(this, meta, "axe");
    }

    @Override
    public int getToolLevel(int meta) {
        return MaricultureEvents.getToolLevel(this, meta, 0);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case MachineMeta.BOOKSHELF:
                return 1F;
            case MachineMeta.FISH_SORTER:
            case MachineMeta.UNPACKER:
                return 1.5F;
            case MachineMeta.SAWMILL:
            case MachineMeta.DICTIONARY_ITEM:
                return 2F;
        }

        return MaricultureEvents.getBlockHardness(this, meta, 1F);
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == MachineMeta.BOOKSHELF ? 5 : 0;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        MaricultureEvents.onBlockPlaced(stack, this, world, x, y, z, entity, world.getTileEntity(x, y, z));
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        MaricultureEvents.onBlockBroken(block, meta, world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        IIcon icon = null;
        if (meta == MachineMeta.FISH_SORTER) return fishSorter[side];
        else if (side < 2) {
            if (meta == MachineMeta.BOOKSHELF) return Blocks.planks.getIcon(side, meta);
            else if (meta == MachineMeta.UNPACKER) return unpacker;
            else icon = Core.woods.getIcon(side, WoodMeta.BASE_WOOD);
        } else icon = super.getIcon(side, meta);

        return MaricultureEvents.getInventoryIcon(this, meta, side, icon);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
        TileEntity tile = block.getTileEntity(x, y, z);
        if (tile instanceof TileBookshelf) return Blocks.bookshelf.getIcon(side, 0);
        return MaricultureEvents.getWorldIcon(this, side, super.getIcon(block, x, y, z, side), block, x, y, z);
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
            case MachineMeta.FISH_SORTER:
                return new TileFishSorter();
            case MachineMeta.UNPACKER:
                return new TileUnpacker();
        }

        return MaricultureEvents.getTileEntity(this, meta, null);
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
            case MachineMeta.SAWMILL:
            case MachineMeta.UNPACKER:
                return Modules.isActive(Modules.factory);
            case MachineMeta.FISH_SORTER:
                return Modules.isActive(Modules.fishery);
        }

        return MaricultureEvents.isActive(this, meta, false);
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        if (meta == MachineMeta.AUTOFISHER || meta == MachineMeta.AUTOFISHER) {
            return tab == MaricultureTab.tabFishery;
        }

        return MaricultureEvents.isValidTab(this, tab, meta, tab == MaricultureTab.tabFactory);
    }

    @Override
    public int getMetaCount() {
        return MachineMeta.COUNT;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        String name = prefix != null ? prefix : "";
        icons = new IIcon[getMetaCount()];

        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(MaricultureEvents.getMod(this, i, "mariculture") + ":" + name + getName(i));
        }
        
      //Register other icons
        fishSorter = new IIcon[6];
        for (int i = 0; i < 6; i++) {
            fishSorter[i] = iconRegister.registerIcon(Mariculture.modid + ":fishsorter" + (i + 1));
        }

        unpacker = iconRegister.registerIcon(Mariculture.modid + ":unpackerTop");
        
        //Register EXTRA ICONS
        IconRegisterEvent event = new IconRegisterEvent(iconRegister);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
