package mariculture.factory.gui;

import mariculture.core.Mariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.factory.blocks.TileDictionary;
import mariculture.factory.gui.ContainerDictionary.SlotDictionary;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiDictionary extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/dictionary.png");
	private TileDictionary tile;

	public GuiDictionary(InventoryPlayer player, TileDictionary tile) {
		super(new ContainerDictionary(tile, player));
		this.tile = tile;
	}
	
	//Code taken from buildcraft
	@Override
    protected void mouseClickMove(int x, int y, int mouseButton, long time) {
            Slot slot = getSlotAtPosition(x, y);
            if (mouseButton == 1 && slot instanceof SlotDictionary) {
                    return;
            }
            super.mouseClickMove(x, y, mouseButton, time);
    }

    public Slot getSlotAtPosition(int x, int y) {
            for (int slotIndex = 0; slotIndex < this.inventorySlots.inventorySlots.size(); ++slotIndex) {
                    Slot slot = (Slot) this.inventorySlots.inventorySlots.get(slotIndex);
                    if (isMouseOverSlot(slot, x, y)) {
                            return slot;
                    }
            }
            return null;
    }
    
    private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
        int left = this.guiLeft;
        int top = this.guiTop;
        mouseX -= left;
        mouseY -= top;
        return mouseX >= slot.xDisplayPosition - 1 && mouseX < slot.xDisplayPosition + 16 + 1 && mouseY >= slot.yDisplayPosition - 1 && mouseY < slot.yDisplayPosition + 16 + 1;
    }
    
    //End code from buildcraft

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 10, 6, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = tile.getFreezeProgressScaled(24);
		this.drawTexturedModalRect(x + 77, y + 50, 176, 74, progress + 1, 16);
	}
}