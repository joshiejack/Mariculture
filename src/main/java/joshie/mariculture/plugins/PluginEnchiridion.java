package joshie.mariculture.plugins;

import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.RecipeHelper;
import joshie.mariculture.core.lib.CraftingMeta;
import joshie.mariculture.core.lib.GuideMeta;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.diving.Diving;
import joshie.mariculture.fishery.Fishery;
import joshie.mariculture.plugins.Plugins.Plugin;
import joshie.mariculture.plugins.enchiridion.EventHandler;
import joshie.mariculture.plugins.enchiridion.ItemGuide;
import joshie.mariculture.plugins.enchiridion.PageVat;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import enchiridion.api.DisplayRegistry;
import enchiridion.api.GuideHandler;

public class PluginEnchiridion extends Plugin {
    public PluginEnchiridion(String name) {
        super(name);
    }

    public static Item guides;
    private static EventHandler handler;

    @Override
    public void preInit() {
        handler = new EventHandler();
        guides = new ItemGuide().setUnlocalizedName("guide");
        
        RecipeHelper.addBookRecipe(new ItemStack(guides, 1, GuideMeta.PROCESSING), new ItemStack(Core.crafting, 1, CraftingMeta.BURNT_BRICK));
        if (Modules.isActive(Modules.diving)) {
            RecipeHelper.addBookRecipe(new ItemStack(guides, 1, GuideMeta.DIVING), new ItemStack(Diving.snorkel));
        }
        if (Modules.isActive(Modules.factory)) {
            RecipeHelper.addBookRecipe(new ItemStack(guides, 1, GuideMeta.MACHINES), new ItemStack(Core.crafting, 1, CraftingMeta.WHEEL));
        }
        if (Modules.isActive(Modules.fishery)) {
            RecipeHelper.addBookRecipe(new ItemStack(guides, 1, GuideMeta.FISHING), new ItemStack(Fishery.rodReed));
        }
        if (Modules.isActive(Modules.magic)) {
            RecipeHelper.addBookRecipe(new ItemStack(guides, 1, GuideMeta.ENCHANTS), new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE));
        }

        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            GuideHandler.registerPageHandler("vat", new PageVat());
            GuideHandler.registerBook(new ItemStack(guides, 1, GuideMeta.PROCESSING), Mariculture.modid, "processing", 0x1C1B1B);
            GuideHandler.registerBook(new ItemStack(guides, 1, GuideMeta.DIVING), Mariculture.modid, "diving", 0x75BAFF);
            GuideHandler.registerBook(new ItemStack(guides, 1, GuideMeta.MACHINES), Mariculture.modid, "machines", 0x333333);
            GuideHandler.registerBook(new ItemStack(guides, 1, GuideMeta.FISHING), Mariculture.modid, "fishing", 0x008C8C);
            GuideHandler.registerBook(new ItemStack(guides, 1, GuideMeta.ENCHANTS), Mariculture.modid, "enchants", 0xA64DFF);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            Integer[] colors = new Integer[] { 0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11 };
            DisplayRegistry.registerMetaCycling(Core.pearls, "pearl", colors);
            DisplayRegistry.registerMetaCycling(Items.fish, "fish", new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 });
        }
    }
}
