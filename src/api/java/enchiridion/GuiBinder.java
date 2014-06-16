package enchiridion;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiBinder extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation("books", "textures/gui/binder.png");
	protected final InventoryBinder storage;	
	public GuiBinder(IInventory inventory, InventoryBinder storage) {
		super(new ContainerBinder(inventory, storage));
		this.storage = storage;
		this.ySize += 10;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
