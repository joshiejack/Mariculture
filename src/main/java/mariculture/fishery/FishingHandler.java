package mariculture.fishery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ICaughtAliveModifier;
import mariculture.api.fishery.IFishing;
import mariculture.api.fishery.IHelmetFishManipulator;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics;
import mariculture.core.config.Vanilla;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.util.RecipeItem;
import mariculture.fishery.items.ItemVanillaRod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.oredict.OreDictionary;

public class FishingHandler implements IFishing {
    // Registering Fishing Rods
    private static final HashMap<Item, RodType> registry = new HashMap();

    @Override
    public RodType getRodType(ItemStack stack) {
        return stack != null ? registry.get(stack.getItem()) : null;
    }

    @Override
    public void registerRod(Item item, RodType type) {
        registry.put(item, type);
    }

    private boolean isVanillaRod(ItemStack stack) {
        return stack.getItem() instanceof ItemVanillaRod;
    }

    // Handling Fishing Rods
    @Override
    public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player) {
        RodType rodType = getRodType(stack);
        if (!world.isRemote && !rodType.canFish(world, (int) player.posX, (int) player.posY, (int) player.posZ, player, stack)) return stack;
        int baitQuality = getBait(player, stack)[0];
        int baitSlot = getBait(player, stack)[1];
        if (player.fishEntity != null) {
            rodType.damage(world, player, stack, player.fishEntity.func_146034_e(), world.rand);
            player.swingItem();
        } else if (baitSlot != -1 || !Vanilla.VANILLA_FORCE && stack.getItem() instanceof ItemVanillaRod) {
            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
            if (!Vanilla.VANILLA_POOR && isVanillaRod(stack)) {
                baitQuality = 35;
            }

            EntityHook hook = new EntityHook(world, player, rodType.getDamage(), baitQuality);
            if (!world.isRemote) {
                world.spawnEntityInWorld(hook);
            }

            if (!player.capabilities.isCreativeMode && baitSlot != -1) {
                if (baitQuality > 0) {
                    player.inventory.decrStackSize(baitSlot, 1);
                }
            }

            player.swingItem();
        }

        return stack;
    }

    private int[] getBait(EntityPlayer player, ItemStack rod) {
        int baitQuality = 0;
        int currentSlot = player.inventory.currentItem;
        int foundSlot = -1;

        if (currentSlot > 0) {
            int leftSlot = currentSlot - 1;
            if (player.inventory.getStackInSlot(leftSlot) != null) if (canUseBait(rod, player.inventory.getStackInSlot(leftSlot))) {
                baitQuality = getBaitQuality(player.inventory.getStackInSlot(leftSlot));
                foundSlot = leftSlot;
            }
        }

        if (foundSlot == -1 && currentSlot < 8) {
            int rightSlot = currentSlot + 1;
            if (player.inventory.getStackInSlot(rightSlot) != null) if (canUseBait(rod, player.inventory.getStackInSlot(rightSlot))) {
                baitQuality = getBaitQuality(player.inventory.getStackInSlot(rightSlot));
                foundSlot = rightSlot;
            }
        }

        return new int[] { baitQuality, foundSlot };
    }

    // Bait Related Handling
    private static final HashMap<RodType, ArrayList<ItemStack>> canUse = new HashMap();
    private static final HashMap<List, Integer> baits = new HashMap();

    @Override
    public void addBait(ItemStack bait, Integer catchRate) {
        baits.put(Arrays.asList(bait.getItem(), bait.getItemDamage()), catchRate);
    }

    @Override
    public void addBaitForQuality(ItemStack bait, List<RodType> rods) {
        for (RodType type : rods) {
            addBaitForQuality(bait, type);
        }
    }

    @Override
    public void addBaitForQuality(ItemStack bait, RodType quality) {
        ArrayList<ItemStack> baitList = canUse.get(quality);
        if (baitList == null) {
            baitList = new ArrayList();
        }

        baitList.add(bait);
        canUse.put(quality, baitList);
    }

    @Override
    public int getBaitQuality(ItemStack bait) {
        Integer i = baits.get(Arrays.asList(bait.getItem(), bait.getItemDamage()));
        if (i == null) i = baits.get(Arrays.asList(bait.getItem(), OreDictionary.WILDCARD_VALUE));
        return i == null ? 0 : i;
    }

    @Override
    public boolean canUseBait(ItemStack rod, ItemStack bait) {
        RodType quality = getRodType(rod);
        if (canUse.containsKey(quality)) {
            ArrayList<ItemStack> baitList = canUse.get(quality);
            if (baitList != null && baitList.size() > 0) {
                for (ItemStack l : baitList)
                    if (RecipeItem.equals(bait, l)) return true;

                return false;
            } else return false;
        } else return false;
    }

    @Override
    public ArrayList<ItemStack> getCanUseList(RodType quality) {
        return canUse.get(quality);
    }

    private static final HashMap<Rarity, ArrayList<Loot>> fishing_loot = new HashMap();

    @Override
    public void addLoot(Loot loot) {
        ArrayList<Loot> lootList = fishing_loot.get(loot.rarity);
        if (lootList == null) {
            lootList = new ArrayList();
        }

        lootList.add(loot);
        fishing_loot.put(loot.rarity, lootList);
        if (loot.quality == null) {
            addVanillaLoot(loot.rarity, new WeightedRandomFishable(loot.loot, (int) Math.max(loot.chance / 10, 1)));
        }
    }

    // Add Vanilla Junk + Good Loot
    private void addVanillaLoot(Rarity rarity, WeightedRandomFishable loot) {
        if (rarity == Rarity.GOOD || rarity == Rarity.RARE) {
            FishingHooks.addTreasure(loot);
        } else {
            FishingHooks.addJunk(loot);
        }
    }

    //Returns whether this dimension is valid or not
    private boolean isDimensionValid(World world, int id) {
        if (id == Short.MAX_VALUE) return true;
        else if (id == 0) return !world.provider.isHellWorld && world.provider.dimensionId != 1;
        else return id == world.provider.dimensionId;
    }

    //Returns whether this fishing rod is valid to catch the loot
    private boolean isFishingRodValid(RodType rod, RodType quality, boolean exact) {
        if (quality == null) return true;
        else if (exact) return rod == quality;
        else return rod.getQuality() >= quality.getQuality();
    }

    //Returns a fishing loot catch for this location
    private ItemStack getLoot(World world, RodType rod, Rarity rarity) {
        ArrayList<Loot> loots = fishing_loot.get(rarity);
        Collections.shuffle(loots);
        for (Loot loot : loots) {
            if (loot.loot.getItem() != null) {
                if (isDimensionValid(world, loot.dimension) && isFishingRodValid(rod, loot.quality, loot.exact)) {
                    double chance = loot.chance * 10;
                    if (world.rand.nextInt(1000) < chance) return loot.loot.copy();
                }
            }
        }

        return rarity == Rarity.JUNK ? FishingHooks.getRandomFishable(world.rand, 0.05F) : FishingHooks.getRandomFishable(world.rand, 0.1F);
    }

    private ItemStack getVanillaLoot(World world, EntityPlayer player, ItemStack stack, int loot, int speed) {
        float f = world.rand.nextFloat();
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151370_z.effectId, stack) + loot;
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151369_A.effectId, stack) + speed;
        if (player != null) {
            player.addStat(net.minecraftforge.common.FishingHooks.getFishableCategory(f, i, j).stat, 1);
        }

        return net.minecraftforge.common.FishingHooks.getRandomFishable(world.rand, f, i, j);
    }

    @Override
    public ItemStack getCatch(World world, int x, int y, int z, EntityPlayer player, ItemStack stack) {
        if (stack == null) return getFishForLocation(player, world, x, y, z, RodType.NET);
        else {
            RodType type = getRodType(stack);
            if (Vanilla.VANILLA_LOOT && type == RodType.DIRE) return getVanillaLoot(world, player, stack, 0, 0);
            if (type != null) {
                ItemStack loot = null;
                
                // Vanilla Luck of the Sea increases treasure and decreases junk chance by roughly 2% 
                // Trying to emulate this here
                int lootBonus = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151370_z.effectId, stack) * 2;
                
                if (world.rand.nextInt(1000) < Math.max(0, type.getChances().get(2) + lootBonus) * 10) {
                    loot = getLoot(world, type, Rarity.RARE);
                } else {
                    if (world.rand.nextInt(1000) < Math.max(0, type.getChances().get(1) + lootBonus) * 10) {
                        loot = getLoot(world, type, Rarity.GOOD);
                    } else if (world.rand.nextInt(1000) < Math.max(0, type.getChances().get(0) - lootBonus) * 10) {
                        loot = getLoot(world, type, Rarity.JUNK);
                    }
                }

                boolean forceLoot = false;
                boolean forceFish = false;
                Item helmet = PlayerHelper.getHelmet(player);
                if (helmet instanceof IHelmetFishManipulator) {
                    IHelmetFishManipulator manipulator = ((IHelmetFishManipulator) helmet);
                    ItemStack stackHat = PlayerHelper.getArmorStack(player, ArmorSlot.HAT);
                    forceLoot = manipulator.forceLoot(stackHat);
                    forceFish = manipulator.forceFish(stackHat);
                }

                if (!forceLoot && (loot == null || forceFish)) return getFishForLocation(player, world, x, y, z, type);
                else {
                    if (loot == null) {
                        if (world.rand.nextInt(100) < type.getQuality()) {
                            loot = net.minecraftforge.common.FishingHooks.getRandomFishable(world.rand, 0.125F, 0, 0);
                        } else {
                            loot = net.minecraftforge.common.FishingHooks.getRandomFishable(world.rand, 0.025F, 0, 0);
                        }
                    }

                    if (loot == null) loot = new ItemStack(Items.stick);
                    if (loot.isItemEnchantable()) {
                        if (world.rand.nextInt(100) < type.getQuality()) {
                            int chance = type.getLootEnchantmentChance();
                            if (chance > 0) {
                                EnchantmentHelper.addRandomEnchantment(world.rand, loot, world.rand.nextInt(chance));
                            }
                        }
                    }

                    if (helmet instanceof IHelmetFishManipulator) {
                        loot = ((IHelmetFishManipulator) helmet).getLoot(PlayerHelper.getArmorStack(player, ArmorSlot.HAT), loot);
                    }

                    return loot;
                }
            } else return new ItemStack(Items.stick);
        }
    }

    public static ArrayList<FishSpecies> catchables;

    private ItemStack getFishForLocation(EntityPlayer player, World world, int x, int y, int z, RodType type) {
        double modifier = 10D;
        if (player != null) {
            for (ItemStack stack : player.inventory.armorInventory) {
                if (stack != null && stack.getItem() != null && stack.getItem() instanceof ICaughtAliveModifier) {
                    modifier += ((ICaughtAliveModifier) stack.getItem()).getModifier(stack);
                }
            }
        }

        //Creates the catchable fish list if it doesn't exist
        if (catchables == null) {
            catchables = new ArrayList();
            for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
                catchables.add(species.getValue());
            }
        }

        Salinity salt = MaricultureHandlers.environment.getSalinity(world, x, z);
        int temperature = MaricultureHandlers.environment.getTemperature(world, x, y, z);
        int yHeight = y;
        World fakeWorld = world;

        Item helmet = PlayerHelper.getHelmet(player);
        if (helmet instanceof IHelmetFishManipulator) {
            IHelmetFishManipulator manipulator = ((IHelmetFishManipulator) helmet);
            ItemStack stack = PlayerHelper.getArmorStack(player, ArmorSlot.HAT);
            salt = manipulator.getSalt(stack, salt);
            temperature = manipulator.getTemperature(stack, temperature);
            yHeight = manipulator.getYHeight(stack, yHeight);
            fakeWorld = manipulator.getWorld(stack, fakeWorld);
        }

        for (int i = 0; i < 30; i++) {
            Collections.shuffle(catchables);
            for (FishSpecies fish : catchables) {
                double catchChance = fish.getCatchChance(fakeWorld, x, yHeight, z, salt, temperature);
                if (catchChance > 0 && type.getQuality() >= fish.getRodNeeded().getQuality() && fakeWorld.rand.nextInt(1000) < catchChance) {
                    if (FishMechanics.IGNORE_BIOMES) catchFish(player, fakeWorld.rand, fish, type, fish.getCaughtAliveChance(fakeWorld, yHeight) * (modifier * 1.5D));
                    else return catchFish(player, fakeWorld.rand, fish, type, fish.getCaughtAliveChance(fakeWorld, x, yHeight, z, salt, temperature) * (modifier));
                }
            }
        }

        return new ItemStack(Items.stick);
    }

    private ItemStack catchFish(EntityPlayer player, Random rand, FishSpecies fish, RodType quality, double chance) {
        boolean alive = false;
        if (rand.nextInt(1000) < chance * FishMechanics.ALIVE_MODIFIER) {
            alive = true;
        }

        boolean catchAlive = quality.caughtAlive(fish.getSpecies());
        ItemStack ret = Fishing.fishHelper.makePureFish(fish);
        Item helmet = PlayerHelper.getHelmet(player);
        if (helmet instanceof IHelmetFishManipulator) {
            IHelmetFishManipulator manipulator = ((IHelmetFishManipulator) helmet);
            ItemStack stack = PlayerHelper.getArmorStack(player, ArmorSlot.HAT);
            if (alive) { //If the fish is alive, check if it's always meant to be dead
                alive = !manipulator.alwaysDead(stack);
            }

            if (alive) { //Add the dna to fish, for determing gender
                Fish.gender.addDNA(ret, manipulator.getGender(stack));
                ret = manipulator.getLoot(stack, ret);
            }
        }

        if (!catchAlive && !alive) return fish.getRawForm(1);
        else return ret;
    }
}
