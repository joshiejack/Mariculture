package mariculture.core.guide;

import net.minecraft.item.ItemStack;

public class PageCrafting extends PageParser {
	String[] craft1;
	String[] craft2;
	String[] craft3;
	String output;
	int number;

	@Override
	public void read(XMLHelper xml) {		
		craft1 = xml.getElement("craft1").split("\\s*,\\s*");
		craft2 = xml.getElement("craft2").split("\\s*,\\s*");
		craft3 = xml.getElement("craft3").split("\\s*,\\s*");
		output = xml.getElement("craftResult");
		number = xml.getHelper("craftResult").getAttribAsInteger("num", 1);
	}

	@Override
	public void parse() {
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x - 1, y - 1, 0, 0, 58, 58);
		
		for(int i = 0; i < 3; i++) {
			drawItemStack(gui, (ItemStack) GuideHandler.getIcon(craft1[i]), x + (i * 20), y + 0);
			drawItemStack(gui, (ItemStack) GuideHandler.getIcon(craft2[i]), x + (i * 20), y + 20);
			drawItemStack(gui, (ItemStack) GuideHandler.getIcon(craft3[i]), x + (i * 20), y + 40);
		}
		
		drawItemStack(gui, (ItemStack) GuideHandler.getIcon(output), x + 64, y + 18);
		if(number < 10)
			gui.getMC().fontRenderer.drawString("x" + number, x + 67, y + 36, 4210752);
		else
			gui.getMC().fontRenderer.drawString("x" + number, x + 63, y + 36, 4210752);
	}
}
