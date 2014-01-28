package mariculture.core.guide;

import mariculture.api.guide.PageParser;
import mariculture.api.guide.XMLHelper;
import mariculture.core.lib.Text;
import mariculture.core.util.FluidDictionary;
import mariculture.plugins.nei.NEIBase;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

public class PageVat extends PageParser {
	String input, fluid1, fluid2, fluid3, output, colorIn, colorOut;
	int numInput, vol1, vol2, vol3, numOutput, time;
	
	public String getColor(String color) {
		if(color.equals("grey"))
			return Text.GREY;
		if(color.equals("black"))
			return Text.BLACK;
		return Text.DARK_GREY;
	}

	@Override
	public void read(XMLHelper xml) {
		input = xml.getOptionalElement("input");
		fluid1 = xml.getOptionalElement("fluid1");
		fluid2 = xml.getOptionalElement("fluid2");
		fluid3 = xml.getOptionalElement("fluidOutput");
		output = xml.getOptionalElement("output");

		if(!input.equals("")) {
			colorIn = getColor(xml.getHelper("input").getOptionalAttribute("color"));
			numInput = xml.getHelper("input").getAttribAsInteger("num", 1);
		} 
		
		if(!output.equals("")) {
			colorOut = getColor(xml.getHelper("output").getOptionalAttribute("color"));
			numOutput = xml.getHelper("output").getAttribAsInteger("num", 1);
		}
		
		if(!fluid1.equals(""))
			vol1 = xml.getHelper("fluid1").getAttribAsInteger("vol", 1000);
		if(!fluid2.equals(""))
			vol2 = xml.getHelper("fluid2").getAttribAsInteger("vol", 1000);
		if(!fluid3.equals(""))
			vol3 = xml.getHelper("fluidOutput").getAttribAsInteger("vol", 1000);
		
		time = xml.getElementAsInteger("time", 10);
	}

	@Override
	public void parse() {		
		gui.getMC().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		/** Drawing the Tank on the left **/
		
		//Fluid 1
		if(!fluid1.equals("")) {
			Icon icon = FluidRegistry.getFluid(GuideHandler.getFluidIcon(fluid1)).getIcon();
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 6, y + 28, icon, 7, 16);
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 13, y + 28, icon, 16, 16);
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 6, y + 12, icon, 7, 16);
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 13, y + 12, icon, 16, 16);
			
			if(!fluid2.equals("")) {
				icon = FluidRegistry.getFluid(GuideHandler.getFluidIcon(fluid2)).getIcon();
			}
			
			//Fluid 2
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 29, y + 28, icon, 16, 16);
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 45, y + 28, icon, 7, 16);
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 29, y + 12, icon, 16, 16);
			NEIBase.drawScaledTexturedModelRectFromIcon(x + 45, y + 12, icon, 7, 16);
		}
		
		/** End Left Tank Begin Right Tank **/
		
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x, y, 58, 0, 58, 62);
		gui.drawTexturedModalRect(x + 84, y, 58, 0, 58, 62);
		gui.drawTexturedModalRect(x + 62, y + 20, 2, 63, 22, 14);
		
		if(!input.equals("")) {
			drawItemStack(gui, (ItemStack) GuideHandler.getIcon(input), x + 20, y + 20);
			gui.getMC().fontRenderer.drawString(colorIn + "x" + numInput, x + 36, y + 27, 4210752);
		} if(!input.equals("")) {
			drawItemStack(gui, (ItemStack) GuideHandler.getIcon(output), x + 104, y + 20);
			gui.getMC().fontRenderer.drawString(colorOut + "x" + numOutput, x + 120, y + 27, 4210752);
		}
		
		if(!fluid1.equals("")) {
			int xPlus = (fluid2.equals(""))? 12: 10;
			if(vol1 > 9999)
				xPlus-=3;
			String after = fluid1.startsWith("metal")? " ingots": "mB";
			if(after.equals(" ingots"))
				xPlus-=2;
			gui.getMC().fontRenderer.drawString("" + vol1 + after, x + xPlus, y + 2, 4210752);
			if(!fluid2.equals("")) {
				after = fluid2.startsWith("metal")? " ingots": "mB";
				if(after.equals(" ingots"))
					xPlus-=2;
				gui.getMC().fontRenderer.drawString("" + vol2 + after, x + 36 + xPlus, y, 4210752);
			}
		}
		
		if(!fluid3.equals("")) {
			int xPlus = 0;
			String after = fluid3.startsWith("metal")? " ingots": "mB";
			if(after.equals(" ingots"))
				xPlus-=2;
			gui.getMC().fontRenderer.drawString("" + vol3 + after, x + 42 + xPlus, y + 2, 4210752);
		}
		
		gui.getMC().fontRenderer.drawString("" + time + "s", x + 62, y + 11, 4210752);
	}
}
