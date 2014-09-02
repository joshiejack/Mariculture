package joshie.mariculture.factory.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderBlocksCustom extends RenderBlocks {
    public static Block getBlockForSide(ItemStack stack, int side) {
        String name = stack.stackTagCompound.getString("BlockIdentifier" + side);
        return (Block) Block.blockRegistry.getObject(name);
    }

    public static int getMetaForSide(ItemStack stack, int side) {
        if (stack.stackTagCompound.getIntArray("BlockMetas").length > 0) return stack.stackTagCompound.getIntArray("BlockMetas")[side];
        else return 0;
    }

    public static int getSideForSide(ItemStack stack, int side) {
        if (stack.stackTagCompound.getIntArray("BlockSides").length > 0) return stack.stackTagCompound.getIntArray("BlockSides")[side];
        else return 0;
    }

    public IIcon getIconForSide(ItemStack stack, int side) {
        return getBlockIconFromSideAndMetadata(getBlockForSide(stack, side), getSideForSide(stack, side), getMetaForSide(stack, side));
    }

    public void renderBlockAsItem(Block block, int meta, float flt, ItemStack stack) {
        if (!stack.hasTagCompound()) return;
        for (int i = 0; i < 6; i++) {
            String name = stack.stackTagCompound.getString("BlockIdentifier" + i);
            if (name == null) return;
        }
        Tessellator tessellator = Tessellator.instance;
        boolean flag = block == Blocks.grass;

        if (block == Blocks.dispenser || block == Blocks.dropper || block == Blocks.furnace) {
            meta = 3;
        }

        int j;
        float f1;
        float f2;
        float f3;

        if (useInventoryTint) {
            j = block.getRenderColor(meta);

            if (flag) {
                j = 16777215;
            }

            f1 = (j >> 16 & 255) / 255.0F;
            f2 = (j >> 8 & 255) / 255.0F;
            f3 = (j & 255) / 255.0F;
            GL11.glColor4f(f1 * flt, f2 * flt, f3 * flt, 1.0F);
        }

        j = block.getRenderType();
        setRenderBoundsFromBlock(block);
        int k;

        if (j != 0 && j != 31 && j != 39 && j != 16 && j != 26) {
            if (j == 1) {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                IIcon iicon = getIconForSide(stack, 0);
                drawCrossedSquares(iicon, -0.5D, -0.5D, -0.5D, 1.0F);
                tessellator.draw();
            } else if (j == 19) {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                block.setBlockBoundsForItemRender();
                renderBlockStemSmall(block, meta, renderMaxY, -0.5D, -0.5D, -0.5D);
                tessellator.draw();
            } else if (j == 23) {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                block.setBlockBoundsForItemRender();
                tessellator.draw();
            } else if (j == 13) {
                block.setBlockBoundsForItemRender();
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                f1 = 0.0625F;
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                tessellator.addTranslation(0.0F, 0.0F, f1);
                renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
                tessellator.addTranslation(0.0F, 0.0F, -f1);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                tessellator.addTranslation(0.0F, 0.0F, -f1);
                renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
                tessellator.addTranslation(0.0F, 0.0F, f1);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                tessellator.addTranslation(f1, 0.0F, 0.0F);
                renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
                tessellator.addTranslation(-f1, 0.0F, 0.0F);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                tessellator.addTranslation(-f1, 0.0F, 0.0F);
                renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
                tessellator.addTranslation(f1, 0.0F, 0.0F);
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            } else if (j == 22) {
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                TileEntityRendererChestHelper.instance.renderChest(block, meta, flt);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            } else if (j == 6) {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderBlockCropsImpl(block, meta, -0.5D, -0.5D, -0.5D);
                tessellator.draw();
            } else if (j == 2) {
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderTorchAtAngle(block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D, 0);
                tessellator.draw();
            } else if (j == 10) {
                for (k = 0; k < 2; ++k) {
                    if (k == 0) {
                        setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
                    }

                    if (k == 1) {
                        setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            } else if (j == 27) {
                k = 0;
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();

                for (int l = 0; l < 8; ++l) {
                    byte b0 = 0;
                    byte b1 = 1;

                    if (l == 0) {
                        b0 = 2;
                    }

                    if (l == 1) {
                        b0 = 3;
                    }

                    if (l == 2) {
                        b0 = 4;
                    }

                    if (l == 3) {
                        b0 = 5;
                        b1 = 2;
                    }

                    if (l == 4) {
                        b0 = 6;
                        b1 = 3;
                    }

                    if (l == 5) {
                        b0 = 7;
                        b1 = 5;
                    }

                    if (l == 6) {
                        b0 = 6;
                        b1 = 2;
                    }

                    if (l == 7) {
                        b0 = 3;
                    }

                    float f5 = b0 / 16.0F;
                    float f6 = 1.0F - k / 16.0F;
                    float f7 = 1.0F - (k + b1) / 16.0F;
                    k += b1;
                    setRenderBounds(0.5F - f5, f7, 0.5F - f5, 0.5F + f5, f6, 0.5F + f5);
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
                }

                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            } else if (j == 11) {
                for (k = 0; k < 4; ++k) {
                    f2 = 0.125F;

                    if (k == 0) {
                        setRenderBounds(0.5F - f2, 0.0D, 0.0D, 0.5F + f2, 1.0D, f2 * 2.0F);
                    }

                    if (k == 1) {
                        setRenderBounds(0.5F - f2, 0.0D, 1.0F - f2 * 2.0F, 0.5F + f2, 1.0D, 1.0D);
                    }

                    f2 = 0.0625F;

                    if (k == 2) {
                        setRenderBounds(0.5F - f2, 1.0F - f2 * 3.0F, -f2 * 2.0F, 0.5F + f2, 1.0F - f2, 1.0F + f2 * 2.0F);
                    }

                    if (k == 3) {
                        setRenderBounds(0.5F - f2, 0.5F - f2 * 3.0F, -f2 * 2.0F, 0.5F + f2, 0.5F - f2, 1.0F + f2 * 2.0F);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            } else if (j == 21) {
                for (k = 0; k < 3; ++k) {
                    f2 = 0.0625F;

                    if (k == 0) {
                        setRenderBounds(0.5F - f2, 0.30000001192092896D, 0.0D, 0.5F + f2, 1.0D, f2 * 2.0F);
                    }

                    if (k == 1) {
                        setRenderBounds(0.5F - f2, 0.30000001192092896D, 1.0F - f2 * 2.0F, 0.5F + f2, 1.0D, 1.0D);
                    }

                    f2 = 0.0625F;

                    if (k == 2) {
                        setRenderBounds(0.5F - f2, 0.5D, 0.0D, 0.5F + f2, 1.0F - f2, 1.0D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            } else if (j == 32) {
                for (k = 0; k < 2; ++k) {
                    if (k == 0) {
                        setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                    }

                    if (k == 1) {
                        setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            } else if (j == 35) {
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                renderBlockAnvilOrient((BlockAnvil) block, 0, 0, 0, meta << 2, true);
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            } else if (j == 34) {
                for (k = 0; k < 3; ++k) {
                    if (k == 0) {
                        setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
                        setOverrideBlockTexture(this.getBlockIcon(Blocks.obsidian));
                    } else if (k == 1) {
                        setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
                        setOverrideBlockTexture(this.getBlockIcon(Blocks.beacon));
                    } else if (k == 2) {
                        setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                        setOverrideBlockTexture(this.getBlockIcon(Blocks.glass));
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                clearOverrideBlockTexture();
            } else if (j == 38) {
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                renderBlockHopperMetadata((BlockHopper) block, 0, 0, 0, 0, true);
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            } else {
                FMLRenderAccessLibrary.renderInventoryBlock(this, block, meta, j);
            }
        } else {
            if (j == 16) {
                meta = 1;
            }

            block.setBlockBoundsForItemRender();
            setRenderBoundsFromBlock(block);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 0));
            tessellator.draw();

            if (flag && useInventoryTint) {
                k = block.getRenderColor(meta);
                f2 = (k >> 16 & 255) / 255.0F;
                f3 = (k >> 8 & 255) / 255.0F;
                float f4 = (k & 255) / 255.0F;
                GL11.glColor4f(f2 * flt, f3 * flt, f4 * flt, 1.0F);
            }

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 1));
            tessellator.draw();

            if (flag && useInventoryTint) {
                GL11.glColor4f(flt, flt, flt, 1.0F);
            }

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 2));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 3));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 4));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIconForSide(stack, 5));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }
}
