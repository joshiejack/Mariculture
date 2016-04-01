package mariculture.plugins;

import static mariculture.Mariculture.modid;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fish;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.botania.FishMana;
import mariculture.plugins.botania.ItemLivingRod;
import mariculture.plugins.enchiridion.ItemGuide;
import net.minecraft.init.Blocks;
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

    public static final RodType MANA = new RodType("MANA", 90, 10D, 10D, 10D, 50);
    public static FishSpecies mana;
    public static Item rodLiving;

    @Override
    public void preInit() {
        ItemGuide.BOTANIA = true;
        if (Modules.isActive(Modules.fishery)) {
            mana = Fishing.fishHelper.registerFish(modid, FishMana.class, 65);
            Fishing.mutation.addMutation(Fish.electricRay, Fish.clown, mana, 30D);
            Fishing.mutation.addMutation(Fish.electricRay, Fish.ender, mana, 30D);
            Fishing.mutation.addMutation(Fish.electricRay, Fish.blaze, mana, 30D);
            rodLiving = new ItemLivingRod().setUnlocalizedName("rodLiving");
            Fishing.fishing.registerRod(rodLiving, MANA);
        }
    }

    private void addLoot(ItemStack stack, Rarity rarity, double chance) {
        Fishing.fishing.addLoot(new Loot(stack, chance, rarity, Short.MAX_VALUE, MANA, true));
    }

    @Override
    public void init() {
        PluginEnchiridion.registerBookItem("botania", GuideMeta.BOTANIA);
        RecipeHelper.addBookRecipe(new ItemStack(PluginEnchiridion.guides, 1, GuideMeta.BOTANIA), new ItemStack(ModItems.petal, 1, OreDictionary.WILDCARD_VALUE));
        PearlGenHandler.addPearl(OreDictionary.getOres("manaPearl").get(0), GameRegistry.findBlock("Botania", "storage"), 0, 1);

        if (Modules.isActive(Modules.fishery)) {
            GameRegistry.addShapedRecipe(new ItemStack(rodLiving), new Object[] { "  T", " TS", "T S", 'T', new ItemStack(ModItems.manaResource, 1, 3), 'S', new ItemStack(ModItems.manaResource, 1, 16) });

            Fishing.fishing.addBait(new ItemStack(ModItems.petal, 1, OreDictionary.WILDCARD_VALUE), 70);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.HOPPER), MANA);
            Fishing.fishing.addBaitForQuality(new ItemStack(ModItems.petal, 1, OreDictionary.WILDCARD_VALUE), MANA);

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
        
        //Flower Cycling with botania
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.yellow_flower), new ItemStack(Blocks.red_flower, 1, 0), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Blocks.red_flower, 1, 1), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 1), new ItemStack(Blocks.red_flower, 1, 2), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Blocks.red_flower, 1, 3), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 3), new ItemStack(Blocks.red_flower, 1, 4), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 4), new ItemStack(Blocks.red_flower, 1, 5), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 5), new ItemStack(Blocks.red_flower, 1, 6), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 6), new ItemStack(Blocks.red_flower, 1, 7), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 7), new ItemStack(Blocks.red_flower, 1, 8), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.red_flower, 1, 8), new ItemStack(Blocks.yellow_flower), 10);
        
        //Tall Plant cycling with botania
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Blocks.double_plant, 1, 4), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.double_plant, 1, 4), new ItemStack(Blocks.double_plant, 1, 5), 10);
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Blocks.double_plant, 1, 5), new ItemStack(Blocks.double_plant, 1, 0), 10);
    }

    @Override
    public void postInit() {
        
    }
}
