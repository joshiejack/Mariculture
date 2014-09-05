package joshie.maritech.extensions.modules;

import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.helpers.RecipeHelper.asStack;
import static joshie.mariculture.core.lib.MCLib.baseIron;
import static joshie.mariculture.core.lib.MCLib.comparator;
import static joshie.mariculture.core.lib.MCLib.hopper;
import static joshie.mariculture.core.lib.MCLib.ironBars;
import static joshie.mariculture.core.lib.MCLib.ironWheel;
import static joshie.mariculture.core.lib.MCLib.life;
import static joshie.mariculture.core.lib.MCLib.quartzSlab;
import static joshie.mariculture.core.lib.MCLib.sponge;
import static joshie.mariculture.core.lib.MCLib.stoneSlab;
import static joshie.mariculture.core.lib.MCLib.tank;
import static joshie.mariculture.core.lib.MCLib.titaniumSheet;
import static joshie.mariculture.core.lib.MCLib.transparent;
import static joshie.mariculture.core.lib.MCLib.water;
import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.EntityIds;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.core.lib.UpgradeMeta;
import joshie.mariculture.core.network.PacketHandler;
import joshie.maritech.entity.EntityFLUDDSquirt;
import joshie.maritech.handlers.MTWorldGen;
import joshie.maritech.items.ItemFLUDD;
import joshie.maritech.items.ItemRotor;
import joshie.maritech.lib.MTLib;
import joshie.maritech.lib.MTRenderIds;
import joshie.maritech.network.PacketFLUDD;
import joshie.maritech.network.PacketRotorSpin;
import joshie.maritech.network.PacketSponge;
import joshie.maritech.tile.TileFLUDDStand;
import joshie.maritech.tile.TileGenerator;
import joshie.maritech.tile.TilePressureVessel;
import joshie.maritech.tile.TileRotor;
import joshie.maritech.tile.TileRotorAluminum;
import joshie.maritech.tile.TileRotorCopper;
import joshie.maritech.tile.TileRotorTitanium;
import joshie.maritech.tile.TileSluice;
import joshie.maritech.tile.TileSluiceAdvanced;
import joshie.maritech.tile.TileSponge;
import joshie.maritech.util.IModuleExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class ExtensionFactory implements IModuleExtension {
    private static ArmorMaterial armorFLUDD = EnumHelper.addArmorMaterial("FLUDD", 0, new int[] { 0, 0, 0, 0 }, 0);
    public static Item fludd;
    public static Item turbineAluminum;
    public static Item turbineTitanium;
    public static Item turbineCopper;

    @Override
    public void preInit() {
        GameRegistry.registerWorldGenerator(new MTWorldGen(), 1);
        turbineCopper = new ItemRotor(900, MachineRenderedMeta.ROTOR_COPPER).setUnlocalizedName("turbine.copper");
        turbineAluminum = new ItemRotor(3600, MachineRenderedMeta.ROTOR_ALUMINUM).setUnlocalizedName("turbine.aluminum");
        turbineTitanium = new ItemRotor(28800, MachineRenderedMeta.ROTOR_TITANIUM).setUnlocalizedName("turbine.titanium");
        fludd = new ItemFLUDD(armorFLUDD, MTRenderIds.FLUDD, 1).setUnlocalizedName("fludd");
        RegistryHelper.registerTiles("Mariculture", TileSluice.class, TileFLUDDStand.class, TilePressureVessel.class, TileSponge.class, TileSluiceAdvanced.class, TileGenerator.class, TileRotor.class, TileRotorCopper.class, TileRotorAluminum.class, TileRotorTitanium.class);
        EntityRegistry.registerModEntity(EntityFLUDDSquirt.class, "WaterSquirt", EntityIds.FAKE_SQUIRT, Mariculture.instance, 80, 3, true);
    }

    @Override
    public void init() {
        addShaped(MTLib.generator, new Object[] { " B ", "CMC", "RIR", 'B', MTLib.copperBattery, 'C', comparator, 'M', "ingotMagnesium", 'R', "dustRedstone", 'I', baseIron });
        addShaped(MTLib.mechSponge, new Object[] { " D ", "ATA", "SCS", 'D', "fish", 'S', sponge, 'C', baseIron, 'A', water, 'T', "ingotAluminum" });
        addShaped(asStack(MTLib.sluice, 4), new Object[] { " H ", "WBW", "IMI", 'H', hopper, 'W', ironWheel, 'M', baseIron, 'B', ironBars, 'I', "ingotAluminum" });
        addShaped(MTLib.sluiceAdvanced, new Object[] { "TPT", "TST", "TBT", 'T', "ingotTitanium", 'P', MTLib.pressurisedBucket, 'S', asStack(Core.upgrade, UpgradeMeta.ADVANCED_STORAGE), 'B', MTLib.sluice });
        addShaped(MTLib.pressureVessel, new Object[] { "WLW", "PTP", "PSP", 'W', ironWheel, 'L', "blockLapis", 'P', titaniumSheet, 'T', tank, 'S', MTLib.sluice });
        addShaped(((ItemFLUDD) fludd).build(0), new Object[] { " E ", "PGP", "LUL", 'E', MTLib.plasticLens, 'P', MTLib.goldPlastic, 'G', transparent, 'L', tank, 'U', life });
        addShaped(asStack(turbineCopper), new Object[] { " I ", "ISI", " I ", 'I', "ingotCopper", 'S', "slabWood" });
        addShaped(asStack(turbineAluminum), new Object[] { " I ", "ISI", " I ", 'I', "ingotAluminum", 'S', stoneSlab });
        addShaped(asStack(turbineTitanium), new Object[] { " I ", "ISI", " I ", 'I', "ingotTitanium", 'S', quartzSlab });
    }

    @Override
    public void postInit() {
        PacketHandler.registerPacket(PacketSponge.class, Side.CLIENT);
        PacketHandler.registerPacket(PacketSponge.class, Side.SERVER);
        PacketHandler.registerPacket(PacketFLUDD.class, Side.CLIENT);
        PacketHandler.registerPacket(PacketFLUDD.class, Side.SERVER);
        PacketHandler.registerPacket(PacketRotorSpin.class, Side.CLIENT);
    }
}
