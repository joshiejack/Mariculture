package mariculture.fishery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ILootHandler;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Extra;
import mariculture.core.util.Rand;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class LootHandler implements ILootHandler {
	private static final ArrayList<Loot> goodies = new ArrayList<Loot>();
	private static final ArrayList<Loot> junk = new ArrayList<Loot>();
	
	@Override
	public void addLoot(Loot loot) {
		if(loot.type == Rarity.GOOD) {
			goodies.add(loot);
		} else junk.add(loot);
	}
	
	//Used to determine whether the dimension is valid for catching the loot
	private boolean dimensionMatches(World world, int dimension) {
		int id = world.provider.dimensionId;
		if(dimension == OreDictionary.WILDCARD_VALUE) return true;
		if(dimension == 0) {
			return id == 0 || dimension > 1 || dimension < - 1;
		} else return dimension == id;
	}
	
	//Determines whether this rod is calid
	private boolean rodIsAccepted(boolean exact, RodQuality quality, RodQuality rod) {
		if(exact) {
			 return quality == rod;
		} else return rod.getRank() >= quality.getRank();
	}
	
	//Returns an item from the loot list, attempts to fetch one ten times
	public ItemStack getOtherLoot(Random rand, RodQuality quality, World world) {
		for(int i = 0; i < 10; i++) {
			ArrayList<Loot> list = (rand.nextInt(100) <= quality.getRatio())? goodies: junk;
			Collections.shuffle(list);
			Iterator it = list.iterator();
			while(it.hasNext()) {
				Loot loot = (Loot) it.next();
				if(dimensionMatches(world, loot.dimension) && rodIsAccepted(loot.exact, loot.quality, quality)) {
					if(Rand.nextInt(loot.rarity)) {
						ItemStack ret = loot.loot.copy();
						if (ret.isItemEnchantable() && !ret.isItemEnchanted()) {
							if (quality.getRank() > quality.GOOD.getRank()) {
								int enchant = quality.getEnchantability();
								enchant *= ret.itemID == Item.book.itemID ? 2 : 1;
								EnchantmentHelper.addRandomEnchantment(rand, ret, enchant);
							}
						}
	
						if (ret.isItemStackDamageable()) {
							if (quality.getRank() < quality.GOOD.getRank()) {
								int dmg = rand.nextInt(ret.getMaxDamage());
								ret.setItemDamage(dmg);
							}
						}
						
						return ret;
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public ItemStack getLoot(Random rand, RodQuality quality, World world, int x, int y, int z) {
		ItemStack fish = getFishForLocation(rand, quality, world, x, y, z);
		if(fish == null) {
			ItemStack ret = getOtherLoot(rand, quality, world);
			if(ret != null) return ret;
			else if(world.provider.isHellWorld) return new ItemStack(Item.fishCooked);
			else if(world.provider.dimensionId == 1) return new ItemStack(Item.enderPearl);
			else return new ItemStack(Item.fishRaw);
		} else return fish;
	}
	
	public static ArrayList<FishSpecies> catchables;
	private ItemStack getFishForLocation(Random rand, RodQuality quality, World world, int x, int y, int z) {
		//Creates the catchable fish list if it doesn't exist
		if(catchables == null) {
			catchables = new ArrayList();
			for(Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
				catchables.add(species.getValue());
			}
		}
		
		Salinity salt = MaricultureHandlers.environment.getSalinity(world, x, z);
		int temperature = MaricultureHandlers.environment.getTemperature(world, x, y, z);
		int time = Time.getTime(world);
		for(int i = 0; i < 2; i++) {
			Collections.shuffle(catchables);
			for(FishSpecies fish: catchables) {
				double multiplier = Extra.IGNORE_BIOMES? 1.0D: 5D;
				double catchChance = (Extra.IGNORE_BIOMES? fish.getCatchChance(world, y, time): fish.getCatchChance(world, salt, temperature, time, y)) * multiplier;
				if(quality.getEnum() != null && fish.canCatch(rand, world, x, y, z, quality.getEnum())) {
					if(Extra.IGNORE_BIOMES) {
						return catchFish(rand, fish, quality, fish.getCaughtAliveChance(world, y, time) * 10D);
					} else return catchFish(rand, fish, quality, fish.getCaughtAliveChance(world, salt, temperature, time, y) * 10D);
				}
					
				if(catchChance > 0 && quality.getRank() >= fish.getRodNeeded().getRank() && rand.nextInt(1000) < catchChance) {
					if(Extra.IGNORE_BIOMES) {
							return catchFish(rand, fish, quality, fish.getCaughtAliveChance(world, y, time) * 15D);
					} else return catchFish(rand, fish, quality, fish.getCaughtAliveChance(world, salt, temperature, time, y) * 10D);
				}
			}
		}

		return null;
	}
	
	private ItemStack catchFish(Random rand, FishSpecies fish, RodQuality quality, double chance) {
		boolean alive = false;
		if(rand.nextInt(1000) < chance) alive = true;
		boolean catchAlive = quality.caughtAlive(fish.getSpecies());
		if(!catchAlive && !alive) return new ItemStack(Fishery.fishyFood, 1, fish.getID());
		return Fishing.fishHelper.makePureFish(fish);
	}
	
	@Deprecated
	@Override
	public void addLoot(ItemStack stack, Object... args) {
		if(args.length == 2) {
			if(args[0] instanceof EnumRodQuality) {
				junk.add(new Loot(stack, ((EnumRodQuality) args[0]).getQuality(), Rarity.JUNK, (Integer) args[1], OreDictionary.WILDCARD_VALUE, false));
			} else junk.add(new Loot(stack, (RodQuality) args[0], Rarity.JUNK, (Integer) args[1], OreDictionary.WILDCARD_VALUE, false));
		} else if(args.length == 3 && args[2] instanceof Integer) {
			if(args[0] instanceof EnumRodQuality) {
				junk.add(new Loot(stack, ((EnumRodQuality) args[0]).getQuality(), Rarity.JUNK, (Integer) args[1], (Integer) args[2], false));
			} else junk.add(new Loot(stack, (RodQuality) args[0], Rarity.JUNK, (Integer) args[1], (Integer) args[2], false));
		} else if(args.length == 3) {
			junk.add(new Loot(stack, (RodQuality) args[0], Rarity.JUNK, (Integer) args[1], OreDictionary.WILDCARD_VALUE, (Boolean)args[2]));
		} else if(args.length == 4) {
			junk.add(new Loot(stack, (RodQuality) args[0], Rarity.JUNK, (Integer) args[1], (Integer) args[2], (Boolean)args[3]));
		}
	}

	@Override
	public ItemStack getLoot(Random rand, EnumRodQuality quality, World world, int x, int y, int z) {
		return getLoot(rand, quality.getQuality(), world, x, y, z);
	}
}
