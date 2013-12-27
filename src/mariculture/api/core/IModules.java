package mariculture.api.core;

public interface IModules {
	/** This will return whether or not a module is active
	 * 
	 *  Valid Strings are:
	 *  core, diving, factory, fishery, magic, sealife, transport, world**/
	public boolean isLoaded(String module);
}
