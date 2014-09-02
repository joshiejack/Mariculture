package joshie.mariculture.plugins.tconstruct;

import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.core.lib.MetalRates;
import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.plugins.PluginTConstruct;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.tools.ToolCore;
import cpw.mods.fml.common.Loader;

public class TitaniumTools {
    public static final int titanium_id = 200;
    public static TitaniumPart arrowhead;
    public static TitaniumPart axe_head;
    public static TitaniumPart battle_sign_head;
    public static TitaniumPart binding;
    public static TitaniumPart broad_axe_head;
    public static TitaniumPart chisel_head;
    public static TitaniumPart chunk;
    public static TitaniumPart crossbar;
    public static TitaniumPart excavator_head;
    public static TitaniumPart frypan_head;
    public static TitaniumPart full_guard;
    public static TitaniumPart hammer_head;
    public static TitaniumPart hand_guard;
    public static TitaniumPart knife_blade;
    public static TitaniumPart large_guard;
    public static TitaniumPart large_sword_blade;
    public static TitaniumPart large_plate;
    public static TitaniumPart pickaxe_head;
    public static TitaniumPart scythe_head;
    public static TitaniumPart shovel_head;
    public static TitaniumPart sword_blade;
    public static TitaniumPart tool_rod;
    public static TitaniumPart tough_binding;
    public static TitaniumPart tough_rod;

    public static void preInit() {
        arrowhead = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.arrow.head");
        axe_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.axe.head");
        battle_sign_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.battlesign.head");
        binding = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.binding");
        chisel_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.chisel.head");
        chunk = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.chunk");
        crossbar = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.crossbar");
        excavator_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.excavator.head");
        frypan_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.frypan.head");
        full_guard = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.full.guard");
        hammer_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.hammer.head");
        knife_blade = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.knife.blade");
        large_guard = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.large.guard");
        large_sword_blade = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.large.sword.blade");
        large_plate = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.large.plate");
        broad_axe_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.lumberaxe.head");
        hand_guard = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.medium.guard");
        pickaxe_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.pickaxe.head");
        scythe_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.scythe.head");
        shovel_head = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.shovel.head");
        sword_blade = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.sword.blade");
        tool_rod = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.tool.rod");
        tough_binding = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.tough.binding");
        tough_rod = (TitaniumPart) new TitaniumPart().setUnlocalizedName("titanium.tough.rod");
        RegistryHelper.registerItems(new Item[] { arrowhead, axe_head, battle_sign_head, binding, broad_axe_head, chisel_head, chunk, crossbar, excavator_head, frypan_head, full_guard, hammer_head, hand_guard, knife_blade, large_guard, large_sword_blade, large_plate, pickaxe_head, scythe_head, shovel_head, sword_blade, tool_rod, tough_binding, tough_rod });
    }

    public static void postInit() {
        addCastings(titanium_id, Fluids.getFluidStack("titanium", MetalRates.INGOT), 80);

        //Register Titanium with Patterns
        if (OreDictionary.getOres("ingotTitanium").size() > 0) {
            for (ItemStack ingot : OreDictionary.getOres("ingotTitanium")) {
                PatternBuilder.instance.registerFullMaterial(ingot, 2, "Titanium", new ItemStack(chunk, 1, titanium_id), new ItemStack(tool_rod, 1, titanium_id), titanium_id);
            }
        }

        addTools();

        int level = Loader.isModLoaded("IguanaTweaks") ? 5 : 4;
        TConstructRegistry.addToolMaterial(titanium_id, "Titanium", level, 650, 1500, 2, 1.5F, 2, 0.0F, "", "");
        TConstructClientRegistry.addMaterialRenderMapping(titanium_id, "Mariculture", "titanium", true);
        TConstructRegistry.addBowMaterial(titanium_id, 768, 40, 1.2F);
        TConstructRegistry.addArrowMaterial(titanium_id, 5.0F, 0.25F, 100.0F);
    }

