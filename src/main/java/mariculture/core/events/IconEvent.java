package mariculture.core.events;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IconEvent {
    public static class IconRegisterEvent extends Event {
        public final IIconRegister register;
        
        public IconRegisterEvent(IIconRegister register) {
            this.register = register;
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
