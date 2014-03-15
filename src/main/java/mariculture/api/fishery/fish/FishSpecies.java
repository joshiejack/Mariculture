package mariculture.api.fishery.fish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ILootHandler.LootQuality;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class FishSpecies {
	public static ArrayList<FishSpecies> speciesList = new ArrayList();
	private static final HashMap<String, ArrayList<FishProduct>> products = new HashMap();
	public final int fishID;
	private IIcon theIcon;

	public FishSpecies(int id) {
		fishID = id;
		speciesList.add(this);
	}
	/* Group/Species */
	// This just converts the class name to something to be used for the images. no need to touch it, unless you name your classes differently
	public String getSpecies() {
		return (this.getClass().getSimpleName().toLowerCase()).substring(4);
	}
	
	/** Fish Products list **/
	public ArrayList<FishProduct> getProductList() {
		return products.get(getSpecies());
	}
	
	/** The EnumFishGroup this species belongs to **/
	public EnumFishGroup getGroup() {
		return EnumFishGroup.OCEAN;
	}
	
	/** Whether or not this fish species is dominant **/
	public boolean isDominant() {
		return true;
	}
	
	/* Default DNA */
	/** This is the chance that the fish will generate extra copies of themselves 
	 * in an incubator (Higher = Less Likely) (note DNA overwrites the fish species) **/
	public int getFertility() {
		return 50;
	}
	
	/** This is how much food this species of fish will consume everytime it's
	 * time to eat in the Fish Tank (note DNA overwrites the fish species)  **/
	public int getFoodConsumption() {
		return 1;
	}
	
	/** Lifespan in a tank defined in minutes (note DNA overwrites the fish species) **/
	public int getLifeSpan() {
		return 25;
	}
	
	/** This the tank level that is required for the fish to 'work' Note:
	* Returning numbers other than the listed will make the fish work with NO
	* tanks (note DNA overwrites the fish species)
	* 
	* @return 1, 3, 5 : Basic, Intermediate, Advanced */
	public int getTankLevel() {
		return 1;
	}
	
	/** Return 0 for Lazy, 1 for Normal and 2 for Hardworker **/
	public int getBaseProductivity() {
		return EnumFishWorkEthic.NORMAL.getMultiplier();
	}

	/* Fish Products */
	/** How much fish meal this species of fish produces **/
	public int getFishMealSize() {
		return 2;
	}
	
	/** Number between 1 and 2000, for the melting point of this fish **/
	public int getMeltingPoint() {
		return 180;
	}
	
	/** How much fish oil the fish will give you when liquified in the liquifier,
	 * this is number of buckets worth So if you return 6, the fish will give
	 * you 6 buckets worth of fish oil, the default is roughly 1/6th of a bucket */
	public double getFishOilVolume() {
		return 0.166;
	}
	
	/** Here you can define a custom product for your fish to return when it is
	 * liquified. **/
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Items.bone);
	}
	
	/** Set the chance of getting the product, the lower the number the higher
	 * the chance, minimum number = 2; If you set it to 1, there will be a 0%
	 * chance of getting the product, do not return 0 or less */
	public int getLiquifiedProductChance() {
		return 10;
	}
	
	/** Called when fish are registered **/
	public abstract void addFishProducts();
	
	/** Add Products **/
	public void addProduct(ItemStack stack, double chance) {
		String fish = this.getSpecies();
		ArrayList<FishProduct> list = null;
		if(products.containsKey(fish))
			list = products.get(fish);
		else
			list = new ArrayList();
		list.add(new FishProduct(stack, chance));
		products.put(fish, list);
	}

	/** The product the fish produces, called everytime the bubbles complete a
	 * cycle */
	public ItemStack getProduct(Random rand) {
		for(FishProduct product: products.get(getSpecies())) {
			int chance = (int) (product.chance * 10);
			if(rand.nextInt(1000) < chance)
				return product.product.copy();
		}
		
		return null;
	}
	
	/* Called Methods */
	
	/** return a weighted number to be used to determine how often this fish is caught, Vanilla Raw Fish = 60, Higher = More Common **/
	public int getCatchChance() {
		return 20;
	}
	
	/** Whether when this fish is caught it is ALWAYS dead, Defaults to true **/
	public boolean caughtAsRaw() {
		return true;
	}
	
	/** Return a list of the biomes that this fish can be caught in, defaults to the biomes of their group **/
	public List<EnumBiomeType> getCatchableBiomes() {
		List<EnumBiomeType> biomes = new ArrayList();
		for(EnumBiomeType biome: getGroup().getBiomes()) {
			biomes.add(biome);
		}
		
		return biomes;
	}
	
	/** Return the Quality type this fish is considered, most fish should be under the 'fish' quality, use good and rare for rarer fish **/
	public LootQuality getLootQuality() {
		return LootQuality.FISH;
	}

	/** Whether this fish can live in the area that they are, defaults to calling their group biome preference
	 * @param World Object
	 * @param xCoordinate of FishFeeder
	 * @param yCoordinate of FishFeeder
	 * @param zCoordinate of FishFeeder **/
	public boolean canLive(World world, int x, int y, int z) {
		return getGroup().canLive(world, x, y, z);
	}
	
	/** How much food eating this fish restores, return -1 if it's not edible **/
	public int getFoodStat() {
		return 1;
	}
	
	/** How much saturation this fish restores **/
	public float getFoodSaturation() {
		return 0.3F;
	}
	
	/** How long in ticks, it takes to teat this fish **/
	public int getFoodDuration() {
		return 32;
	}
	
	/** Whether or not this fish can eaten if the player is full **/
	public boolean canAlwaysEat() {
		return false;
	}
	
	/** Add a potion effect for this fish to return **/
	public String getPotionEffect(ItemStack stack) {
		return null;
	}

	/** This is called after a player has eaten, and only if it can eat, which is define by the getFoodStat call
	 * 
	 * @param World object
	 * @param The player eating */
	public void onConsumed(World world, EntityPlayer player) {
		return;
	}

	/** Called when you right click a fish
	 * 
	 * @param World Object
	 * @param The Player right clicking
	 * @param The FishStack
	 * @param Random */
	public ItemStack onRightClick(World world, EntityPlayer player, ItemStack stack, Random rand) {
		return stack;
	}

	/** This is called every half a second, and lets you affect the world around
	 * the feeder with your fish The tank type is passed so you can determine
	 * whether you are in or outside the tank, It is only called if your fish
	 * are active
	 * 
	 * @param World Object
	 * @param xCoordinate of FishFeeder
	 * @param yCoordinate of FishFeeder
	 * @param zCoordinate of FishFeeder
	 * @param Tank Size */
	public void affectWorld(World world, int x, int y, int z, int tankType) {
		return;
	}

	/** This is called whenever a living entity is in the water of the tank, you can
	 * have your fish species do something special to them if you like It is
	 * called every half a second, so if a player is in for less than that, the
	 * effect won't apply. This is only called if your fish are active
	 * 
	 * @param The Entity */
	public void affectLiving(EntityLivingBase entity) {
		return;
	}
	
	/* Other */
	/** This is the chance that a fish will spawn in an ocean chest return null
	 * if you do not want the species to spawn in chests (Make sure the int
	 * array is three long) */
	public int[] getChestGenChance() {
		return new int[] { 1, 3, 5 };
	}
	
	/* Language/IIcon */
	/** Fish's name **/
	public String getName() {
		return StatCollector.translateToLocal("fish.data.species." + this.getSpecies());
	}
	
	/** Returns your fish icon **/
	public IIcon getIcon() {
		return theIcon;
	}
	
	/** Called to register your fish icon **/
	public void registerIcon(IIconRegister iconRegister) {
		theIcon = iconRegister.registerIcon("mariculture:fish/" + getSpecies());
	}
}
