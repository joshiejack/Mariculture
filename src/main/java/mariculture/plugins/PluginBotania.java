package mariculture.plugins;

import static mariculture.Mariculture.modid;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fish;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.botania.FishMana;
import mariculture.plugins.botania.ItemLivingRod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginBotania extends Plugin {
    public PluginBotania(String name) {
        super(name);
    }

    public static final RodType MANA = new RodType(90, 10D, 10D, 10D, 50);
    public static FishSpecies mana;
    public static Item rodLiving;

    @Override
    public void preInit() {
        if (Modules.isActive(Modules.fishery)) {
            mana = Fishing.fishHelper.registerFish(modid, FishMana.class, 65);
            Fishing.mutation.addMutation(Fish.koi, Fish.gold, mana, 33D);
            rodLiving = new ItemLivingRod().setUnlocalizedName("rodLiving");
            Fishing.fishing.registerRod(rodLiving, MANA);
        }
    }

    private void addLoot(ItemStack stack, Rarity rarity, double chance) {
        Fishing.fishing.addLoot(new Loot(stack, chance, rarity, Short.MAX_VALUE, MANA, true));
    }

    @Override
    public void init() {
        PearlGenHandler.addPearl(OreDictionary.getOres("manaPearl").get(0), GameRegistry.findBlock("Botania", "storage"), 0, 1);

        if (Modules.isActive(Modules.fishery)) {
            GameRegistry.addShapedRecipe(new ItemStack(rodLiving), new Object[] { "  T", " TS", "T S", 'T', new ItemStack(ModItems.manaResource, 1, 3), 'S', new ItemStack(ModItems.manaResource, 1, 16) });

            Fishing.fishing.addBait(new ItemStack(ModItems.petal, 1, OreDictionary.WILDCARD_VALUE), 60);
            Fishing.fishing.addBait(new ItemStack(ModItems.manaPetal, 1, OreDictionary.WILDCARD_VALUE), 75);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.HOPPER), MANA);
            Fishing.fishing.addBaitForQuality(new ItemStack(ModItems.petal, 1, OreDictionary.WILDCARD_VALUE), MANA);
            Fishing.fishing.addBaitForQuality(new ItemStack(ModItems.manaPetal, 1, OreDictionary.WILDCARD_VALUE), MANA);

            addLoot(new ItemStack(ModItems.recordGaia1), Rarity.RARE, 10);
            addLoot(new ItemStack(ModItems.recordGaia2), Rarity.RARE, 10);
            addLoot(new ItemStack(ModItems.waterRing), Rarity.RARE, 1);
            addLoot(new ItemStack(ModItems.manasteelShears), Rarity.RARE, 2);
            for (int i = 0; i < 16; i++) {
                addLoot(new ItemStack(ModBlocks.flower, 1, i), Rarity.GOOD, 10);
            }

            for (int i = 0; i < 32; i++) {
                addLoot(new ItemStack(ModItems.cosmetic, 1, i), Rarity.JUNK, 10);
            }
        }
    }

    @Override
    public void postInit() {
        for (int id : FishSpecies.species.keySet()) {
            if (id >= 4) {
                BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Items.fish, 1, 0), new ItemStack(Items.fish, 1, id), 10);
            }
        }
    }
}
