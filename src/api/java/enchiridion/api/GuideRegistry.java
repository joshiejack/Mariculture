package enchiridion.api;

import enchiridion.api.pages.PageCrafting;
import enchiridion.api.pages.PageImage;
import enchiridion.api.pages.PageParagraph;
import enchiridion.api.pages.PageStack;
import enchiridion.api.pages.PageText;
import enchiridion.api.pages.PageUnderline;

public class GuideRegistry {	
	public static void init() {		
		registerHandlers();
	}
	
	public static void registerHandlers() {
		GuideHandler.registerPageHandler("crafting", new PageCrafting());
		GuideHandler.registerPageHandler("paragraph", new PageParagraph());
		GuideHandler.registerPageHandler("img", new PageImage());
		GuideHandler.registerPageHandler("stack", new PageStack());
		GuideHandler.registerPageHandler("hr", new PageUnderline());
		GuideHandler.registerPageHandler("text", new PageText());
	}
}
