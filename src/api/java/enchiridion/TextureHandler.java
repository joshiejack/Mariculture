package enchiridion;

import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import enchiridion.CustomBooks.BookInfo;

public class TextureHandler {
	public static TextureMap map;
	
	@SubscribeEvent
	public void onStitch(TextureStitchEvent.Pre event) {
		if(event.map.getTextureType() == 1) {
			map = event.map;
			for (Entry<String, BookInfo> books : CustomBooks.bookInfo.entrySet()) {
				String id = books.getKey();
				BookInfo info = books.getValue();
				if(info.path != null) {
					map.setTextureEntry(info.path, new CustomIconAtlas(books.getKey(), info.path));
				}
			}
		} 
	}
}
