package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.lib.PearlColor;
import mariculture.core.tile.TileOyster;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class RenderOyster extends RenderBase {
    public static IIcon lid_blur;
    public static IIcon lid;
    public static IIcon tongue;
    public ItemStack stack;

    public RenderOyster() {}

    @Override
    public void init() {
        super.init();
        TileOyster tile = (TileOyster) world.getTileEntity(x, y, z);
        stack = tile.getStackInSlot(0);
    }

    @Override
    public boolean isItem() {
        return isItem;
    }

    @Override
    public void renderBlock() {
        if (isItem) {
            GL11.glScalef(1.25F, 1.25F, 1.25F);
            GL11.glTranslatef(-0.15F, 0.05F, 0F);
        }

        if (dir == ForgeDirection.SOUTH || isItem) {
            setTexture(tongue);
            renderBlock(0.15, 0.1, 0.1, 0.85, 0.11, 0.9);
            renderAngledBlock(-0.15D, 0.55D, -0.35D, 0.2D, 0.55D, -0.35D, -0.15D, 0.05D, 0.1D, 0.2D, 0.05D, 0.1D, 0.0D, 0.005D, 0.0D);

            setTexture(lid);
            renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
            renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
            renderBlock(0.1, 0.1, 0.1, 0.15, 0.15, 0.9);
            renderBlock(0.85, 0.1, 0.1, 0.9, 0.15, 0.9);
            renderBlock(0.1, 0.1, 0.05, 0.9, 0.15, 0.1);
            renderBlock(0.15, 0.1, 0.9, 0.85, 0.15, 0.95);
            renderAngledBlock(-0.2D, 0.6D, -0.4D, 0.2D, 0.6D, -0.4D, -0.2D, 0.15D, 0.15D, 0.2D, 0.15D, 0.15D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.3D, 0.55D, -0.5D, 0.3D, 0.55D, -0.5D, -0.3D, 0.3D, 0.25D, 0.3D, 0.3D, 0.25D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.15D, 0.6D, -0.35D, 0.15D, 0.6D, -0.35D, -0.15D, 0.05D, 0.1D, 0.15D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);

            if (!isItem) {
                renderBlock(0.15, 0.62, 0.65, 0.85, 0.67, 0.7);
            } else {
                renderBlock(0.15, 0.15, 0.0, 0.85, 0.7, 0.1);
                setTexture(tongue);
                renderBlock(0.15, 0.15, 0.1, 0.85, 0.7, 0.101);
            }

            setTexture(lid_blur);
            renderAngledBlock(-0.1D, 0.55D, -0.35D, 0.85D, 0.55D, -0.35D, -0.1D, 0.05D, 0.1D, 0.85D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.85D, 0.55D, -0.35D, 0.1D, 0.55D, -0.35D, -0.85D, 0.05D, 0.1D, 0.1D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
        } else if (dir == ForgeDirection.EAST) {
            // Birch >> Acacia >> Jungle >> Dark Oak
            setTexture(tongue);
            renderBlock(0.1, 0.1, 0.15, 0.9, 0.11, 0.85);
            renderAngledBlock(-0.35D, 0.55D, -0.15D, 0.1D, 0.05D, -0.15D, -0.35D, 0.55D, 0.15D, 0.1D, 0.05D, 0.15D, 0.0D, 0.005D, 0.0D);
            setTexture(lid);
            renderAngledBlock(-0.4D, 0.6D, -0.2D, 0.1D, 0.15D, -0.2D, -0.4D, 0.6D, 0.2D, 0.1D, 0.15D, 0.2D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.5D, 0.55D, -0.3D, 0.2D, 0.3D, -0.3D, -0.5D, 0.55D, 0.3D, 0.2D, 0.3D, 0.3D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.35D, 0.6D, -0.15D, 0.05D, 0.05D, -0.15D, -0.35D, 0.6D, 0.15D, 0.05D, 0.05D, 0.15D, 0.0D, 0.05D, 0.0D);
            renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
            renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
            renderBlock(0.1, 0.1, 0.1, 0.9, 0.15, 0.15);
            renderBlock(0.1, 0.1, 0.85, 0.9, 0.15, 0.9);
            renderBlock(0.05, 0.1, 0.1, 0.1, 0.15, 0.9);
            renderBlock(0.9, 0.1, 0.15, 0.95, 0.15, 0.85);
            renderBlock(0.65, 0.62, 0.15, 0.7, 0.67, 0.85);
            setTexture(lid_blur);
            renderAngledBlock(-0.35D, 0.55D, -0.9D, 0.1D, 0.05D, -0.9D, -0.35D, 0.55D, 0.15D, 0.1D, 0.05D, 0.15D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.35D, 0.55D, -0.15D, 0.1D, 0.05D, -0.15D, -0.35D, 0.55D, 0.9D, 0.1D, 0.05D, 0.9D, 0.0D, 0.05D, 0.0D);
        } else if (dir == ForgeDirection.WEST) {
            setTexture(tongue);
            renderBlock(0.05, 0.1, 0.15, 0.9, 0.11, 0.85);
            renderAngledBlock(-0.1D, 0.05D, -0.15D, 0.35D, 0.55D, -0.15D, -0.1D, 0.05D, 0.15D, 0.35D, 0.55D, 0.15D, 0.0D, 0.005D, 0.0D);
            setTexture(lid);
            renderAngledBlock(-0.1D, 0.15D, -0.2D, 0.4D, 0.6D, -0.2D, -0.1D, 0.15D, 0.2D, 0.4D, 0.6D, 0.2D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.2D, 0.3D, -0.3D, 0.5D, 0.55D, -0.3D, -0.2D, 0.3D, 0.3D, 0.5D, 0.55D, 0.3D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.05D, 0.05D, -0.15D, 0.35D, 0.6D, -0.15D, -0.05D, 0.05D, 0.15D, 0.35D, 0.6D, 0.15D, 0.0D, 0.05D, 0.0D);
            renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
            renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
            renderBlock(0.1, 0.1, 0.1, 0.9, 0.15, 0.15);
            renderBlock(0.1, 0.1, 0.85, 0.9, 0.15, 0.9);
            renderBlock(0.05, 0.1, 0.15, 0.1, 0.15, 0.85);
            renderBlock(0.9, 0.1, 0.1, 0.95, 0.15, 0.9);
            renderBlock(0.3, 0.62, 0.15, 0.35, 0.67, 0.85);
            setTexture(lid_blur);
            renderAngledBlock(-0.1D, 0.05D, -0.9D, 0.35D, 0.55D, -0.9D, -0.1D, 0.05D, 0.15D, 0.35D, 0.55D, 0.15D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.1D, 0.05D, -0.15D, 0.35D, 0.55D, -0.15D, -0.1D, 0.05D, 0.9D, 0.35D, 0.55D, 0.9D, 0.0D, 0.05D, 0.0D);
        } else if (dir == ForgeDirection.NORTH) {
            setTexture(tongue);
            renderBlock(0.15, 0.1, 0.1, 0.85, 0.11, 0.9);
            renderAngledBlock(-0.15D, 0.05D, -0.1D, 0.2D, 0.05D, -0.1D, -0.15D, 0.55D, 0.35D, 0.2D, 0.55D, 0.35D, 0.0D, 0.005D, 0.0D);
            setTexture(lid);
            renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
            renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
            renderBlock(0.1, 0.1, 0.1, 0.15, 0.15, 0.95);
            renderBlock(0.85, 0.1, 0.1, 0.9, 0.15, 0.95);
            renderBlock(0.15, 0.1, 0.05, 0.85, 0.15, 0.1);
            renderBlock(0.15, 0.1, 0.9, 0.85, 0.15, 0.95);
            renderAngledBlock(-0.2D, 0.15D, -0.15D, 0.2D, 0.15D, -0.15D, -0.2D, 0.6D, 0.4D, 0.2D, 0.6D, 0.4D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.3D, 0.3D, -0.25D, 0.3D, 0.3D, -0.25D, -0.3D, 0.553D, 0.5D, 0.3D, 0.55D, 0.5D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.15D, 0.05D, -0.1D, 0.15D, 0.05D, -0.1D, -0.15D, 0.6D, 0.35D, 0.15D, 0.6D, 0.35D, 0.0D, 0.05D, 0.0D);
            renderBlock(0.15, 0.62, 0.3, 0.85, 0.67, 0.35);
            setTexture(lid_blur);
            renderAngledBlock(-0.1D, 0.05D, -0.1D, 0.85D, 0.05D, -0.1D, -0.1D, 0.55D, 0.35D, 0.85D, 0.55D, 0.35D, 0.0D, 0.05D, 0.0D);
            renderAngledBlock(-0.85D, 0.05D, -0.1D, 0.1D, 0.05D, -0.1D, -0.85D, 0.55D, 0.35D, 0.1D, 0.55D, 0.35D, 0.0D, 0.05D, 0.0D);
        }

        renderContents();
    }

    public void renderContents() {
        if (isItem) {
            setTexture(Core.pearlBlock, PearlColor.WHITE);
            renderBlock(0.4, 0.05, 0.4, 0.6, 0.25, 0.6);
        } else if (stack != null) {
            if (stack.getItem() == Item.getItemFromBlock(Blocks.sand)) {
                setTexture(Blocks.sand, stack.getItemDamage());
                renderBlock(0.45, 0.05, 0.45, 0.55, 0.2, 0.55);
            } else if (stack.getItem() == Items.ender_pearl) {
                setTexture(Blocks.wool, 13);
                renderBlock(0.4, 0.05, 0.4, 0.6, 0.25, 0.6);
            } else {
                setTexture(PearlGenHandler.getTexture(stack));
                renderBlock(0.4, 0.05, 0.4, 0.6, 0.25, 0.6);
            }
        }
    }
}
