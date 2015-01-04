package maritech.extensions.modules;

import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addShapeless;
import static mariculture.core.helpers.RecipeHelper.addVatItemRecipe;
import static mariculture.core.helpers.RecipeHelper.asStack;
import static mariculture.core.lib.MCLib.baseWood;
import static mariculture.core.lib.MCLib.goldSilk;
import static mariculture.core.lib.MCLib.greyClay;
import static mariculture.core.lib.MCLib.heating;
import static mariculture.core.lib.MCLib.life;
import static mariculture.core.lib.MCLib.string;
import static mariculture.core.lib.MCLib.thermometer;
import static mariculture.core.lib.MCLib.titaniumRod;
import static mariculture.core.lib.MCLib.whiteClay;
import static mariculture.core.util.Fluids.getFluidName;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.core.lib.MCLib;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import mariculture.lib.helpers.RegistryHelper;
import maritech.items.ItemBattery;
import maritech.items.ItemFishDNA;
import maritech.items.ItemFluxRod;
import maritech.lib.MTLib;
import maritech.tile.TileAutofisher;
import maritech.tile.TileExtractor;
import maritech.tile.TileIncubator;
import maritech.tile.TileInjector;
import maritech.util.IModuleExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ExtensionFishery implements IModuleExtension {
    public static Item dna;
    public static Item rodFlux;

    @Override
    public void preInit() {
        dna = new ItemFishDNA().setUnlocalizedName("dna");
        rodFlux = new ItemFluxRod().setUnlocalizedName("rod.flux");
        RegistryHelper.registerTiles("Mariculture", new Class[] { TileAutofisher.class, TileIncubator.class, TileExtractor.class, TileInjector.class });
    }

    @Override
    public void init() {
        addShaped(MTLib.incubatorTop, new Object[] { "DFD", "CHC", 'F', "fish", 'D', "dyeBrown", 'C', greyClay, 'H', heating });
        addShaped(MTLib.incubatorBase, new Object[] { "DBD", "CHC", 'C', whiteClay, 'B', MTLib.copperBattery, 'D', "dyeLightBlue", 'H', heating });
        addShaped(MTLib.autofisher, new Object[] { " F ", "RPR", "WBW", 'W', "logWood", 'R', asStack(Fishery.rodWood), 'F', "fish", 'B', baseWood, 'P', "plankWood" });
        addShaped(ItemBattery.make(asStack(ExtensionFishery.rodFlux), 0), new Object[] { "  R", " RS", "B S", 'R', Fishery.rodTitanium, 'S', string, 'B', MTLib.titaniumBattery });
        ItemStack charged = new ItemStack(ExtensionFishery.rodFlux);
        charged.setTagCompound(new NBTTagCompound());
        charged.stackTagCompound.setInteger("Energy", 250000);
        addShapeless(charged, new Object[] { asStack(ExtensionFishery.rodFlux), "blockRedstone" });
        addVatItemRecipe(charged, getFluidName("flux"), 1000, asStack(ExtensionFishery.rodFlux), 15);
        
        ItemStack scuba = Modules.isActive(Modules.diving)? asStack(ExtensionDiving.scubaMask): MCLib.fishSorter;
        ItemStack turbine = Modules.isActive(Modules.factory)? asStack(ExtensionFactory.turbineTitanium): MCLib.ironWheel;
        ItemStack gold = Modules.isActive(Modules.factory)? MTLib.goldPlastic: goldSilk;
        addShaped(MTLib.extractor, new Object[] { " H ", "PSP", "TBT", 'H', life, 'P', titaniumRod, 'S', Fishery.scanner, 'T', turbine, 'B', "blockTitanium" });
        addShaped(MTLib.injector, new Object[] { " S ", "GTG", "BEB", 'S', scuba, 'G', gold, 'T', thermometer, 'B', ExtensionCore.batteryTitanium, 'E', MTLib.extractor });
        
        Fishing.fishing.registerRod(rodFlux, RodType.FLUX);
    }

    @Override
    public void postInit() {
        if (Modules.isActive(Modules.factory)) {
            Fishing.fishing.addLoot(new Loot(new ItemStack(ExtensionFactory.fludd), 1D, Rarity.RARE, 0, RodType.SUPER));
        }
    }
}
