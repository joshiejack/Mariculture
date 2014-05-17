package mariculture.fishery;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IFishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.helpers.ReflectionHelper;
import mariculture.core.lib.Extra;
import mariculture.core.util.Rand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;

public class FishingHandler implements IFishing {
	// Registering Fishing Rods
	public static HashMap<Item, RodType> registry = new HashMap();

	@Override
	public RodType getRodType(ItemStack stack) {
		return registry.get(stack.getItem());
	}

	@Override
	public void registerRod(Item item, RodType type) {
		registry.put(item, type);
	}

	// Handling Fishing Rods
	@Override
	public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player) {
		RodType rodType = getRodType(stack);
		if (!rodType.canFish(world, (int) player.posX, (int) player.posY, (int) player.posZ, player, stack))
			return stack;
		int baitQuality = getBait(player, stack)[0];
		int baitSlot = getBait(player, stack)[1];

		if (player.fishEntity != null) {
			rodType.damage(world, player, stack, player.fishEntity.func_146034_e(), world.rand);
			player.swingItem();
		} else if (baitSlot != -1) {
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (Rand.rand.nextFloat() * 0.4F + 0.8F));
			EntityHook hook = new EntityHook(world, player, baitQuality);
			if (!world.isRemote) {
				world.spawnEntityInWorld(hook);
			}

			if (!player.capabilities.isCreativeMode) {
				if (baitQuality > 0)
					player.inventory.decrStackSize(baitSlot, 1);
			}

			player.swingItem();
		}

		return stack;
	}

	public int[] getBait(EntityPlayer player, ItemStack rod) {
		int baitQuality = 0;
		int currentSlot = player.inventory.currentItem;
		int foundSlot = -1;

		if (currentSlot > 0) {
			int leftSlot = currentSlot - 1;

			if (player.inventory.getStackInSlot(leftSlot) != null) {
				if (canUseBait(player.inventory.getStackInSlot(leftSlot), rod)) {
					baitQuality = getBaitQuality(player.inventory.getStackInSlot(leftSlot));
					foundSlot = leftSlot;
				}
			}
		}

		if (foundSlot == -1 && currentSlot < 8) {
			int rightSlot = currentSlot + 1;

			if (player.inventory.getStackInSlot(rightSlot) != null) {
				if (canUseBait(player.inventory.getStackInSlot(rightSlot), rod)) {
					baitQuality = getBaitQuality(player.inventory.getStackInSlot(rightSlot));
					foundSlot = rightSlot;
				}
			}
		}

		return new int[] { baitQuality, foundSlot };
	}

	// Bait Related Handling
	private final HashMap<RodType, ArrayList<List>> canUse = new HashMap();
	private final HashMap<List, Integer> baits = new HashMap();

	@Override
	public void addBait(ItemStack bait, Integer catchRate) {
		baits.put(convert(bait), catchRate);
	}
	
	@Override
	public void addBaitForQuality(ItemStack bait, List<RodType> rods) {
		for(RodType type: rods) {
			addBaitForQuality(bait, type);
		}
	}

	@Override
	public void addBaitForQuality(ItemStack bait, RodType quality) {
		ArrayList<List> baitList = canUse.get(quality);
		if (baitList == null)
			baitList = new ArrayList();
		baitList.add(convert(bait));
		canUse.put(quality, baitList);
	}

	@Override
	public int getBaitQuality(ItemStack bait) {
		List name = convert(bait);
		return baits.containsKey(name) ? baits.get(name) : 0;
	}

	@Override
	public boolean canUseBait(ItemStack rod, ItemStack bait) {
		RodType quality = getRodType(rod);
		if (canUse.containsKey(quality)) {
			ArrayList<List> baitList = canUse.get(quality);
			if (baitList != null && baitList.size() > 0) {
				List list = convert(bait);
				for (List l : baitList) {
					if (l.equals(list))
						return true;
				}

				return false;
			} else
				return false;
		} else
			return false;
	}

	@Override
	public ArrayList<List> getCanUseList(RodType quality) {
		return canUse.get(quality);
	}

	private List convert(ItemStack stack) {
		return Arrays.asList(stack.getItem(), stack.getItemDamage());
	}

	// Loot Based Handling
	public static class LootingList {
		private int dimension;
		private RodType type;

		public LootingList(int dimension, RodType type) {
			this.dimension = dimension;
			this.type = type;
		}
	}
	
	public static final HashMap<Rarity, ArrayList<Loot>> fishing_loot = new HashMap();
	@Override
	public void addLoot(Loot loot) {
		ArrayList<Loot> lootList = fishing_loot.get(loot.rarity);
		if(lootList == null) lootList = new ArrayList();
		lootList.add(loot);
		fishing_loot.put(loot.rarity, lootList);
		if (loot.quality == null) {
			addVanillaLoot(loot.rarity, new WeightedRandomFishable(loot.loot, (int) Math.max((loot.chance/10), 1)));
		}
	}

	// Add Vanilla Junk + Good Loot
	private void addVanillaLoot(Rarity rarity, WeightedRandomFishable loot) {
		try {
			if (rarity == rarity.GOOD || rarity == rarity.RARE) {
				List list = new ArrayList(getFinalStatic(EntityHook.class.getField("field_146041_e")));
				list.add(loot);
				ReflectionHelper.setFinalStatic(EntityHook.class.getField("field_146041_e"), list);
			} else {
				List list = new ArrayList(getFinalStatic(EntityHook.class.getField("field_146039_d")));
				list.add(loot);
				ReflectionHelper.setFinalStatic(EntityHook.class.getField("field_146039_d"), list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List getFinalStatic(Field field) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		return (List) field.get(field);
	}
	
	//Returns whether this dimension is valid or not
	private boolean isDimensionValid(World world, int id) {
		if(id == Short.MAX_VALUE) return true;
		else if (id == 0) return !world.provider.isHellWorld && world.provider.dimensionId != 1;
		else return id == world.provider.dimensionId;
	}
	
	//Returns whether this fishing rod is valid to catch the loot
	private boolean isFishingRodValid(RodType rod, RodType quality, boolean exact) {
		if(quality == null) return true;
		else if(exact) return rod == quality;
		else return rod.getQuality() >= quality.getQuality();
	}
	
	//Returns a fishing loot catch for this location
	private ItemStack getLoot(World world, RodType rod, Rarity rarity) {
		ArrayList<Loot> loots = fishing_loot.get(rarity);
		Collections.shuffle(loots);
		for(Loot loot: loots) {
			if(isDimensionValid(world, loot.dimension) && isFishingRodValid(rod, loot.quality, loot.exact)) {
				double chance = loot.chance * 10;
				if(world.rand.nextInt(1000) < chance) {
					return loot.loot.copy();
				}
			}
		}
		
		List list = EntityFishHook.field_146041_e;
		if(rarity == rarity.JUNK) list = EntityFishHook.field_146039_d;
		return ((WeightedRandomFishable)WeightedRandom.getRandomItem(world.rand, list)).func_150708_a(world.rand);
	}

	@Override
	public ItemStack getCatch(World world, int x, int y, int z, ItemStack stack) {
		if (stack == null) {
			return getFishForLocation(world, x, y, z, RodType.NET);
		} else {
			RodType type = getRodType(stack);
			if (type != null) {
				ItemStack loot = null;
				int lootBonus = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151370_z.effectId, stack);
				if((lootBonus > 0 && world.rand.nextInt(200) < (lootBonus * 10)) || world.rand.nextInt(1000) < Math.max(0.01D, type.getChances().get(0))) {
					loot = getLoot(world, type, Rarity.RARE);
				} else {
					double chance = Math.max(0.01D, type.getChances().get(1));
					if((lootBonus > 0 && world.rand.nextInt(250) < (lootBonus * 10)) || world.rand.nextInt(1000) < Math.max(0.01D, type.getChances().get(1))) {
						loot = getLoot(world, type, Rarity.GOOD);
					} else if ((lootBonus > 0 && world.rand.nextInt(300) < (lootBonus * 10)) || world.rand.nextInt(1000) < Math.max(0.01D, type.getChances().get(2))) {
						loot = getLoot(world, type, Rarity.JUNK);
					}
				}
				
				if(loot == null) {
					return getFishForLocation(world, x, y, z, type);
				} else {
					if(loot.isItemEnchantable()) {
						if(world.rand.nextInt(100) < type.getQuality()) {
							int chance = type.getLootEnchantmentChance(); 
							if (chance > 0) {
								EnchantmentHelper.addRandomEnchantment(world.rand, loot, world.rand.nextInt(chance));
							}
						}
					}
					
					return loot;
				}
			} else return new ItemStack(Items.stick);
		}
	}

	public static ArrayList<FishSpecies> catchables;
	private ItemStack getFishForLocation(World world, int x, int y, int z, RodType type) {
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
				if(catchChance > 0 && type.getQuality() >= fish.getRodNeeded().getQuality() && world.rand.nextInt(1000) < catchChance) {
					if(Extra.IGNORE_BIOMES) {
							return catchFish(world.rand, fish, type, fish.getCaughtAliveChance(world, y, time) * 15D);
					} else return catchFish(world.rand, fish, type, fish.getCaughtAliveChance(world, salt, temperature, time, y) * 10D);
				}
			}
		}

		return null;
	}
	
	private ItemStack catchFish(Random rand, FishSpecies fish, RodType quality, double chance) {
		boolean alive = false;
		if(rand.nextInt(1000) < chance) alive = true;
		boolean catchAlive = quality.caughtAlive(fish.getSpecies());
		if(!catchAlive && !alive) return new ItemStack(Items.fish, 1, fish.getID());
		return Fishing.fishHelper.makePureFish(fish);
	}
}
