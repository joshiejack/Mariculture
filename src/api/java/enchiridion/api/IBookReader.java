package enchiridion.api;

import net.minecraft.item.ItemStack;

import org.w3c.dom.Document;

public interface IBookReader {
	//Return the document when enchiridion is in debug mode, irrelevant for other mods
	public Document getDocumentDebugMode(String xml);
	//Return the document in normal mode
	public Document getDocument(String xml);
	//Return the gui relevant to this stack
	public Object getGui(ItemStack stack, String xml);
}
