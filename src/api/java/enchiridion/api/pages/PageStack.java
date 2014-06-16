package enchiridion.api.pages;

import net.minecraft.item.ItemStack;

import org.w3c.dom.Element;

import enchiridion.api.DisplayRegistry;
import enchiridion.api.XMLHelper;

public class PageStack extends PageParser {
	String stack;
	
	@Override
	public void read(Element xml) {
		stack = XMLHelper.getAttribute(xml, "item");
	}

	@Override
	public void parse() {
		drawItemStack((ItemStack) DisplayRegistry.getIcon(stack), x, y + 0);
	}
}
