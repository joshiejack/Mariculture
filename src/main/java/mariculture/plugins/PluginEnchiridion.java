package mariculture.plugins;

import joshie.enchiridion.api.EnchiridionAPI;
import mariculture.Mariculture;
import mariculture.aesthetics.Aesthetics;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DropletMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.PlansMeta;
import mariculture.core.lib.RockMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.diving.Diving;
import mariculture.factory.Factory;
import mariculture.factory.items.ItemPlan;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemDroplet;
import mariculture.lib.util.IHasMetaItem;
import mariculture.magic.Magic;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.enchiridion.EventHandler;
import mariculture.plugins.enchiridion.ItemGuide;
import mariculture.plugins.enchiridion.PageVat;
import mariculture.plugins.enchiridion.RecipeHandlerVat;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
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
            RecipeHelper.addBookRecipe(new ItemStack(guides, 1, GuideMeta.FISH_DATA), new ItemStack(Fishery.fishEggs, 1, OreDictionary.WILDCARD_VALUE));
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
            //GuideHandler.registerBook(new ItemStack(guides, 1, GuideMeta.FISH_DATA), Mariculture.modid, "fish", 0xFF8000);
            GuideHandler.registerBook(new ItemStack(guides, 1, GuideMeta.ENCHANTS), Mariculture.modid, "enchants", 0xA64DFF);
        }
    }
    
    public static void register(Item item) {
    	register(item, 1, "", "");
    }
    
    public static void register(Block block) {
    	register(block, 1, "", "");
    }
    
    public static void register(Item item, int count) {
    	register(item, count, "", "");
    }
    
    public static void register(Block block, int count) {
    	register(block, count, "", "");
    }
    
    public static void register (Block block, int count, String prefix, String suffix) {
    	register(Item.getItemFromBlock(block), count, prefix, suffix);
    }
    
    public static void register(Item item, int count, String prefix, String suffix) {
    	if (!(item instanceof IHasMetaItem)) {
    		ItemStack stack = new ItemStack(item);
    		String name = stack.getUnlocalizedName().replace("tile.", "").replace("item.", "").replace("mariculture.", "").replace(".name", "").replace("maritech.", "");
    		DisplayRegistry.registerShorthand(name, stack);
    	} else {
	    	for (int i = 0; i < count; i++) {
	    		ItemStack stack = new ItemStack(item, 1, i);
	    		String name = prefix + ((IHasMetaItem)item).getName(stack) + suffix;
	    		if (stack.getItem() instanceof ItemDroplet) {
	    			name = "droplet" + name.substring(0, 1).toUpperCase() + name.substring(1);
	    		}
	    		
	    		DisplayRegistry.registerShorthand(name, stack);
	    	}
	    }
    }

    @Override
    public void init() {
        /** Register this mod as containing books **/
        FMLInterModComms.sendMessage("Enchiridion2", "registerBookMod", "Mariculture");
        
        /** Build a NBT Tag **/
        //EnchiridionAPI.instance.registerBookData(new ItemStack(guides, 1, GuideMeta.FISH_DATA), "mariculture_fish_breeding");
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("book", "mariculture_fish_breeding");
        new ItemStack(guides, 1, GuideMeta.FISH_DATA).writeToNBT(tag);
        FMLInterModComms.sendMessage("Enchiridion2", "registerBookItem", tag);
        
    	if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            EnchiridionAPI.instance.registerRecipeHandler(new RecipeHandlerVat());
    		if (Modules.isActive(Modules.factory)) {
    			register(Factory.filter);
    			register(Factory.chalk);
    			register(Factory.paintbrush);
    			for (int i = 0; i < PlansMeta.COUNT; i++) {
    	    		ItemStack stack = new ItemStack(Factory.plans);
    	    		stack.setTagCompound(new NBTTagCompound());
    	    		stack.getTagCompound().setInteger("type", i);
    	    		String name = ((ItemPlan)stack.getItem()).getName(stack);
    	    		DisplayRegistry.registerShorthand(name, stack);
    	    	}
    		}
    		
    		register(Core.crafting, CraftingMeta.COUNT);
    		register(Core.hammer);
    		register(Core.ladle);
    		register(Core.materials, MaterialsMeta.COUNT);
    		register(Core.bottles, BottleMeta.COUNT);
    		register(Core.pearls, PearlColor.COUNT);
    		register(Core.upgrade, UpgradeMeta.COUNT);
    		register(Core.glass, GlassMeta.COUNT);
    		register(Core.limestone, LimestoneMeta.COUNT, "limestone", "");
    		register(Core.machines, MachineMeta.COUNT);
    		register(Core.machinesMulti, MachineMultiMeta.COUNT);
    		register(Core.metals, MetalMeta.COUNT);
    		register(Core.pearlBlock, PearlColor.COUNT);
    		register(Aesthetics.pearlBrick, PearlColor.COUNT);
    		register(Core.renderedMachines, MachineRenderedMeta.COUNT);
    		register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COUNT);
    		register(Core.rocks, RockMeta.COUNT);
    		register(Core.sands, GroundMeta.COUNT);
    		register(Core.tanks, TankMeta.COUNT, "", "Tank");
    		register(Core.woods, WoodMeta.COUNT);
    		register(Core.buckets, BucketMeta.COUNT);
    		register(Core.transparent, TransparentMeta.COUNT, "transparent_", "");
    		
    		if (Modules.isActive(Modules.diving)) {
    			register(Diving.divingBoots);
    			register(Diving.divingHelmet);
    			register(Diving.divingPants);
    			register(Diving.divingTop);
    			register(Diving.snorkel);
    			register(Diving.lifejacket);
    		}
    		
    		if (Modules.isActive(Modules.fishery)) {
    			register(Fishery.bait, BaitMeta.COUNT);
    			register(Fishery.droplet, DropletMeta.COUNT);
    			register(Fishery.fishEggs);
    			register(Fishery.fishy, FishSpecies.species.size());
    			register(Fishery.net);
    			register(Fishery.rodReed);
    			register(Fishery.rodTitanium);
    			register(Fishery.rodWood);
    		}
    		
    		if (Modules.isActive(Modules.magic)) {
    			register(Magic.basicMirror);
    			register(Magic.magicMirror);
    			register(Magic.celestialMirror);
    		}
    	}
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
