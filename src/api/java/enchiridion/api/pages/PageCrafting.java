package enchiridion.api.pages;

import net.minecraft.item.ItemStack;

import org.w3c.dom.Element;

import enchiridion.api.DisplayRegistry;
import enchiridion.api.XMLHelper;

public class PageCrafting extends PageParser {
	String[] craft1;
	String[] craft2;
	String[] craft3;
	String output;
	int number;

	@Override
	public void read(Element xml) {		
		craft1 = (XMLHelper.getElement(xml, "craft1") + " ").split("\\|");
		craft2 = (XMLHelper.getElement(xml, "craft2") + " ").split("\\|");
		craft3 = (XMLHelper.getElement(xml, "craft3") + " ").split("\\|");
		output = XMLHelper.getElement(xml, "craftResult");
		number = XMLHelper.getAttribAsInteger(XMLHelper.getNode(xml, "craftResult"), "num", 1);
	}

	@Override
	public void parse() {
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x - 1, y - 1, 0, 0, 58, 58);
		
		if(craft1.length != 3 || craft2.length != 3 || craft3.length != 3) return;
		for(int i = 0; i < 3; i++) {
			drawItemStack((ItemStack) DisplayRegistry.getIcon(craft1[i].trim()), x + (i * 20), y + 0);
			drawItemStack((ItemStack) DisplayRegistry.getIcon(craft2[i].trim()), x + (i * 20), y + 20);
			drawItemStack((ItemStack) DisplayRegistry.getIcon(craft3[i].trim()), x + (i * 20), y + 40);
		}
		
		drawItemStack((ItemStack) DisplayRegistry.getIcon(output), x + 64, y + 18);
		if(number < 10)
			gui.getMC().fontRenderer.drawString("x" + number, x + 67, y + 36, 4210752);
		else
			gui.getMC().fontRenderer.drawString("x" + number, x + 63, y + 36, 4210752);
	}
}
