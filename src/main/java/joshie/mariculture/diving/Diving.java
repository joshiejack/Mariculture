package joshie.mariculture.diving;

import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.helpers.RecipeHelper.asStack;
import static joshie.mariculture.core.lib.ItemLib.glassLens;
import static joshie.mariculture.core.lib.ItemLib.leather;
import static joshie.mariculture.core.lib.ItemLib.reeds;
import static joshie.mariculture.core.lib.ItemLib.string;
import joshie.mariculture.core.lib.Modules.RegistrationModule;
import joshie.mariculture.core.lib.RenderIds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class Diving extends RegistrationModule {
    public static Item divingHelmet;
    public static Item divingTop;
    public static Item divingPants;
    public static Item divingBoots;
    public static Item snorkel;
    public static Item lifejacket;

    private static ArmorMaterial armorDIVING = EnumHelper.addArmorMaterial("DIVING", 20, new int[] { 1, 0, 2, 1 }, 0);
    private static ArmorMaterial armorSnorkel = EnumHelper.addArmorMaterial("SNORKEL", 10, new int[] { 0, 0, 0, 0 }, 0);

    @Override
    public void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new DivingEventHandler());
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new ArmorClientHandler());
        }
    }

    @Override
    public void registerBlocks() {
        return;
    }

    @Override
    public void registerItems() {
        divingHelmet = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 0).setUnlocalizedName("diving.helmet");
        divingTop = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 1).setUnlocalizedName("diving.top");
        divingPants = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 2).setUnlocalizedName("diving.pants");
        divingBoots = new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 3).setUnlocalizedName("diving.boots");
        snorkel = new ItemArmorSnorkel(armorSnorkel, RenderIds.SNORKEL, 0).setUnlocalizedName("snorkel");
        lifejacket = new ItemArmorSnorkel(armorSnorkel, RenderIds.SNORKEL, 1).setUnlocalizedName("lifejacket");
    }

    @Override
    public void registerOther() {
        return;
    }

    @Override
    public void registerRecipes() {
        addShaped(asStack(divingHelmet), new Object[] { "CCC", "CGC", 'C', "ingotCopper", 'G', "blockGlass" });
        addShaped(asStack(divingTop), new Object[] { " C ", "C C", " C ", 'C', leather });
        addShaped(asStack(divingPants), new Object[] { "CCC", " C ", "CCC", 'C', leather });
        addShaped(asStack(divingBoots), new Object[] { "C C", "L L", 'C', leather, 'L', "ingotIron" });
        addShaped(asStack(snorkel), new Object[] { "  R", "LLR", 'R', reeds, 'L', glassLens });
        addShaped(asStack(lifejacket), new Object[] { "WSW", "WWW", "WWW", 'S', string, 'W', "logWood" });
    }
}