    public static void addCastings(int id, FluidStack fluid, int delay) {
        PluginTConstruct.addPartCasting(new ItemStack(tool_rod, 1, id), TConstructRegistry.getItemStack("toolRodCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(pickaxe_head, 1, id), TConstructRegistry.getItemStack("pickaxeHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(shovel_head, 1, id), TConstructRegistry.getItemStack("shovelHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(axe_head, 1, id), TConstructRegistry.getItemStack("hatchetHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(sword_blade, 1, id), TConstructRegistry.getItemStack("swordBladeCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(large_guard, 1, id), TConstructRegistry.getItemStack("wideGuardCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(hand_guard, 1, id), TConstructRegistry.getItemStack("handGuardCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(crossbar, 1, id), TConstructRegistry.getItemStack("crossBarCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(binding, 1, id), TConstructRegistry.getItemStack("bindingCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(frypan_head, 1, id), TConstructRegistry.getItemStack("frypanHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(battle_sign_head, 1, id), TConstructRegistry.getItemStack("signHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(knife_blade, 1, id), TConstructRegistry.getItemStack("knifeBladeCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(chisel_head, 1, id), TConstructRegistry.getItemStack("chiselHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(tough_rod, 1, id), TConstructRegistry.getItemStack("toughRodCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(tough_binding, 1, id), TConstructRegistry.getItemStack("toughBindingCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(large_plate, 1, id), TConstructRegistry.getItemStack("largePlateCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(broad_axe_head, 1, id), TConstructRegistry.getItemStack("broadAxeHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(scythe_head, 1, id), TConstructRegistry.getItemStack("scytheHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(excavator_head, 1, id), TConstructRegistry.getItemStack("excavatorHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(large_sword_blade, 1, id), TConstructRegistry.getItemStack("largeBladeCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(hammer_head, 1, id), TConstructRegistry.getItemStack("hammerHeadCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(full_guard, 1, id), TConstructRegistry.getItemStack("fullGuardCast"), fluid, delay);
        PluginTConstruct.addPartCasting(new ItemStack(arrowhead, 1, id), new ItemStack(TConstructRegistry.getItem("metalPattern"), 1, 25), fluid, delay);
    }

    public static void addTools() {
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("arrow"), new Item[] { arrowhead, tool_rod, TConstructRegistry.getItem("fletching") });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("battleaxe"), new Item[] { broad_axe_head, tough_rod, broad_axe_head, tough_binding });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("battlesign"), new Item[] { battle_sign_head, tool_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("broadsword"), new Item[] { sword_blade, tool_rod, large_guard });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("chisel"), new Item[] { chisel_head, tool_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("cleaver"), new Item[] { large_sword_blade, tough_rod, large_plate, tough_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("cutlass"), new Item[] { sword_blade, tool_rod, full_guard });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("dagger"), new Item[] { knife_blade, tool_rod, crossbar });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("excavator"), new Item[] { excavator_head, tough_rod, large_plate, tough_binding });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("frypan"), new Item[] { frypan_head, tool_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("hammer"), new Item[] { hammer_head, tough_rod, large_plate, large_plate });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("hatchet"), new Item[] { axe_head, tool_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("longsword"), new Item[] { sword_blade, tool_rod, hand_guard });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("lumberaxe"), new Item[] { broad_axe_head, tough_rod, large_plate, tough_binding });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("mattock"), new Item[] { axe_head, tool_rod, shovel_head });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("pickaxe"), new Item[] { pickaxe_head, tool_rod, binding });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("rapier"), new Item[] { sword_blade, tool_rod, crossbar });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("scythe"), new Item[] { scythe_head, tough_rod, tough_binding, tough_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shortbow"), new Item[] { tool_rod, TConstructRegistry.getItem("bowstring"), tool_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shortbow"), new Item[] { TConstructRegistry.getItem("toolRod"), TConstructRegistry.getItem("bowstring"), tool_rod });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shortbow"), new Item[] { tool_rod, TConstructRegistry.getItem("bowstring"), TConstructRegistry.getItem("toolRod") });
        TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shovel"), new Item[] { shovel_head, tool_rod });
    }
}
