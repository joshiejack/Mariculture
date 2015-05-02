package mariculture.fishery.gui;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.fishery.tile.TileFishTank;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

public class GuiFishTank extends GuiMariculture {
    private static final RenderItem fishyRender = new RenderTankItem();
    private TileFishTank tile;

    public GuiFishTank(InventoryPlayer player, TileFishTank tile) {
        super(new ContainerFishTank(tile, player), "fishtank", 52);

        this.tile = tile;
    }
    
    @Override
    public void addItemToolTip(ItemStack stack, List<String> currenttip) {   
        if (stack.stackSize > 999) {
            currenttip.add("");
            currenttip.add(stack.stackSize + " " + stack.getDisplayName());
        }
    }

    @Override
    public void func_146977_a(Slot slot) {
        int i = slot.xDisplayPosition;
        int j = slot.yDisplayPosition;
        ItemStack itemstack = slot.getStack();
        boolean flag = false;
        boolean flag1 = slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
        String s = null;

        if (slot == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null) {
            itemstack = itemstack.copy();
            itemstack.stackSize /= 2;
        } else if (this.field_147007_t && this.field_147008_s.contains(slot) && itemstack1 != null) {
            if (this.field_147008_s.size() == 1) {
                return;
            }

            if (Container.func_94527_a(slot, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slot)) {
                itemstack = itemstack1.copy();
                flag = true;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, itemstack, slot.getStack() == null ? 0 : slot.getStack().stackSize);

                if (itemstack.stackSize > itemstack.getMaxStackSize()) {
                    s = EnumChatFormatting.YELLOW + "" + itemstack.getMaxStackSize();
                    itemstack.stackSize = itemstack.getMaxStackSize();
                }

                if (itemstack.stackSize > slot.getSlotStackLimit()) {
                    s = EnumChatFormatting.YELLOW + "" + slot.getSlotStackLimit();
                    itemstack.stackSize = slot.getSlotStackLimit();
                }
            } else {
                this.field_147008_s.remove(slot);
                this.func_146980_g();
            }
        }

        this.zLevel = 100.0F;
        fishyRender.zLevel = 100.0F;

        if (itemstack == null) {
            IIcon iicon = slot.getBackgroundIconIndex();

            if (iicon != null) {
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND); // Forge: Blending needs to be enabled for this.
                this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
                this.drawTexturedModelRectFromIcon(i, j, iicon, 16, 16);
                GL11.glDisable(GL11.GL_BLEND); // Forge: And clean that up
                GL11.glEnable(GL11.GL_LIGHTING);
                flag1 = true;
            }
        }

        if (!flag1) {
            if (flag) {
                drawRect(i, j, i + 16, j + 16, -2130706433);
            }

            GL11.glEnable(GL11.GL_DEPTH_TEST);
            fishyRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, i, j);
            fishyRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, i, j, s);
        }

        fishyRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    @Override
    public int getX() {
        return 22;
    }

    @Override
    protected void onMouseClick(int x, int y) {}

    @Override
    public void drawForeground() {}

    @Override
    public void drawBackground(int x, int y) { }
}