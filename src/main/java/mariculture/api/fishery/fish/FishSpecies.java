package mariculture.api.fishery.fish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.RodType;
import mariculture.api.util.CachedCoords;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Multimap;

public abstract class FishSpecies {
    //This is set by the fish-mechanics config, and determines whether to bypass biome requirements when catching fish
    public static boolean DISABLE_BIOME_CATCHING;
    //This is set by the fish-mechanics config, and determines whether to bypass dimension requirements when catching fish
    public static boolean DISABLE_DIMENSION_CATCHING;
    //Reference from names to number id, mappings + the List of Fish Species
    public static final HashMap<Integer, FishSpecies> species = new HashMap();
    public static final HashMap<String, Integer> ids = new HashMap();

    //The Products this species produces and the biomes they can live in
    private static final HashMap<String, ArrayList<FishProduct>> products = new HashMap();
    public String modid;

    //The Fish Icons
    private IIcon theIcon;
    private IIcon altIcon;

    //Should be ignored, just a helper method for getting the fish id
    public final int getID() {
        return ids.get(getSpecies());
    }

    //This is called when creating a fish, it can be ignore by you
    public final FishSpecies setup(String mod) {
        modid = mod;
        return this;
    }

    /* Group/Species */
    // This just converts the class name to something to be used for the images. no need to touch it, unless you name your classes differently
    public String getSimpleName() {
        return getClass().getSimpleName().toLowerCase().substring(4);
    }

    public String getSpecies() {
        return modid + ":" + getSimpleName();
    }

    //Helper method ignore
    protected final boolean isAcceptedTemperature(int temp) {
        return temp >= (getTemperatureBase() - getTemperatureTolerance()) && temp <= (getTemperatureBase() + getTemperatureTolerance());
    }
    
    /** This is base temperature of the fish species **/ 
    public abstract int getTemperatureBase();
    
    /** This is the temperature tolerance of this fish, how far up and down from the base it can go **/ 
    public abstract int getTemperatureTolerance();
    
    /** This is base salinity of the fish species **/
    public abstract Salinity getSalinityBase();
    
    /** This is the salinity tolerance of this fish, how far up and down from the base it can go **/
    public abstract int getSalinityTolerance();

    /** If this fish can swim in anything other than water, they do not care about salinity **/
	public boolean ignoresSalinity() {
		return getWater1() != Blocks.water || getWater2() != Blocks.water;
	}

    /** This determines whether a fish item entity will still die when it's in water **/
    public boolean isLavaFish() {
        return false;
    }

    /** Return the itemstack this fish turns in to when it dies **/
    public ItemStack getRawForm(int stackSize) {
        return new ItemStack(Items.fish, stackSize, getID());
    }

    /** Whether or not this fish species is dominant **/
    public abstract boolean isDominant();

    /* Default DNA, Average Default is marked in brackets, All of these are the default values for specific DNA, they are overwritten by a fish's DNA */
    /** (25) Lifespan in a tank defined in minutes (note DNA overwrites the fish species) **/
    public abstract int getLifeSpan();

    /** (200) This is the number of fish eggs that this fish will generate, whole numbers, Suggested Maximum of 5000 **/
    public abstract int getFertility();

    /** (1) This is how many attempts to create a product a fish has, defaults generally, 0 = fails everytime **/
    public int getBaseProductivity() {
        return 1;
    }

    /** (1) This is how much food this species of fish will consume everytime it's
     * time to eat in the Fish Tank  **/
    public int getFoodConsumption() {
        return 1;
    }

    //NOT DNA, This will allow a fish to use 0 food when their dna says, only if their species lets them
    public boolean requiresFood() {
        return true;
    }

    /** Return the amount of water blocks this fish needs **/
    public int getWaterRequired() {
        return 15;
    }
    
    public boolean isFluidValid(Block block) {
        return block == getWater1() || block == getWater2();
    }
    
    /** The water type 1 that is valid **/
    public Block getWater1() {
        return Blocks.water;
    }
    
    /** The water type 1 that is valid **/
    public Block getWater2() {
        return Blocks.water;
    }

