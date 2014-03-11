package mariculture.fishery;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class DummyContainerMariculture extends DummyModContainer {
	public DummyContainerMariculture() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "maricultureCore";
		meta.name = "Mariculture Core";
		meta.version = "1.0";
		meta.description = "Fishies!";
		meta.authorList = Arrays.asList("joshie");
		meta.url = "http://mariculture.wikispaces.com";
		meta.screenshots = new String[0];
		meta.parent = "mariculture";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Subscribe
	public void modConstruction(FMLConstructionEvent evt) {
	}

	@Subscribe
	public void preInit(FMLPreInitializationEvent evt) {
	}

	@Subscribe
	public void init(FMLInitializationEvent evt) {
	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent evt) {
	}
}