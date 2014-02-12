package mariculture.core.handlers;

import mariculture.Mariculture;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {
	@SubscribeEvent
	public void onSound(SoundLoadEvent event) {
		event.manager.addSound(Mariculture.modid + ":blink.ogg");
		event.manager.addSound(Mariculture.modid + ":firepunch.ogg");
		event.manager.addSound(Mariculture.modid + ":fludd.ogg");
		event.manager.addSound(Mariculture.modid + ":mirror.ogg");
		event.manager.addSound(Mariculture.modid + ":sift.ogg");
		event.manager.addSound(Mariculture.modid + ":powerpunch.ogg");
		event.manager.addSound(Mariculture.modid + ":hammer.ogg");
		event.manager.addSound(Mariculture.modid + ":bang.ogg");
	}
}
