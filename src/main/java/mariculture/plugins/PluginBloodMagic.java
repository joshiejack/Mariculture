package mariculture.plugins;

import static mariculture.api.fishery.Loot.Rarity.GOOD;
import static mariculture.api.fishery.Loot.Rarity.JUNK;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.Modules;
import mariculture.core.util.Text;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.bloodmagic.BloodRodType;
import mariculture.plugins.bloodmagic.ItemBoundRod;
import mariculture.plugins.bloodmagic.ItemMobMagnetBloodEdition;
import mariculture.plugins.bloodmagic.RitualOfTheBloodRiver;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;

public class PluginBloodMagic extends Plugin {
    public static final RodType BLOOD = new BloodRodType(95, 15D, 20D, 5D, 45);
    public static Item rodBlood;

    @Override
    public void preInit() {
        rodBlood = new ItemBoundRod().setUnlocalizedName("rodBlood");
        RegistryHelper.registerItems(new Item[] { rodBlood });
        Fishing.fishing.registerRod(rodBlood, BLOOD);
    }

    @Override
    public void init() {
        if (Modules.isActive(Modules.fishery)) {
            // Fishing
            Fishing.fishing.addBait(new ItemStack(Items.rotten_flesh), 35);
            Fishing.fishing.addBait(new ItemStack(ModItems.weakBloodShard), 75);
            Fishing.fishing.addBait(new ItemStack(ModItems.demonBloodShard), 100);
            Fishing.fishing.addBaitForQuality(new ItemStack(Items.rotten_flesh), BLOOD);
            Fishing.fishing.addBaitForQuality(new ItemStack(ModItems.weakBloodShard), BLOOD);
            Fishing.fishing.addBaitForQuality(new ItemStack(ModItems.demonBloodShard), BLOOD);

            addLoot(new ItemStack(ModItems.blankSlate), JUNK, 10);
            addLoot(new ItemStack(Blocks.web), JUNK, 10);
            addLoot(new ItemStack(ModItems.baseItems, 1, 3), GOOD, 10);
            addLoot(new ItemStack(ModItems.baseAlchemyItems, 1, 5), GOOD, 10);

            addLoot(new ItemStack(ModItems.simpleCatalyst), GOOD, 7);
            addLoot(new ItemStack(ModItems.baseItems, 1, 0), GOOD, 7);

            addLoot(new ItemStack(ModItems.weakFillingAgent), JUNK, 15);
            addLoot(new ItemStack(ModItems.baseAlchemyItems, 1, 3), JUNK, 15);
            addLoot(new ItemStack(ModItems.baseItems, 1, 1), GOOD, 15);
            addLoot(new ItemStack(ModItems.reinforcedSlate), JUNK, 20);
            addLoot(new ItemStack(ModItems.alchemyFlask), GOOD, 40);

            addLoot(new ItemStack(ModItems.standardFillingAgent), JUNK, 50);
            addLoot(new ItemStack(ModItems.aether), GOOD, 50);
            addLoot(new ItemStack(ModItems.baseAlchemyItems, 1, 4), GOOD, 45);
            addLoot(new ItemStack(ModItems.baseItems, 1, 4), GOOD, 45);

            addLoot(new ItemStack(ModItems.imbuedSlate), JUNK, 75);
            addLoot(new ItemStack(ModItems.enhancedFillingAgent), GOOD, 75);
            addLoot(new ItemStack(ModItems.itemKeyOfDiablo), GOOD, 75);
            addLoot(new ItemStack(ModItems.boundBoots), GOOD, 200);

            // Rituals
            Rituals.ritualList.add(new Rituals(1, 50000, new RitualOfTheBloodRiver(), Text.translate("ritual")));
            BindingRegistry.registerRecipe(new ItemStack(rodBlood), new ItemStack(Fishery.rodTitanium));
        }

        if (Modules.isActive(Modules.magic)) {
            Magic.magnet = new ItemMobMagnetBloodEdition(0).setUnlocalizedName("mobMagnet");
        }
    }

    private void addLoot(ItemStack stack, Rarity rarity, double chance) {
        Fishing.fishing.addLoot(new Loot(stack, chance, rarity, Short.MAX_VALUE, BLOOD, true));
    }

    @Override
    public void postInit() {

    }
}
