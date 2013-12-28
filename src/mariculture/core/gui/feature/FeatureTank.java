package mariculture.core.gui.feature;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.util.ITank;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FeatureTank extends Feature {
	public enum TankSize {
		SINGLE, DOUBLE
	}
	
	private ITank tank;
	private int yPoz;
	private int xPoz;
	private TankSize size;
	
	public FeatureTank(ITank tank, int x, int y, TankSize size) {
		super();
		this.yPoz = y;
		this.xPoz = x;
		this.size = size;
		this.tank = tank;
	}
	
	@Override
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		int xPlus = (size == TankSize.SINGLE)? 17: 35;
		if (mouseX >= (xPoz - 1) && mouseX <= (xPoz + xPlus - 1) && mouseY >= (yPoz - 1) && mouseY <= (yPoz + 59 - 1)) {
			tooltip.add(tank.getLiquidName());
			tooltip.add(tank.getLiquidQty() + "mB");
		}
	}
	
	@Override
	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		super.draw(gui, x, y, mouseX, mouseY);
		
		int height = tank.getTankScaled(58);
		FluidStack liquid = tank.getFluid();
		if (liquid == null) {
			return;
		}
		
		int start = 0;

		Icon liquidIcon = null;
		Fluid fluid = liquid.getFluid();
		if (fluid != null && fluid.getStillIcon() != null) {
			liquidIcon = fluid.getStillIcon();
		}

		tm.bindTexture(TextureMap.locationBlocksTexture);

		if (liquidIcon != null) {
			while (true) {
				int i;

				if (height > 16) {
					i = 16;
					height -= 16;
				} else {
					i = height;
					height = 0;
				}

				gui.drawTexturedModelRectFromIcon(x + xPoz, y + yPoz + 58 - i - start, liquidIcon, 16, 16 - (16 - i));

				if (size.equals(TankSize.DOUBLE)) {
					gui.drawTexturedModelRectFromIcon(x + xPoz + 10, y + yPoz + 58 - i - start, liquidIcon, 16, 16 - (16 - i));
					gui.drawTexturedModelRectFromIcon(x + xPoz + 18, y + yPoz + 58 - i - start, liquidIcon, 16, 16 - (16 - i));
				}
				
				start = start + 16;

				if (i == 0 || height == 0) {
					break;
				}
			}
		}

		tm.bindTexture(texture);
		int width = (size.equals(TankSize.DOUBLE))? 34: 16;
		int xStart = (size.equals(TankSize.DOUBLE))? 16: 0; 
		gui.drawTexturedModalRect(x + xPoz, y + yPoz, xStart, 0, width, 60);
	}
}
