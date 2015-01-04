package maritech.extensions.modules;

import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.asStack;
import static mariculture.core.lib.MCLib.aluminumSheet;
import static mariculture.core.lib.MCLib.cooling;
import static mariculture.core.lib.MCLib.ink;
import static mariculture.core.lib.MCLib.ironWheel;
import static mariculture.core.lib.MCLib.piston;
import static maritech.lib.MTLib.compressorBase;
import static maritech.lib.MTLib.compressorTop;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.network.PacketHandler;
import mariculture.lib.helpers.RegistryHelper;
import maritech.handlers.ScubaEvent;
import maritech.items.ItemArmorScuba;
import maritech.lib.MTLib;
import maritech.lib.MTRenderIds;
import maritech.network.PacketCompressor;
import maritech.tile.TileAirCompressor;
import maritech.util.IModuleExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;

public class ExtensionDiving implements IModuleExtension {
    public static Item scubaMask;
    public static Item scubaTank;
    public static Item scubaSuit;
    public static Item swimfin;
    public static ArmorMaterial armorSCUBA = EnumHelper.addArmorMaterial("SCUBA", 15, new int[] { 0, 0, 1, 0 }, 0);

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new ScubaEvent());
        scubaMask = new ItemArmorScuba(armorSCUBA, MTRenderIds.SCUBA, 0).setUnlocalizedName("scuba.mask");
        scubaTank = new ItemArmorScuba(armorSCUBA, MTRenderIds.SCUBA, 1).setUnlocalizedName("scuba.tank").setNoRepair();
        scubaSuit = new ItemArmorScuba(armorSCUBA, MTRenderIds.SCUBA, 2).setUnlocalizedName("scuba.suit");
        swimfin = new ItemArmorScuba(armorSCUBA, MTRenderIds.SCUBA, 3).setUnlocalizedName("swimfin");
        RegistryHelper.registerTiles("Mariculture", new Class[] { TileAirCompressor.class });

        if (MaricultureTab.tabFactory != null) {
            MaricultureTab.tabFactory.setIcon(new ItemStack(Core.renderedMachinesMulti, 1, MachineRenderedMultiMeta.COMPRESSOR_TOP), true);
        }
    }

    @Override
    public void init() {
        addShaped(asStack(compressorTop, 2), new Object[] { "  F", " PB", "III", 'I', aluminumSheet, 'F', cooling, 'B', MTLib.titaniumBattery, 'P', piston });
        addShaped(compressorBase, new Object[] { "ITT", "III", "W  ", 'I', aluminumSheet, 'W', ironWheel, 'T', "ingotTitanium" });
        addShaped(asStack(scubaMask), new Object[] { "PD", "LL", 'P', "dyeBlack", 'L', MTLib.plasticLens, 'D', "dyeYellow" });
        addShaped(asStack(asStack(scubaTank), scubaTank.getMaxDamage() - 1, 1), new Object[] { "DSD", "S S", "DSD", 'S', aluminumSheet, 'D', "dyeYellow" });
        addShaped(asStack(scubaSuit), new Object[] { "NNN", " N ", "NNN", 'N', MTLib.neoprene });
        addShaped(asStack(swimfin), new Object[] { "N N", "PDP", "PDP", 'N', MTLib.neoprene, 'P', MTLib.plastic, 'D', ink });
    }

    @Override
    public void postInit() {
        PacketHandler.registerPacket(PacketCompressor.class, Side.CLIENT);
    }
}
