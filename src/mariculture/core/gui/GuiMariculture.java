package mariculture.core.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mariculture.core.Mariculture;
import mariculture.core.gui.feature.Feature;
import mariculture.core.helpers.InventoHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class GuiMariculture extends GuiContainer {
	private static ResourceLocation TEXTURE;
	protected int nameHeight = 5;
	public int mouseX = 0;
	public int mouseY = 0;
	public ArrayList<String> tooltip = new ArrayList<String>();
	protected ArrayList<Feature> features = new ArrayList<Feature>();

	public GuiMariculture(ContainerMariculture container, String texture) {
		this(container, texture, 0);
	}
	
	public GuiMariculture(Container container, String texture, int yOffset) {
		super(container);
		this.TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/" + texture + ".png");
		this.ySize += yOffset;
		this.xSize = 201;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		drawForeground();
		fontRenderer.drawString(StatCollector.translateToLocal(getName()), getX(), nameHeight, 4210752);
		fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
		tooltip.clear();
		addToolTip();
		drawToolTip(tooltip, mouseX, mouseY, fontRenderer);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawBackground(x, y);

		if(features.size() > 0)
			drawFeatures(x, y);
	}
	
	public abstract String getName();
	public abstract int getX();

	public void drawForeground() {
		return;
	}
	
	public void drawBackground(int x, int y) {
		return;
	}

	public void drawFeatures(int x, int y) {
		for(Feature feat: features) {
			feat.draw(this, x, y, mouseX, mouseY);
		}
	}
	
	public void addToolTip() {
		for(Feature feat: features) {
			feat.addTooltip(tooltip, mouseX, mouseY);
		}
	}

	public void addItemToolTip(ItemStack stack, List<String> list) {
		return;
	}

	protected void drawToolTip(List par1List, int par2, int par3,
			FontRenderer font) {
		if (!par1List.isEmpty()) {
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int k = 0;
			Iterator iterator = par1List.iterator();

			while (iterator.hasNext()) {
				String s = (String) iterator.next();
				int l = font.getStringWidth(s);

				if (l > k) {
					k = l;
				}
			}

			int i1 = par2 + 12;
			int j1 = par3 - 12;
			int k1 = 8;

			if (par1List.size() > 1) {
				k1 += 2 + (par1List.size() - 1) * 10;
			}

			if (i1 + k > this.width) {
				i1 -= 28 + k;
			}

			if (j1 + k1 + 6 > this.height) {
				j1 = this.height - k1 - 6;
			}

			this.zLevel = 300.0F;
			int l1 = -267386864;
			this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
			this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
			int i2 = 1347420415;
			int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
			this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
			this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

			for (int k2 = 0; k2 < par1List.size(); ++k2) {
				String s1 = (String) par1List.get(k2);
				font.drawStringWithShadow(s1, i1, j1, -1);

				if (k2 == 0) {
					j1 += 2;
				}

				j1 += 10;
			}

			this.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}

	@Override
	public void handleMouseInput() {
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		mouseX = x - guiLeft;
		mouseY = y - guiTop;

		super.handleMouseInput();
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3)  {
		super.mouseClicked(par1, par2, par3);
		for(Feature feat: features) {
			feat.mouseClicked(mouseX, mouseY);
		}
    }

	@Override
	protected void mouseClickMove(int x, int y, int mouseButton, long time) {
		Slot slot = getSlotAtPosition(x, y);
		if (mouseButton == 1 && slot instanceof SlotFake) {
			return;
		}

		super.mouseClickMove(x, y, mouseButton, time);
	}

	public Slot getSlotAtPosition(int x, int y) {
		for (int slotIndex = 0; slotIndex < this.inventorySlots.inventorySlots .size(); ++slotIndex) {
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
		return mouseX >= slot.xDisplayPosition - 1
				&& mouseX < slot.xDisplayPosition + 16 + 1
				&& mouseY >= slot.yDisplayPosition - 1
				&& mouseY < slot.yDisplayPosition + 16 + 1;
	}
}
