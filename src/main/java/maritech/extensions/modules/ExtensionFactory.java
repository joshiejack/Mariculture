package maritech.extensions.modules;

import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.asStack;
import static mariculture.core.lib.MCLib.baseIron;
import static mariculture.core.lib.MCLib.comparator;
import static mariculture.core.lib.MCLib.hopper;
import static mariculture.core.lib.MCLib.ironBars;
import static mariculture.core.lib.MCLib.ironWheel;
import static mariculture.core.lib.MCLib.life;
import static mariculture.core.lib.MCLib.quartzSlab;
import static mariculture.core.lib.MCLib.sponge;
import static mariculture.core.lib.MCLib.stoneSlab;
import static mariculture.core.lib.MCLib.tank;
import static mariculture.core.lib.MCLib.titaniumSheet;
import static mariculture.core.lib.MCLib.transparent;
import static mariculture.core.lib.MCLib.water;
import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.network.PacketHandler;
import mariculture.lib.helpers.RegistryHelper;
import maritech.entity.EntityFLUDDSquirt;
import maritech.handlers.MTWorldGen;
import maritech.items.ItemFLUDD;
import maritech.items.ItemRotor;
import maritech.lib.MTLib;
import maritech.lib.MTRenderIds;
import maritech.network.PacketFLUDD;
import maritech.network.PacketRotorSpin;
import maritech.network.PacketSponge;
import maritech.tile.TileFLUDDStand;
import maritech.tile.TileGenerator;
import maritech.tile.TilePressureVessel;
import maritech.tile.TileRotor;
import maritech.tile.TileRotorAluminum;
import maritech.tile.TileRotorCopper;
import maritech.tile.TileRotorTitanium;
import maritech.tile.TileSluice;
import maritech.tile.TileSluiceAdvanced;
import maritech.tile.TileSponge;
import maritech.util.IModuleExtension;
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
        RegistryHelper.registerTiles("Mariculture", TileSluice.class, TileFLUDDStand.class, TilePressureVessel.class, TileSponge.class, TileSluiceAdvanced.class, TileGenerator.class, TileRotor.class, 
                TileRotorCopper.class, TileRotorAluminum.class, TileRotorTitanium.class);
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
