package mariculture.core.guide;

import mariculture.core.lib.Text;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

public class PageVat extends PageParser {
	String input, fluid1, fluid2, fluid3, output, colorIn, colorOut, fluid1Type, fluid2Type, fluid3Type;
	int numInput, vol1, vol2, vol3, numOutput, time;
	
	@Override
	public void read(XMLHelper xml) {
		input = xml.getOptionalElement("input");
		fluid1 = xml.getOptionalElement("fluid1");
		fluid2 = xml.getOptionalElement("fluid2");
		fluid3 = xml.getOptionalElement("fluid3");
		output = xml.getOptionalElement("output");

		if(!input.equals("")) {
			colorIn = Text.getColor(xml.getHelper("input").getOptionalAttribute("color"));
			numInput = xml.getHelper("input").getAttribAsInteger("num", 1);
		} 
		
		if(!output.equals("")) {
			colorOut = Text.getColor(xml.getHelper("output").getOptionalAttribute("color"));
			numOutput = xml.getHelper("output").getAttribAsInteger("num", 1);
		}
		
		if(!fluid1.equals("")) {
			vol1 = xml.getHelper("fluid1").getAttribAsInteger("vol", 1000);
			fluid1Type = xml.getHelper("fluid1").getOptionalAttribute("type").equals("")? "mB": " " + xml.getHelper("fluid1").getOptionalAttribute("type");
		}
		
		if(!fluid2.equals("")) {
			vol2 = xml.getHelper("fluid2").getAttribAsInteger("vol", 1000);
			fluid2Type = xml.getHelper("fluid2").getOptionalAttribute("type").equals("")? "mB": " " + xml.getHelper("fluid2").getOptionalAttribute("type");
		}
		
		if(!fluid3.equals("")) {
			vol3 = xml.getHelper("fluid3").getAttribAsInteger("vol", 1000);
			fluid3Type = xml.getHelper("fluid3").getOptionalAttribute("type").equals("")? "mB": " " + xml.getHelper("fluid3").getOptionalAttribute("type");
		}
		
		time = xml.getElementAsInteger("time", 10);
	}

	@Override
	public void parse() {		
		gui.getMC().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		/** Drawing the Tank on the left **/
		
		//Fluid 1
		if(!fluid1.equals("")) {
			IIcon icon = FluidRegistry.getFluid(GuideHandler.getFluidIcon(fluid1)).getIcon();
			//TODO: Move Drawing Tank Icons to another class
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 6, y + 28, icon, 7, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 13, y + 28, icon, 16, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 6, y + 12, icon, 7, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 13, y + 12, icon, 16, 16);
			
			if(!fluid2.equals("")) {
				icon = FluidRegistry.getFluid(GuideHandler.getFluidIcon(fluid2)).getIcon();
			}
			
			//Fluid 2
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 29, y + 28, icon, 16, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 45, y + 28, icon, 7, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 29, y + 12, icon, 16, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 45, y + 12, icon, 7, 16);
		}
		
		if(!fluid3.equals("")) {
			IIcon icon = FluidRegistry.getFluid(GuideHandler.getFluidIcon(fluid3)).getIcon();
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 97, y + 28, icon, 16, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 90, y + 28, icon, 7, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 113, y + 28, icon, 16, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 129, y + 28, icon, 7, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 97, y + 12, icon, 16, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 90, y + 12, icon, 7, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 113, y + 12, icon, 16, 16);
			//NEIBase.drawScaledTexturedModelRectFromIcon(x + 129, y + 12, icon, 7, 16);
		}
		
		/** End Left Tank Begin Right Tank **/
		
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x, y, 58, 0, 58, 62);
		gui.drawTexturedModalRect(x + 84, y, 58, 0, 58, 62);
		gui.drawTexturedModalRect(x + 62, y + 20, 2, 63, 22, 14);
		
		if(!input.equals("")) {
			drawItemStack(gui, (ItemStack) GuideHandler.getIcon(input), x + 20, y + 20);
			gui.getMC().fontRenderer.drawString(colorIn + "x" + numInput, x + 36, y + 27, 4210752);
		} 
		
		if(!output.equals("")) {
			drawItemStack(gui, (ItemStack) GuideHandler.getIcon(output), x + 104, y + 20);
			gui.getMC().fontRenderer.drawString(colorOut + "x" + numOutput, x + 120, y + 27, 4210752);
		}
		
		if(!fluid1.equals("")) {
			int xPlus = (fluid2.equals(""))? 12: 10;
			if(vol1 > 9999)
				xPlus-=3;
			if(!fluid1Type.equals("mB"))
				xPlus-=2;
			if(!fluid2.equals("")) {
				if(fluid2Type == null)
					fluid2Type = fluid1Type;
				if(!fluid2Type.equals("mB"))
					xPlus-=2;
				gui.getMC().fontRenderer.drawString("" + vol1 + fluid1Type, x + xPlus - 25, y - 10, 4210752);
				gui.getMC().fontRenderer.drawString("" + vol2 + fluid2Type, x + xPlus + 30, y - 10, 4210752);
				gui.getMC().fontRenderer.drawString("+", x + xPlus + 20, y - 10, 4210752);
			} else {
				gui.getMC().fontRenderer.drawString("" + vol1 + fluid1Type, x + xPlus, y + 2, 4210752);
			}
		}
		
		if(!fluid3.equals("")) {
			int xPlus = 0;
			if(!fluid3Type.equals("mB"))
				xPlus+=2;
			gui.getMC().fontRenderer.drawString("" + vol3 + fluid3Type, x + 92 + xPlus, y + 2, 4210752);
		}
		
		gui.getMC().fontRenderer.drawString("" + time + "s", x + 62, y + 11, 4210752);
	}
}
