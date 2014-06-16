package powercrystals.minefactoryreloaded;

import java.util.Map;
import java.util.TreeMap;

public abstract class MFRRegistry {
	private static Map<String, Boolean> _unifierBlacklist = new TreeMap<String, Boolean>();

	public static void registerUnifierBlacklist(String string) {
		_unifierBlacklist.put(string, null);
	}

	public static Map<String, Boolean> getUnifierBlacklist() {
		return _unifierBlacklist;
	}
}
