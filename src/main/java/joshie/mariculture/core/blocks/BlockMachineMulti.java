package joshie.mariculture.core.blocks;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.api.events.MaricultureEvents;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.blocks.base.BlockFunctionalMulti;
import joshie.mariculture.core.lib.MachineMultiMeta;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.lib.RockMeta;
import joshie.mariculture.core.tile.TileCrucible;
import joshie.mariculture.fishery.tile.TileIncubator;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachineMulti extends BlockFunctionalMulti {
    private IIcon[] crucibleIcons;
    private IIcon[] incubatorIcons;

    public BlockMachineMulti() {
        super(Material.piston);
    }

    @Override
    public String getToolType(int meta) {
        return MaricultureEvents.getToolType(this, meta, "pickaxe");
    }

    @Override
    public int getToolLevel(int meta) {
        return MaricultureEvents.getToolLevel(this, meta, 2);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        float hardness = 5F;
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case MachineMultiMeta.CRUCIBLE:
                hardness = 6F;
            case MachineMultiMeta.INCUBATOR_BASE:
                hardness = 10F;
        }

        return MaricultureEvents.getBlockHardness(this, meta, hardness);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        IIcon icon = null;
        icon = side < 2 && meta == MachineMultiMeta.CRUCIBLE ? Core.rocks.getIcon(side, RockMeta.BASE_BRICK) : super.getIcon(side, meta);
        return MaricultureEvents.getInventoryIcon(this, meta, side, icon);
    }

    @Override
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
        IIcon icon = null;
        if (side > 1) {
            TileEntity tile = block.getTileEntity(x, y, z);
            if (tile instanceof TileCrucible) {
                TileCrucible crucible = (TileCrucible) tile;
                if (crucible.master == null) icon = getIcon(side, MachineMultiMeta.CRUCIBLE);
                else if (crucible.isMaster()) icon = crucibleIcons[1];
                else return crucibleIcons[0];
            } else if (tile instanceof TileIncubator && block.getBlockMetadata(x, y, z) != MachineMultiMeta.INCUBATOR_BASE) {
                TileIncubator incubator = (TileIncubator) tile;
                if (incubator.master == null) icon = getIcon(side, MachineMultiMeta.INCUBATOR_TOP);
                else if (incubator.facing == ForgeDirection.DOWN) icon = incubatorIcons[0];
                else icon = incubatorIcons[1];
            }
        }

        if (icon == null) {
            icon = super.getIcon(block, x, y, z, side);
        }

        return MaricultureEvents.getWorldIcon(this, side, icon, block, x, y, z);
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        TileEntity tile = null;
        switch (meta) {
            case MachineMultiMeta.CRUCIBLE:
                tile = new TileCrucible();
                break;
            case MachineMultiMeta.INCUBATOR_BASE:
                tile = new TileIncubator();
                break;
            case MachineMultiMeta.INCUBATOR_TOP:
                tile = new TileIncubator();
                break;
        }

        return MaricultureEvents.getTileEntity(this, meta, tile);
    }

    @Override
    public boolean isActive(int meta) {
        boolean isActive = false;
        switch (meta) {
            case MachineMultiMeta.CRUCIBLE:
                isActive = true;
            case MachineMultiMeta.INCUBATOR_BASE:
                isActive = Modules.isActive(Modules.fishery);
            case MachineMultiMeta.INCUBATOR_TOP:
                isActive = Modules.isActive(Modules.fishery);
        }

        return MaricultureEvents.isActive(this, meta, isActive);
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        boolean isValid = tab == MaricultureTab.tabFishery;
        if (meta == MachineMultiMeta.CRUCIBLE) {
            isValid = tab == MaricultureTab.tabFactory;
        }

        return MaricultureEvents.isValidTab(this, tab, meta, isValid);
    }

    @Override
    public int getMetaCount() {
        return MachineMultiMeta.COUNT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        //Extra Icons for the blocks
        incubatorIcons = new IIcon[2];
        incubatorIcons[0] = iconRegister.registerIcon(Mariculture.modid + ":incubatorBottom");
        incubatorIcons[1] = iconRegister.registerIcon(Mariculture.modid + ":incubatorTopTop");

        crucibleIcons = new IIcon[2];
        crucibleIcons[0] = iconRegister.registerIcon(Mariculture.modid + ":crucibleTop");
        crucibleIcons[1] = iconRegister.registerIcon(Mariculture.modid + ":crucibleBottom");
    }
}
