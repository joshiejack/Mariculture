package mariculture.core.lib;

import java.util.logging.Level;

import mariculture.api.core.IModules;
import mariculture.core.Core;
import mariculture.core.handlers.LogHandler;
import mariculture.diving.Diving;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.sealife.Sealife;
import mariculture.transport.Transport;
import mariculture.world.WorldPlus;

public class Modules {
	public static Module core = new Core();
	public static Module diving = new Diving();
	public static Module factory = new Factory();
	public static Module fishery = new Fishery();
	public static Module magic = new Magic();
	public static Module sealife = new Sealife();
	public static Module transport = new Transport();
	public static Module world = new WorldPlus();

	public static class Module {
		public boolean isActive() {
			return true;
		}

		public void setActive(boolean active) {}
		public void registerHandlers() {}
		public void registerBlocks() {}
		public void registerEntities() {}
		public void registerItems() {}
		public void registerOther() {}
		public void addRecipes() {}

		public void load() {			
			if (isActive()) {
				registerHandlers();
				registerBlocks();
				registerEntities();
				registerItems();
				registerOther();
			}
		}
		
		public void postLoad() {
			if(isActive()) {
				addRecipes();
				
				String name = (this.getClass().getSimpleName());
				LogHandler.log(Level.INFO, "Mariculture: " + name + " Module Finished Loading");
			}
		}
	}
}
