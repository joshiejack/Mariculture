package joshie.maritech.extensions.modules;

import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.helpers.RecipeHelper.addShapeless;
import static joshie.mariculture.core.helpers.RecipeHelper.addUpgrade;
import static joshie.mariculture.core.helpers.RecipeHelper.addVatItemRecipe;
import static joshie.mariculture.core.helpers.RecipeHelper.asStack;
import static joshie.mariculture.core.lib.MCLib.comparator;
import static joshie.mariculture.core.lib.MCLib.limestone;
import static joshie.mariculture.core.lib.MCLib.pearls;
import static joshie.mariculture.core.lib.MCLib.quartz;
import static joshie.mariculture.core.lib.MCLib.redstoneTorch;
import static joshie.mariculture.core.lib.MCLib.repeater;
import static joshie.mariculture.core.lib.MCLib.rubber;
import static joshie.mariculture.core.lib.MCLib.transparent;
import static joshie.mariculture.core.util.Fluids.getFluidName;
import static joshie.mariculture.core.util.Fluids.isRegistered;
import joshie.mariculture.core.lib.MetalRates;
import joshie.mariculture.core.lib.UpgradeMeta;
import joshie.maritech.items.ItemBattery;
import joshie.maritech.lib.MTLib;
import joshie.maritech.util.IModuleExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ExtensionCore implements IModuleExtension {
    public static Item batteryTitanium;
    public static Item batteryCopper;

    @Override
    public void preInit() {
        batteryCopper = new ItemBattery(10000, 100, 250).setUnlocalizedName("battery.copper");
        batteryTitanium = new ItemBattery(100000, 1000, 2500).setUnlocalizedName("battery.titanium");
    }

    @Override
    public void init() {
        addShaped(MTLib.copperBattery, new Object[] { " I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotCopper" });
        addShaped(MTLib.titaniumBattery, new Object[] { " I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotTitanium" });
        addShaped(MTLib.neoprene, new Object[] { "IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', MTLib.bottleGas });
        addShaped(asStack(MTLib.neoprene, 2), new Object[] { "IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', MTLib.bottleGas2 });
        addShaped(MTLib.plasticLens, new Object[] { " N ", "NGN", " N ", 'N', MTLib.neoprene, 'G', transparent });
        addVatItemRecipe(asStack(MTLib.plastic, 4), getFluidName("gold"), MetalRates.BLOCK, MTLib.goldPlastic, 60 * 5);
        
      //Capacitor
        ItemStack previous = addUpgrade(UpgradeMeta.BASIC_RF, new Object[] { " T ", "CRC", 'T', redstoneTorch, 'C', "ingotCopper", 'R', "dustRedstone" });
        previous = addUpgrade(UpgradeMeta.STANDARD_RF, new Object[] { "CTC", "QUQ", "RCR", 'C', "ingotCopper", 'T', redstoneTorch, 'Q', quartz, 'R', repeater, 'U', previous });
        previous = addUpgrade(UpgradeMeta.ADVANCED_RF, new Object[] { " D ", "RUR", "QCQ", 'D', "dustRedstone", 'Q', quartz, 'R', repeater, 'C', MTLib.copperBattery, 'U', previous });
        addUpgrade(UpgradeMeta.ULTIMATE_RF, new Object[] { " C ", "RUR", "QBQ", 'Q', comparator, 'R', "dustRedstone", 'B', "blockRedstone", 'C', MTLib.titaniumBattery, 'U', previous });
        
        ItemStack titanium = MTLib.titaniumBattery.copy();
        titanium.setTagCompound(new NBTTagCompound());
        titanium.stackTagCompound.setInteger("Energy", 100000);
        addShapeless(titanium, new Object[] { MTLib.titaniumBattery, "blockRedstone" });

        ItemStack copper = MTLib.copperBattery.copy();
        copper.setTagCompound(new NBTTagCompound());
        copper.stackTagCompound.setInteger("Energy", 10000);
        addShapeless(copper, new Object[] { MTLib.copperBattery, "blockRedstone" });
        addVatItemRecipe(asStack(limestone, 4), getFluidName("natural_gas"), 5000, MTLib.plastic, 45);
        if (isRegistered("bioethanol")) {
            addVatItemRecipe(asStack(limestone, 4), getFluidName("bioethanol"), 10000, MTLib.plastic, 60);
        }
    }

    @Override
    public void postInit() {
        return;
    }
}
