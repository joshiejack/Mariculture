package mariculture.core.handlers;

import java.util.HashMap;

import net.minecraftforge.fluids.FluidStack;

public class FluidDicHandler {

	
	//Key = The FluidName, Value = The Dictionary Name
	public static final HashMap<String, Integer> ratios = new HashMap();
	//Key = The FluidName, Value = The Dictionary name
	public static final HashMap<String, String> dicNames = new HashMap();
	
	public static void init() {
		register("xp", "xpjuice", 20);
		register("xp", "immibis.liquidxp", 100);
		register("xp", "mobessence", 66);
		register("water", "lava", 100);
		register("water", "water", 100);
	}
	
	public static void register(String dictionaryName, String fluid, int ratio) {
		dicNames.put(fluid, dictionaryName);
		ratios.put(fluid, ratio);
	}
	
	public static boolean areSameDicNames(FluidStack stack, String fluid) {
		if(stack != null && stack.getFluid() != null) {
			String stackName = dicNames.get(stack.getFluid().getName());
			System.out.println(dicNames.size());
			return stackName != null? stackName.equals(dicNames.get(fluid)): false;
		} else return false;
	}
	
	public static Integer getValue(String fluid) {
		return ratios.get(fluid);
	}
	
	public static class FluidDicEntry {
		public String name;
		public int ratio;
		
		public FluidDicEntry (String name, int ratio) {
			this.name = name;
			this.ratio = ratio;
		}
	}
	

	/*public static HashMap<String, String> references = new HashMap();
	public static HashMap<String, ArrayList<FluidDicEntry>> entries = new HashMap();
	public static void init() {
		//These are fluid amounts of 100
		register("xp", "xpjuice", 20);
		register("xp", "immibis.liquidxp", 100);
		register("xp", "mobessence", 66);
		register("water", "lava", 100);
		register("water", "water", 100);
		//My Natural gas > Emashers
		//Dart > Emasher Slick Water
		//PR > TE > Liquid Redstone
		//TE > TiC > Liquid Ender
		//MFR > Forestry > Milk
		//Poison BOP, Binnie
	}
	
	public static void register(String dicName, String fluidName) {
		register(dicName, fluidName, 1);
	}
	
	public static void register(String dicName, String fluidName, int ratio) {
		references.put(dicName, fluidName);
		ArrayList<FluidDicEntry> list = entries.get(dicName);
		if(list == null) list = new ArrayList();
		list.add(new FluidDicEntry(fluidName, ratio));
		entries.put(dicName, list);
	}
	
	public static String getDicName(String fluid) {
		return references.get(fluid) != null? references.get(fluid): "";
	}
	
	public static String getDicName(FluidStack stack) {
		if(stack != null && stack.getFluid() != null) {
			return getDicName(stack.getFluid().getName());
		} return "";
	}
	
	//Ratio = Bucket to Bucket based... Hard to explain :p
	public static class FluidDicEntry {
		public String fluid;
		public int ratio;
		public FluidDicEntry(String fluid, int ratio) {
			this.fluid = fluid;
			this.ratio = ratio;
		}
	} */
}