    /** (0) return a bonus for the area the fish can do it's world effects**/
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return 0;
    }

    //Fish Products, these are always based on species
    /* Fish Products list, should be ignored, just calls the products you added */
    public final ArrayList<FishProduct> getProductList() {
        return products.get(getSpecies());
    }

    /** Called when fish are registered **/
    public abstract void addFishProducts();

    /** Helper methods **/
    public final void addProduct(Block block, double chance) {
        addProduct(new ItemStack(block), chance);
    }

    public final void addProduct(Item item, double chance) {
        addProduct(new ItemStack(item), chance);
    }

    /** Add Products, call this from the addFishProducts call, fish can have a maximum of 15 different products **/
    public final void addProduct(ItemStack stack, double chance) {
        String fish = getSpecies();
        ArrayList<FishProduct> list = null;
        if (products.containsKey(fish)) {
            list = products.get(fish);
        } else {
            list = new ArrayList();
        }
        if (list.size() < 15) {
            list.add(new FishProduct(stack.copy(), chance));
        }
        products.put(fish, list);
    }

    /* The product the fish produces, called everytime the bubbles complete a cycle, can be ignored by you */
    public final ItemStack getProduct(Random rand) {
        for (FishProduct product : products.get(getSpecies())) {
            int chance = (int) (product.chance * 10);
            if (rand.nextInt(1000) < chance) return product.product.copy();
        }

        return null;
    }

    //Raw Fish Products, These are what you can do use/Raw Fish for
    /** Return true for this fish to get destroyed when you attack with it **/
    public boolean destroyOnAttack() {
        return false;
    }

    /** Whether this fish has a damage boost when attacking things!**/
    public Multimap getModifiers(UUID uuid, Multimap multimap) {
        return multimap;
    }

    /** Return whether you can use this fish for a potion or not **/
    public String getPotionEffect(ItemStack stack) {
        return null;
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

    /** How much fish meal this species of fish produces **/
    public int getFishMealSize() {
        return (int) Math.max(Math.floor(getFishOilVolume()), 1);
    }

    //Food Based Data
    /** How much food eating this fish restores, return -1 if it's not edible **/
    public int getFoodStat() {
        return (int) Math.ceil(getFishOilVolume());
    }

    /** How much saturation this fish restores **/
    public float getFoodSaturation() {
        return (float) (getFishOilVolume() / 10F);
    }

    /** How long in ticks, it takes to teat this fish **/
    public int getFoodDuration() {
        return 32;
    }

    /** Whether or not this fish can eaten if the player is full **/
    public boolean canAlwaysEat() {
        return false;
    }

    /** This is called after a player has eaten a raw fish
     * 
     * @param World object
     * @param The player eating */
    public void onConsumed(World world, EntityPlayer player) {
        return;
    }

    //On Action, these are called when certain things happen to a fish	
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
     * @param Coordinates of all the water blocks this tank consists of */
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        return;
    }
    
    /** Whether the below method should be called for this fish **/
    public boolean hasLivingEffect() {
        return false;
    }
    
    /** Called when this fish is added to the fish feeder
     *  @param	the fish stack
     *  @param	the world
     *  @param  the x of fish feeder
     *  @param	the y of fish feeder
     *  @param  the z of fish feeder
     *  @param  the water of the fish**/
    public void onFishAdded(ItemStack stack, World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
    	return;
    }
    
    /** Called when this fish is added to the fish feeder
     *  Or when it dies.
     *  @param	the fish stack
     *  @param	the world
     *  @param  the x of fish feeder
     *  @param	the y of fish feeder
     *  @param  the z of fish feeder
     *  @param  the water of the fish**/
    public void onFishRemoved(ItemStack stack, World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
    	return;
    }

    /** This is called whenever a living entity is in the water of the tank as long has hasLivingEffect returns true.
     * You can have your fish species do something special to them if you like It is
     * called every half a second, so if a player is in for less than that, the
     * effect won't apply. This is only called if your fish are active
     * 
     * @param The Entity */
    public void affectLiving(EntityLivingBase entity) {
        return;
    }
    
    /** Helper method for getting entities within an area **/
    public int getCount(Class clazz, World world, ArrayList<CachedCoords> coords) {
        int count = 0;
        for (CachedCoords cord: coords) {
            count += world.getEntitiesWithinAABB(clazz, Blocks.stone.getCollisionBoundingBoxFromPool(world, cord.x, cord.y, cord.z)).size();
        }
        
        return count;
    }
    
    /** Whether this fish allows RF connections to the fish feeder**/
    public boolean canConnectEnergy(ForgeDirection from) {
        return false;
    }

    /** Return the light level this fish gives off, same as normal blocks, from 0-15 **/
    public int getLightValue() {
        return 0;
    }

    //Work Based things
    /** Whether this fish can work int this dimension **/
    public boolean isValidDimensionForWork(World world) {
        return true;
    }

    /** Whether the fish can work at this time of day **/
    public boolean canWorkAtThisTime(boolean isDay) {
        return true;
    }
    
    /** How long the fish lasts before it dies on land, in ticks **/
    public int getOnLandLifespan(FishSpecies fishy) {
        return 15;
    }

    //Catching Based, When/Where/How the fish can be caught
    /** Return the rod Quality needed to catch this fish **/
    public abstract RodType getRodNeeded();

    /** Return whether this type of world is suitable to catch these fish**/
    protected boolean isWorldCorrect(World world) {
        return !world.provider.isHellWorld && world.provider.dimensionId != 1;
    }

    /** Called by the Fishing Handler **/
    public double getCatchChance(World world, int x, int y, int z, Salinity salt, int temp) {
        if (!DISABLE_DIMENSION_CATCHING && !isWorldCorrect(world)) return 0.0D;
        else if (DISABLE_BIOME_CATCHING) {
            return getCatchChance(world, y);
        } else {
            return getCatchChance(world, salt, temp, y);
        }
    }

    /** Return the catch chance based on the variables, return 0 for no catch
     *  -- This method is bypassed if ignore biome catch chance is enabled -- **/
    public double getCatchChance(World world, Salinity salt, int temp, int height) {
        return MaricultureHandlers.environment.matches(salt, temp, getSalinityBase(), getSalinityTolerance(), getTemperatureBase(), getTemperatureTolerance()) ? getCatchChance(world, height) : 0D;
    }

    /** This is called when disable biome catching is active or from the above method **/
    public double getCatchChance(World world, int height) {
        return 0D;
    }

    /** Called by the Fishing Handler **/
    public double getCaughtAliveChance(World world, int x, int y, int z, Salinity salt, int temp) {
        if (!DISABLE_DIMENSION_CATCHING && !isWorldCorrect(world)) return 0.0D;
        else if (DISABLE_BIOME_CATCHING) {
            return getCaughtAliveChance(world, y);
        } else {
            return getCaughtAliveChance(world, salt, temp, y);
        }
    }

    /** Returns the chance for this fish to be caught alive, Called when biome catching is enabled
     * The world
     * The Salinity of the Water
     * The Temperature of the Water
     * The Time of Day of the World
     * The Y Height fishing At
     *  -- This method is bypassed if ignore biome catch chance is enabled -- **/
    public double getCaughtAliveChance(World world, Salinity salt, int temp, int height) {
        return isAcceptedTemperature(temp) && salt == getSalinityBase() ? getCaughtAliveChance(world, height) : 0D;
    }

    /** Called when biome catching is disabled or from the above method **/
    public double getCaughtAliveChance(World world, int height) {
        return 0D;
    }

    /* Language/Icon */
    /** Fish's name **/
    public String getName() {
        return StatCollector.translateToLocal("mariculture.fish.data.species." + getSimpleName());
    }

    /** Gendered Icons?, return true if your fish has a different icon for male or female fish **/
    public boolean hasGenderIcons() {
        return false;
    }

    /** Returns your fish icon 
     * @param gender **/
    public IIcon getIcon(int gender) {
        return hasGenderIcons() && gender == 0 ? altIcon : theIcon;
    }

    /** Called to register your fish icon **/
    public void registerIcon(IIconRegister iconRegister) {
        if (hasGenderIcons()) {
            theIcon = iconRegister.registerIcon(modid + ":fish/" + getSimpleName() + "_female");
            altIcon = iconRegister.registerIcon(modid + ":fish/" + getSimpleName() + "_male");
        } else {
            theIcon = iconRegister.registerIcon(modid + ":fish/" + getSimpleName());
        }
    }
}
