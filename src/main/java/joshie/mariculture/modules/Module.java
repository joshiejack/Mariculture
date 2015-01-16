package joshie.mariculture.modules;

import java.util.ArrayList;

public abstract class Module {
	public static ArrayList<Module> modules = new ArrayList();
	public Module() {
		modules.add(this);
	}
	
	public abstract void preInit();
	public abstract void init();
	public abstract void postInit();
}
