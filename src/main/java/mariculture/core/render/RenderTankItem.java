package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.TankMeta;
import mariculture.core.tile.TileTankBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class RenderTankItem implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return (item.getItemDamage() == TankMeta.TANK || item.getItemDamage() == TankMeta.TANK_ALUMINUM || item.getItemDamage() == TankMeta.TANK_TITANIUM) && type == ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    private void bindTexture(ResourceLocation resource) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        //GL11.glDisable(GL11.GL_LIGHTING);
        //GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        RenderBlocks render = (RenderBlocks) data[0];
        render.useInventoryTint = true;
        FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.stackTagCompound);
        if (fluid != null) {
            render.setOverrideBlockTexture(fluid.getFluid().getStillIcon());
            int dmg = stack.getItemDamage();
            int total = dmg == TankMeta.TANK ? TileTankBlock.COPPER_CAPACITY : dmg == TankMeta.TANK_ALUMINUM ? TileTankBlock.ALUMINUM_CAPACITY : dmg == TankMeta.TANK_TITANIUM ? TileTankBlock.TITANIUM_CAPACITY : dmg == TankMeta.TANK_GAS? TileTankBlock.GAS_CAPACITY: 0;
            if (total > 0) {
                double height = fluid.amount * 1D / total;
                render.renderMinX = 0;

                if (fluid.getFluid().isGaseous()) {
                    render.renderMinY = 1 - height - 0.1;
                } else render.renderMinY = -0.1;

                render.renderMinZ = 0;
                render.renderMaxX = 1;

                if (fluid.getFluid().isGaseous()) {
                    render.renderMaxY = 1 - 0.1;
                } else render.renderMaxY = height - 0.1;

                render.renderMaxZ = 1;
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                render.renderFaceYNeg(Blocks.water, 0.0D, 0.0D, 0.0D, render.overrideBlockTexture);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                render.renderFaceYPos(Blocks.water, 0.0D, 0.0D, 0.0D, render.overrideBlockTexture);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                render.renderFaceZNeg(Blocks.water, 0.0D, 0.0D, 0.0D, render.overrideBlockTexture);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                render.renderFaceZPos(Blocks.water, 0.0D, 0.0D, 0.0D, render.overrideBlockTexture);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                render.renderFaceXNeg(Blocks.water, 0.0D, 0.0D, 0.0D, render.overrideBlockTexture);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                render.renderFaceXPos(Blocks.water, 0.0D, 0.0D, 0.0D, render.overrideBlockTexture);
                tessellator.draw();
            }
        }

        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        render.setOverrideBlockTexture(Core.tanks.getIcon(0, stack.getItemDamage()));
        render.renderBlockAsItem(Core.tanks, 0, 2);
        render.clearOverrideBlockTexture();
    }
}
