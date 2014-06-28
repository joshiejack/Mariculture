package mariculture.magic.gui;

import mariculture.core.gui.GuiStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.network.PacketEnchant;
import mariculture.core.network.PacketHandler;
import mariculture.magic.ItemMirror;
import mariculture.magic.Magic;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

public class GuiMirror extends GuiStorage {
    private int[] enchantLevels = new int[3];
    ItemStack mirror;

    public GuiMirror(IInventory playerInv, InventoryStorage storage, World world, String gui, ItemStack stack) {
        super(new ContainerMirror(playerInv, storage, world, stack), gui);
        allowUserInput = false;
        mirror = stack.copy();
    }

    @Override
    public String getName() {
        if (mirror != null) return StatCollector.translateToLocal("item." + ((ItemMirror) mirror.getItem()).getName(mirror) + ".name");

        return "";
    }

    @Override
    public int getX() {
        if (mirror != null) return ((ItemMirror) mirror.getItem()).getX(mirror);

        return 0;
    }

    @Override
    public void drawBackground(float f, int i, int j) {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        if (storage.getStackInSlot(0) == null) {
            drawTexturedModalRect(x + 7, y + 10, 217, 12, 18, 18);
        }
        if (storage.getStackInSlot(1) == null) {
            drawTexturedModalRect(x + 7, y + 31, 217, 33, 18, 18);
        }
        if (storage.getStackInSlot(2) == null) {
            drawTexturedModalRect(x + 7, y + 53, 217, 55, 18, 18);
        }

        if (hasItemForEnchanting()) {
            GL11.glPushMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            ScaledResolution var7 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            GL11.glViewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(), (var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(), 240 * var7.getScaleFactor());
            GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
            GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
            float var8 = 1.0F;
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            RenderHelper.enableStandardItemLighting();
            GL11.glTranslatef(0.0F, 3.3F, -16.0F);
            GL11.glScalef(var8, var8, var8);
            float var9 = 5.0F;
            GL11.glScalef(var9, var9, var9);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            float var10 = 0F + (0F - 0F) * f;
            GL11.glTranslatef((1.0F - var10) * 0.2F, (1.0F - var10) * 0.1F, (1.0F - var10) * 0.25F);
            GL11.glRotatef(-(1.0F - var10) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            float var11 = 0F + (0F - 0F) * f + 0.25F;
            float var12 = 0F + (0F - 0F) * f + 0.75F;
            var11 = (var11 - MathHelper.truncateDoubleToInt(var11)) * 1.6F - 0.3F;
            var12 = (var12 - MathHelper.truncateDoubleToInt(var12)) * 1.6F - 0.3F;

            if (var11 < 0.0F) {
                var11 = 0.0F;
            }

            if (var12 < 0.0F) {
                var12 = 0.0F;
            }

            if (var11 > 1.0F) {
                var11 = 1.0F;
            }

            if (var12 > 1.0F) {
                var12 = 1.0F;
            }

            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopMatrix();
            RenderHelper.disableStandardItemLighting();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(TEXTURE);
            EnchantmentNameParts.instance.reseedRandomGenerator(storage.seed);

            loadLevels();

            for (int k = 0; k < 3; ++k) {
                final String randString = EnchantmentNameParts.instance.generateNewRandomName();
                zLevel = 0.0F;
                mc.renderEngine.bindTexture(TEXTURE);
                final int level = enchantLevels[k];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                if (level == 0) {
                    drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 185, 108, 19);
                } else {
                    final String lvlString = "" + level;
                    FontRenderer fontRenderer = mc.standardGalacticFontRenderer;
                    int var18 = 6839882;

                    if (mc.thePlayer.experienceLevel < level && !mc.thePlayer.capabilities.isCreativeMode) {
                        drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 185, 108, 19);
                        fontRenderer.drawSplitString(randString, x + 62, y + 16 + 19 * k, 104, (var18 & 16711422) >> 1);
                        fontRenderer = mc.fontRenderer;
                        var18 = 4226832;
                        fontRenderer.drawStringWithShadow(lvlString, x + 62 + 104 - fontRenderer.getStringWidth(lvlString), y + 16 + 19 * k + 7, var18);
                    } else {
                        final int var19 = i - (x + 60);
                        final int var20 = j - (y + 14 + 19 * k);

                        if (var19 >= 0 && var20 >= 0 && var19 < 108 && var20 < 19) {
                            drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 204, 108, 19);
                            var18 = 16777088;
                        } else {
                            drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 166, 108, 19);
                        }

                        fontRenderer.drawSplitString(randString, x + 62, y + 16 + 19 * k, 104, var18);
                        fontRenderer = mc.fontRenderer;
                        var18 = 8453920;
                        fontRenderer.drawStringWithShadow(lvlString, x + 62 + 104 - fontRenderer.getStringWidth(lvlString), y + 16 + 19 * k + 7, var18);
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        if (hasItemForEnchanting()) {
            int var4 = (width - xSize) / 2;
            int var5 = (height - ySize) / 2;

            for (int var6 = 0; var6 < 3; ++var6) {
                final int var7 = par1 - (var4 + 60);
                final int var8 = par2 - (var5 + 14 + 19 * var6);

                if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && ((ContainerMirror) inventorySlots).enchantItem(mc.thePlayer, var6 + 1)) {
                    int level = enchantLevels[var6];
                    PacketHandler.sendToServer(new PacketEnchant(mc.thePlayer.openContainer.windowId, level));
                }
            }
        }
    }

    public void loadLevels() {
        if (storage.getHeldItem() != null) {
            ItemStack stack = storage.getHeldItem();
            if (stack.hasTagCompound() && stack.getItem() != Magic.basicMirror) {
                enchantLevels = stack.stackTagCompound.getIntArray("Levels");
            } else {
                enchantLevels[0] = 1;
                enchantLevels[1] = 3;
                enchantLevels[2] = 5;
            }
        } else {
            enchantLevels[0] = 1;
            enchantLevels[1] = 3;
            enchantLevels[2] = 5;
        }
    }

    public boolean hasItemForEnchanting() {
        if (storage.getStackInSlot(3) != null) {
            ItemStack stack = storage.getStackInSlot(3);
            if (stack.isItemEnchantable() && !stack.isItemEnchanted()) return true;
        }
        return false;
    }
}
