package mariculture.diving;

import static mariculture.core.helpers.RecipeHelper._;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.lib.ItemLib.aluminumSheet;
import static mariculture.core.lib.ItemLib.compressorBase;
import static mariculture.core.lib.ItemLib.compressorTop;
import static mariculture.core.lib.ItemLib.cooling;
import static mariculture.core.lib.ItemLib.glassLens;
import static mariculture.core.lib.ItemLib.ink;
import static mariculture.core.lib.ItemLib.ironWheel;
import static mariculture.core.lib.ItemLib.leather;
import static mariculture.core.lib.ItemLib.neoprene;
import static mariculture.core.lib.ItemLib.piston;
import static mariculture.core.lib.ItemLib.plastic;
import static mariculture.core.lib.ItemLib.plasticLens;
import static mariculture.core.lib.ItemLib.reeds;
import static mariculture.core.lib.ItemLib.string;
import static mariculture.core.lib.ItemLib.titaniumBattery;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.RenderIds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class Diving extends RegistrationModule {
    public static Item divingHelmet;
    public static Item divingTop;
    public static Item divingPants;
    public static Item divingBoots;
    public static Item scubaMask;
    public static Item scubaTank;
    public static Item scubaSuit;
    public static Item swimfin;
    public static Item snorkel;
    public static Item lifejacket;

    private static ArmorMaterial armorSCUBA = EnumHelper.addArmorMaterial("SCUBA", 15, new int[] { 0, 0, 1, 0 }, 0);
    private static ArmorMaterial armorDIVING = EnumHelper.addArmorMaterial("DIVING", 20, new int[] { 1, 0, 2, 1 }, 0);
    private static ArmorMaterial armorSnorkel = EnumHelper.addArmorMaterial("SNORKEL", 10, new int[] { 0, 0, 0, 0 }, 0);

    @Override
    public void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new ArmorClientHandler());
        }
    }

    @Override
    public void registerBlocks() {
        RegistryHelper.registerTiles(new Class[] { TileAirCompressor.class });
    }

    @Override
    public void registerItems() {
        divingHelmet = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 0).setUnlocalizedName("diving.helmet");
        divingTop = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 1).setUnlocalizedName("diving.top");
        divingPants = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 2).setUnlocalizedName("diving.pants");
        divingBoots = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 3).setUnlocalizedName("diving.boots");
        scubaMask = new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 0).setUnlocalizedName("scuba.mask");
        scubaTank = new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 1).setUnlocalizedName("scuba.tank").setNoRepair();
        scubaSuit = new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 2).setUnlocalizedName("scuba.suit");
        swimfin = new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 3).setUnlocalizedName("swimfin");
        snorkel = new ItemArmorSnorkel(armorSnorkel, RenderIds.SNORKEL, 0).setUnlocalizedName("snorkel");
        lifejacket = new ItemArmorSnorkel(armorSnorkel, RenderIds.SNORKEL, 1).setUnlocalizedName("lifejacket");
        RegistryHelper.registerItems(new Item[] { divingHelmet, divingTop, divingPants, divingBoots, scubaMask, scubaTank, scubaSuit, swimfin, snorkel, lifejacket });
    }

    @Override
    public void registerOther() {
        if(MaricultureTab.tabFactory != null) {
            MaricultureTab.tabFactory.setIcon(new ItemStack(Core.renderedMachinesMulti, 1, MachineRenderedMultiMeta.COMPRESSOR_TOP), true);
        }
    }

    @Override
    public void registerRecipes() {
        addShaped(_(compressorTop, 2), new Object[] { "  F", " PB", "III", 'I', aluminumSheet, 'F', cooling, 'B', titaniumBattery, 'P', piston });
        addShaped(compressorBase, new Object[] { "ITT", "III", "W  ", 'I', aluminumSheet, 'W', ironWheel, 'T', "ingotTitanium" });
        addShaped(_(divingHelmet), new Object[] { "CCC", "CGC", 'C', "ingotCopper", 'G', "blockGlass" });
        addShaped(_(divingTop), new Object[] { " C ", "C C", " C ", 'C', leather });
        addShaped(_(divingPants), new Object[] { "CCC", " C ", "CCC", 'C', leather });
        addShaped(_(divingBoots), new Object[] { "C C", "L L", 'C', leather, 'L', "ingotIron" });
        addShaped(_(scubaMask), new Object[] { "PD", "LL", 'P', "dyeBlack", 'L', plasticLens, 'D', "dyeYellow" });
        addShaped(_(_(scubaTank), scubaTank.getMaxDamage() - 1, 1), new Object[] { "DSD", "S S", "DSD", 'S', aluminumSheet, 'D', "dyeYellow" });
        addShaped(_(scubaSuit), new Object[] { "NNN", " N ", "NNN", 'N', neoprene });
        addShaped(_(swimfin), new Object[] { "N N", "PDP", "PDP", 'N', neoprene, 'P', plastic, 'D', ink });
        addShaped(_(snorkel), new Object[] { "  R", "LLR", 'R', reeds, 'L', glassLens });
        addShaped(_(lifejacket), new Object[] { "WSW", "WWW", "WWW", 'S', string, 'W', "logWood" });
    }
}
