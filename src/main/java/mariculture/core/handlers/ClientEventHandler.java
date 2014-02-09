package mariculture.core.handlers;

import org.lwjgl.opengl.GL11;

import mariculture.Mariculture;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ClientEventHandler {
	@ForgeSubscribe
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
