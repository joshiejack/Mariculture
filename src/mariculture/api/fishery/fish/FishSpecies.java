package mariculture.api.fishery.fish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class FishSpecies {
	public static ArrayList<FishSpecies> speciesList = new ArrayList();
	private static final HashMap<String, ArrayList<FishProduct>> products = new HashMap();
	public final int fishID;
	private Icon theIcon;

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
	
	/** How much fish oil the fish will give you when liquified in the liquifier,
	 * this is number of buckets worth So if you return 6, the fish will give
	 * you 6 buckets worth of fish oil, the default is roughly 1/6th of a bucket */
	public double getFishOilVolume() {
		return 0.166;
	}
	
	/** Here you can define a custom product for your fish to return when it is
	 * liquified. **/
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.bone);
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
	/**
	 * Whether or not this fish can be caught in the wild, and under what
	 * conditions
	 * @param random
	 * @param World Object
	 * @param xCoordinate where player is fishing
	 * @param yCoordinate where player is fishing
	 * @param zCoordinate where player is fishing
	 * @param The quality of the rod the player is using
	 **/
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		if(isWorldCorrect(world) && quality.getRank() >= getRodNeeded().getRank() && rand.nextInt(100) < getCatchChance())
			return Fishing.fishHelper.biomeMatches(world.getWorldChunkManager().getBiomeGenAt(x, z), getGroup().getBiomes());
		return false;
	}
	
	/** Return whether this type of world is suitable to catch these fish**/
	public boolean isWorldCorrect(World world) {
		return world.provider.isSurfaceWorld();
	}
	
	/** Whether when this fish is caught it is ALWAYS dead, Defaults to true **/
	public boolean caughtAsRaw() {
		return true;
	}
	
	/** Return a number between 1 and 100 (percentage) **/
	public int getCatchChance() {
		return 20;
	}
	
	/** Return the rod Quality needed to catch this fish **/
	public EnumRodQuality getRodNeeded() {
		return EnumRodQuality.OLD;
	}

	/** Whether this fish can live in the area that they are, defaults to calling their group biome preference
	 * @param World Object
	 * @param xCoordinate of FishFeeder
	 * @param yCoordinate of FishFeeder
	 * @param zCoordinate of FishFeeder **/
	public boolean canLive(World world, int x, int y, int z) {
		return isWorldCorrect(world) && getGroup().canLive(world, x, y, z);
	}

	/** This is called when a player attempts to eat the raw fish
	 * 
	 * @param World object
	 * @param The player eating */
	public void onConsumed(World world, EntityPlayer player) {
		player.getFoodStats().addStats(1, 0.3F);

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
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
	
	/* Language/Icon */
	/** Fish's name **/
	public String getName() {
		return StatCollector.translateToLocal("fish.data.species." + this.getSpecies());
	}
	
	/** Returns your fish icon **/
	public Icon getIcon() {
		return theIcon;
	}
	
	/** Called to register your fish icon **/
	public void registerIcon(IconRegister iconRegister) {
		theIcon = iconRegister.registerIcon("mariculture:fish/" + getSpecies());
	}
}
