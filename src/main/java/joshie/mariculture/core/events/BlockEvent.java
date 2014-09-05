package joshie.mariculture.core.events;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEvent extends Event {
    public final Block block;
    public BlockEvent(Block block) {
        this.block = block;
    }
    
    public static class GetBlockName extends BlockEvent {
        public final int meta;
        public String name;
        public GetBlockName(Block block, int meta, String name) {
            super(block);
            this.meta = meta;
            this.name = name;
        }
    }
    
    public static class GetIsActive extends BlockEvent {
        public final int meta;
        public boolean isActive;
        public GetIsActive(Block block, int meta, boolean isActive) {
            super(block);
            this.meta = meta;
            this.isActive = isActive;
        }
    }
    
    public static class GetIsValidTab extends BlockEvent {
        public final int meta;
        public final CreativeTabs tab;
        public boolean isValid;
        public GetIsValidTab(Block block, int meta, CreativeTabs tab, boolean isValid) {
            super(block);
            this.meta = meta;
            this.tab = tab;
            this.isValid = isValid;
        }
    }

    public static class GetToolType extends BlockEvent {
        public final int meta;
        public String tooltype;
        public GetToolType(Block block, int meta, String tooltype) {
            super(block);
            this.meta = meta;
            this.tooltype = tooltype;
        }
    }
    
    public static class GetToolLevel extends BlockEvent {
        public final int meta;
        public int level;
        public GetToolLevel(Block block, int meta, int level) {
            super(block);
            this.meta = meta;
            this.level = level;
        }
    }

    public static class GetHardness extends BlockEvent {
        public final int meta;
        public float hardness;
        public GetHardness(Block block, int meta, float hardness) {
            super(block);
            this.meta = meta;
            this.hardness = hardness;
        }
    }
    
    public static class GetTileEntity extends BlockEvent {
        public final int meta;
        public TileEntity tile;
        public GetTileEntity(Block block, int meta, TileEntity tile) {
            super(block);
            this.meta = meta;
            this.tile = tile;
        }
    }
    
    public static class TilePlaced extends BlockEvent {
        public final ItemStack stack;
        public final EntityLivingBase entity;
        public final TileEntity tile;
        public int direction;
        public TilePlaced(ItemStack stack, Block block, EntityLivingBase entity, TileEntity tile, int direction) {
            super(block);
            this.stack = stack;
            this.entity = entity;
            this.tile = tile;
            this.direction = direction;
        }
    }
    
    @Cancelable
    public static class BlockBroken extends BlockEvent {
        public final World world;
        public final int x;
        public final int y;
        public final int z;
        public final int meta;
        public BlockBroken(Block block, World world, int x, int y, int z, int meta) {
            super(block);
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.meta = meta;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static class GetInventoryIIcon extends BlockEvent {
        public final int meta;
        public final int side;
        public IIcon icon;
        public GetInventoryIIcon(Block block, int meta, int side, IIcon icon) {
            super(block);
            this.meta = meta;
            this.side = side;
            this.icon = icon;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static class GetWorldIIcon extends BlockEvent {
        public final IBlockAccess world;
        public final int x;
        public final int y;
        public final int z;
        public final int side;
        public IIcon icon;
        public GetWorldIIcon(Block block, int side, IIcon icon, IBlockAccess world, int x, int y, int z) {
            super(block);
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.side = side;
            this.icon = icon;
        }
    }
}
