package maritech.extensions.modules;

import static mariculture.core.helpers.RecipeHelper.addAnvilRecipe;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addShapeless;
import static mariculture.core.helpers.RecipeHelper.addUpgrade;
import static mariculture.core.helpers.RecipeHelper.addVatItemRecipe;
import static mariculture.core.helpers.RecipeHelper.asStack;
import static mariculture.core.lib.MCLib.comparator;
import static mariculture.core.lib.MCLib.limestone;
import static mariculture.core.lib.MCLib.pearls;
import static mariculture.core.lib.MCLib.quartz;
import static mariculture.core.lib.MCLib.redstone;
import static mariculture.core.lib.MCLib.redstoneTorch;
import static mariculture.core.lib.MCLib.repeater;
import static mariculture.core.lib.MCLib.rubber;
import static mariculture.core.lib.MCLib.transparent;
import static mariculture.core.util.Fluids.getFluidName;
import static mariculture.core.util.Fluids.isRegistered;
import mariculture.core.lib.UpgradeMeta;
import maritech.items.ItemBattery;
import maritech.lib.MTLib;
import maritech.util.IModuleExtension;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

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
        addShaped(asStack(MTLib.neoprene, 2), new Object[] { "IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', MTLib.bottleGas });
        addShaped(asStack(MTLib.neoprene, 4), new Object[] { "IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', MTLib.bottleGas2 });
        addShaped(MTLib.plasticLens, new Object[] { " N ", "NGN", " N ", 'N', MTLib.neoprene, 'G', transparent });
        addShapeless(ItemBattery.make(asStack(batteryCopper), 10000), new Object[] { redstone, asStack(batteryCopper, OreDictionary.WILDCARD_VALUE) });
        addShapeless(ItemBattery.make(asStack(batteryTitanium), 10000), new Object[] { redstone, asStack(batteryTitanium, OreDictionary.WILDCARD_VALUE), redstone });
        
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
        addVatItemRecipe(asStack(limestone, 4), getFluidName("natural_gas"), 5000, asStack(MTLib.plastic, 4), 20);
        if (isRegistered("bioethanol")) {
            addVatItemRecipe(asStack(limestone, 4), getFluidName("bioethanol"), 10000, asStack(MTLib.plastic, 3), 60);
        }
    }

    @Override
    public void postInit() {
        return;
    }
}
