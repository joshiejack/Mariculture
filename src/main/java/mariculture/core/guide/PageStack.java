package mariculture.core.guide;

import net.minecraft.item.ItemStack;

public class PageStack extends PageParser {
	String stack;
	
	@Override
	public void read(XMLHelper xml) {
		stack = xml.getAttribute("item");
	}

	@Override
	public void parse() {
		drawItemStack(gui, (ItemStack) GuideHandler.getIcon(stack), x, y + 0);
	}
}
