package maritech.lib;

import mariculture.core.Core;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineRenderedMultiMeta;
import maritech.extensions.modules.ExtensionCore;
import maritech.items.ItemBattery;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MTLib {
    public static final ItemStack extractor = new ItemStack(Core.machines, 1, MachineMeta.EXTRACTOR);
    public static final ItemStack injector = new ItemStack(Core.machines, 1, MachineMeta.INJECTOR);
    public static final ItemStack generator = new ItemStack(Core.machines, 1, MachineMeta.GENERATOR);
    public static final ItemStack sluiceAdvanced = new ItemStack(Core.machines, 1, MachineMeta.SLUICE_ADVANCED);
    public static final ItemStack sluice = new ItemStack(Core.machines, 1, MachineMeta.SLUICE);
    public static final ItemStack pressureVessel = new ItemStack(Core.renderedMachinesMulti, 1, MachineRenderedMultiMeta.PRESSURE_VESSEL);
    public static final ItemStack incubatorBase = new ItemStack(Core.machinesMulti, 1, MachineMultiMeta.INCUBATOR_BASE);
    public static final ItemStack incubatorTop = new ItemStack(Core.machinesMulti, 1, MachineMultiMeta.INCUBATOR_TOP);
    public static final ItemStack autofisher = new ItemStack(Core.machines, 1, MachineMeta.AUTOFISHER);
    public static final ItemStack compressorBase = new ItemStack(Core.renderedMachinesMulti, 1, MachineRenderedMultiMeta.COMPRESSOR_BASE);
    public static final ItemStack compressorTop = new ItemStack(Core.renderedMachinesMulti, 1, MachineRenderedMultiMeta.COMPRESSOR_TOP);
    public static final ItemStack mechSponge = new ItemStack(Core.machines, 1, MachineMeta.SPONGE);
    public static final ItemStack pressurisedBucket = new ItemStack(Core.buckets, 1, BucketMeta.PRESSURE);
    public static final ItemStack plastic = new ItemStack(Core.crafting, 1, CraftingMeta.PLASTIC);
    public static final ItemStack neoprene = new ItemStack(Core.crafting, 1, CraftingMeta.NEOPRENE);
    public static final ItemStack plasticLens = new ItemStack(Core.crafting, 1, CraftingMeta.LENS);
    public static final ItemStack bottleGas2 = new ItemStack(Core.bottles, 1, BottleMeta.GAS);
    public static final ItemStack bottleGas = new ItemStack(Core.bottles, 1, BottleMeta.GAS_BASIC);
    public static final ItemStack copperBattery = ItemBattery.make(new ItemStack(ExtensionCore.batteryCopper, 1, OreDictionary.WILDCARD_VALUE), 0);
    public static final ItemStack titaniumBattery = ItemBattery.make(new ItemStack(ExtensionCore.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE), 0);

}
