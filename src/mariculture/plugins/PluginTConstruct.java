package mariculture.plugins;

import java.util.Arrays;
import java.util.List;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Text;
import mariculture.core.util.FluidDictionary;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.common.TContent;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;
import tconstruct.library.util.IToolPart;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PluginTConstruct extends Plugin {	
	public PluginTConstruct(String name) {
		super(name);
	}

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
	
	public void init() {
		addParts();
	}
	
	private static void addModifiers() {
		ToolBuilder tb = ToolBuilder.instance;
		ItemStack pearl = new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE);
        ItemStack pearlBlock = new ItemStack(Core.pearlBrick, 1, OreDictionary.WILDCARD_VALUE);
        int effect = 20;
        tb.registerToolMod(new ModPearl(new ItemStack[] { pearl }, effect, 1));
        tb.registerToolMod(new ModPearl(new ItemStack[] { pearl, pearl }, effect, 2));
        tb.registerToolMod(new ModPearl(new ItemStack[] { pearlBlock }, effect, 4));
        tb.registerToolMod(new ModPearl(new ItemStack[] { pearl, pearlBlock }, effect, 5));
        tb.registerToolMod(new ModPearl(new ItemStack[] { pearlBlock, pearlBlock }, effect, 8));
        
        for (ToolCore tool : TConstructRegistry.getToolMapping()) {
        	if(tool instanceof HarvestTool) {
        		TConstructClientRegistry.addEffectRenderMapping(tool, effect, "mariculture", "pearl", true);
        	}
        }
	}

	private static void addParts() {
		//Initialise Parts
		arrowhead = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_1).setUnlocalizedName("titanium.arrow.head");
		axe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_2).setUnlocalizedName("titanium.axe.head");
		battle_sign_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_3).setUnlocalizedName("titanium.battlesign.head");
		binding = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_4).setUnlocalizedName("titanium.binding");
		chisel_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_5).setUnlocalizedName("titanium.chisel.head");
		chunk = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_6).setUnlocalizedName("titanium.chunk");
		crossbar = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_7).setUnlocalizedName("titanium.crossbar");
		excavator_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_8).setUnlocalizedName("titanium.excavator.head");
		frypan_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_9).setUnlocalizedName("titanium.frypan.head");
		full_guard = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_10).setUnlocalizedName("titanium.full.guard");
		hammer_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_11).setUnlocalizedName("titanium.hammer.head");
		knife_blade = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_12).setUnlocalizedName("titanium.knife.blade");
		large_guard = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_13).setUnlocalizedName("titanium.large.guard");
		large_sword_blade = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_14).setUnlocalizedName("titanium.large.sword.blade");
		large_plate = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_15).setUnlocalizedName("titanium.large.plate");
		broad_axe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_16).setUnlocalizedName("titanium.lumberaxe.head");
		hand_guard = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_17).setUnlocalizedName("titanium.medium.guard");
		pickaxe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_18).setUnlocalizedName("titanium.pickaxe.head");
		scythe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_19).setUnlocalizedName("titanium.scythe.head");
		shovel_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_20).setUnlocalizedName("titanium.shovel.head");
		sword_blade = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_21).setUnlocalizedName("titanium.sword.blade");
		tool_rod = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_22).setUnlocalizedName("titanium.tool.rod");
		tough_binding = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_23).setUnlocalizedName("titanium.tough.binding");
		tough_rod = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_24).setUnlocalizedName("titanium.tough.rod");
		
		//Register Parts
		GameRegistry.registerItem(arrowhead, "titanium_arrow_head");
		GameRegistry.registerItem(axe_head, "titanium_axe_head");
		GameRegistry.registerItem(battle_sign_head, "titanium_battle_sign");
		GameRegistry.registerItem(binding, "titanium_binding");
		GameRegistry.registerItem(broad_axe_head, "titanium_broad_axe_head");
		GameRegistry.registerItem(chisel_head, "titanium_chisel_head");
		GameRegistry.registerItem(chunk, "titanium_chunk");
		GameRegistry.registerItem(crossbar, "titanium_crossbar");
		GameRegistry.registerItem(excavator_head, "titanium_excavator_head");
		GameRegistry.registerItem(frypan_head, "titanium_frypan_head");
		GameRegistry.registerItem(full_guard, "titanium_full_guard");
		GameRegistry.registerItem(hammer_head, "titanium_hammer_head");
		GameRegistry.registerItem(hand_guard, "titanium_hand_guard");
		GameRegistry.registerItem(knife_blade, "titanium_knife_blade");
		GameRegistry.registerItem(large_guard, "titanium_large_guard");
		GameRegistry.registerItem(large_sword_blade, "titanium_large_swordBlade");
		GameRegistry.registerItem(large_plate, "titanium_large_plate");
		GameRegistry.registerItem(pickaxe_head, "titanium_pickaxe_head");
		GameRegistry.registerItem(scythe_head, "titanium_scythe_head");
		GameRegistry.registerItem(shovel_head, "titanium_shovel_head");
		GameRegistry.registerItem(sword_blade, "titanium_sword_blade");
		GameRegistry.registerItem(tool_rod, "titanium_tool_rod");
		GameRegistry.registerItem(tough_binding, "titanium_tough_binding");
		GameRegistry.registerItem(tough_rod, "titanium_tough_rod");
	}
	
	private static void addRecipes() {
		addMelting();
		addTools();
		addAlloy();
	}

	public static void addMelting() {
		//Melt Down Titanium
		PluginTConstruct.addMelting("ingotRutile", new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), MetalRates.INGOT), 800);
		PluginTConstruct.addMelting("ingotTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 800);
		PluginTConstruct.addMelting("oreRutile", new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), MetalRates.ORE), 800);
		PluginTConstruct.addMelting("dustTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 800);
		PluginTConstruct.addMelting("blockTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.BLOCK), 800);
		PluginTConstruct.addMelting("nuggetTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.NUGGET), 800);
		// >> Form Ingot and Block
		PluginTConstruct.addCasting("ingotRutile", new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), MetalRates.INGOT), 100);
		PluginTConstruct.addCasting("ingotTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 100);
		PluginTConstruct.addBlockCasting("blockTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.BLOCK), 100);
		//Melt Down Magnesium
		PluginTConstruct.addMelting("ingotMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.INGOT), 300);
		PluginTConstruct.addMelting("oreMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.ORE), 300);
		PluginTConstruct.addMelting("dustMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.INGOT), 300);
		PluginTConstruct.addMelting("blockMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.BLOCK), 300);
		PluginTConstruct.addMelting("nuggetMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.NUGGET), 300);
		// >> Form Ingot and Block
		PluginTConstruct.addCasting("ingotMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.INGOT), 100);
		PluginTConstruct.addBlockCasting("blockMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.BLOCK), 100);
		
		addCastings(titanium_id, new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 80);
		
		//Register Titanium with Patterns
		if(OreDictionary.getOres("ingotTitanium").size() > 0) {
			for(ItemStack ingot: OreDictionary.getOres("ingotTitanium")) {
				PatternBuilder.instance.registerFullMaterial(ingot, 2, "Titanium", 
						new ItemStack(chunk, 1, titanium_id), new ItemStack(tool_rod, 1, titanium_id), titanium_id);
			}
		}
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
		PluginTConstruct.addPartCasting(new ItemStack(arrowhead, 1, id), new ItemStack(TContent.metalPattern, 1, 25), fluid, delay);
	}
	
	public static void addTools() {
		TConstructRegistry.addToolRecipe(TContent.arrow, new Item[] { arrowhead, tool_rod, TContent.fletching });
		TConstructRegistry.addToolRecipe(TContent.battleaxe, new Item[] { broad_axe_head, tough_rod, broad_axe_head, tough_binding });
		TConstructRegistry.addToolRecipe(TContent.battlesign, new Item[] { battle_sign_head, tool_rod });
		TConstructRegistry.addToolRecipe(TContent.broadsword, new Item[] { sword_blade, tool_rod, large_guard });
		TConstructRegistry.addToolRecipe(TContent.chisel, new Item[] { chisel_head, tool_rod });
		TConstructRegistry.addToolRecipe(TContent.cleaver, new Item[] { large_sword_blade, tough_rod, large_plate, tough_rod });
		TConstructRegistry.addToolRecipe(TContent.cutlass, new Item[] {sword_blade, tool_rod, full_guard });
		TConstructRegistry.addToolRecipe(TContent.dagger, new Item[] {knife_blade, tool_rod, crossbar });
		TConstructRegistry.addToolRecipe(TContent.excavator, new Item[] {excavator_head, tough_rod, large_plate, tough_binding });
		TConstructRegistry.addToolRecipe(TContent.frypan, new Item[] {frypan_head, tool_rod });
		TConstructRegistry.addToolRecipe(TContent.hammer, new Item[] { hammer_head, tough_rod, large_plate, large_plate });
		TConstructRegistry.addToolRecipe(TContent.hatchet, new Item[] { axe_head, tool_rod });
		TConstructRegistry.addToolRecipe(TContent.longsword, new Item[] { sword_blade, tool_rod, hand_guard });
		TConstructRegistry.addToolRecipe(TContent.lumberaxe, new Item[] { broad_axe_head, tough_rod, large_plate, tough_binding });
		TConstructRegistry.addToolRecipe(TContent.mattock, new Item[] { axe_head, tool_rod, shovel_head });
		TConstructRegistry.addToolRecipe(TContent.pickaxe, new Item[] { pickaxe_head, tool_rod, binding });
		TConstructRegistry.addToolRecipe(TContent.rapier, new Item[] { sword_blade, tool_rod, crossbar });
		TConstructRegistry.addToolRecipe(TContent.scythe, new Item[] { scythe_head, tough_rod, tough_binding, tough_rod });
		TConstructRegistry.addToolRecipe(TContent.shortbow, new Item[] { tool_rod, TContent.bowstring, tool_rod });
		TConstructRegistry.addToolRecipe(TContent.shortbow, new Item[] { TContent.toolRod, TContent.bowstring, tool_rod });
		TConstructRegistry.addToolRecipe(TContent.shortbow, new Item[] { tool_rod, TContent.bowstring, TContent.toolRod });
		TConstructRegistry.addToolRecipe(TContent.shovel, new Item[] { shovel_head, tool_rod });
	}
	
	private static void addAlloy() {
		FluidStack titanium = new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), 8);
		FluidStack rutile = new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), 8);
		FluidStack magnesium = new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), 8);

		Smeltery.addAlloyMixing(titanium, new FluidStack[] { rutile, magnesium });
	}
	
	//Helpers
	public static class TitaniumPart extends Item implements IToolPart {
		public TitaniumPart(int id) {
			super(id);
			setCreativeTab(TConstructRegistry.materialTab);
			setMaxStackSize(64);
			setMaxDamage(0);
			setHasSubtypes(true);
		}

		@SideOnly(Side.CLIENT)
		public void registerIcons(IconRegister icon) {
			this.itemIcon = icon.registerIcon(Mariculture.modid + ":parts/" + ((this.getUnlocalizedName()).replace(".", "_")).substring(5));
		}

		public int getMaterialID(ItemStack itemStack) {
			return PluginTConstruct.titanium_id;
		}
	}

	public static void addMelting(String dic, FluidStack fluid, int temp) {
		if(OreDictionary.getOres(dic).size() > 0) {
			for(ItemStack ore: OreDictionary.getOres(dic)) {
				Smeltery.addMelting(ore, temp, fluid);
			}
		}
	}

	public static void addCasting(String dic, FluidStack fluid, int delay) {
		if(OreDictionary.getOres(dic).size() > 0) {
			TConstructRegistry.getTableCasting().addCastingRecipe(OreDictionary.getOres(dic).get(0), fluid, TConstructRegistry.getItemStack("ingotCast"), delay);
		}
	}

	public static void addBlockCasting(String dic, FluidStack fluid, int delay) {
		if(OreDictionary.getOres(dic).size() > 0) {
			TConstructRegistry.getBasinCasting().addCastingRecipe(OreDictionary.getOres(dic).get(0), fluid, delay);
		}
	}

	public static void addPartCasting(ItemStack output, ItemStack cast, FluidStack fluid, int hardeningDelay) {
		TConstructRegistry.getTableCasting().addCastingRecipe(output, fluid, cast, hardeningDelay);
	}
	
	public static class ModPearl extends ToolMod
	{
	    String tooltipName;
	    int increase;
	    int max;

	    public ModPearl(ItemStack[] items, int effect, int inc)
	    {
	        super(items, effect, "Pearls");
	        tooltipName = Text.AQUA + "Aquatic";
	        increase = inc;
	        max = 50;
	    }

	    @Override
	    protected boolean canModify (ItemStack tool, ItemStack[] input)
	    {
	        ToolCore toolItem = (ToolCore) tool.getItem();
	        if (!validType(toolItem))
	            return false;

	        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
	        if (!tags.hasKey(key))
	            return tags.getInteger("Modifiers") > 0;

	        int keyPair[] = tags.getIntArray(key);
	        if (keyPair[0] + increase <= keyPair[1])
	            return true;

	        else if (keyPair[0] == keyPair[1])
	            return tags.getInteger("Modifiers") > 0;

	        else
	            return false;
	    }

	    @Override
	    public void modify (ItemStack[] input, ItemStack tool)
	    {
	        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
	        int[] keyPair;
	        if (tags.hasKey(key))
	        {
	            keyPair = tags.getIntArray(key);
	            if (keyPair[0] % max == 0)
	            {
	                keyPair[0] += increase;
	                keyPair[1] += max;
	                tags.setIntArray(key, keyPair);

	                int modifiers = tags.getInteger("Modifiers");
	                modifiers -= 1;
	                tags.setInteger("Modifiers", modifiers);
	            }
	            else
	            {
	                keyPair[0] += increase;
	                tags.setIntArray(key, keyPair);
	            }
	            updateModTag(tool, keyPair);
	        }
	        else
	        {
	            int modifiers = tags.getInteger("Modifiers");
	            modifiers -= 1;
	            tags.setInteger("Modifiers", modifiers);
	            String modName = Text.AQUA + "Pearls (" + increase + "/" + max + ")";
	            int tooltipIndex = addToolTip(tool, tooltipName, modName);
	            keyPair = new int[] { increase, max, tooltipIndex };
	            tags.setIntArray(key, keyPair);
	        }
	    }

	    void updateModTag (ItemStack tool, int[] keys)
	    {
	        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
	        String tip = "ModifierTip" + keys[2];
	        String modName = Text.AQUA + "Pearls (" + keys[0] + "/" + keys[1] + ")";
	        tags.setString(tip, modName);
	    }

	    public boolean validType (ToolCore tool)
	    {
	        List list = Arrays.asList(tool.toolCategories());
	        return list.contains("harvest");
	    }
	}

	@ForgeSubscribe
	public void onBreaking(BreakSpeed event) {
		Block block = event.block;
		EntityPlayer player = event.entityPlayer;
		if(player.getCurrentEquippedItem() != null) {
			if (player.isInsideOfMaterial(Material.water)) {
				if(player.getCurrentEquippedItem().getItem() instanceof ToolCore) {
					ToolCore toolCore = (ToolCore) player.getCurrentEquippedItem().getItem();
					if(toolCore.canHarvestBlock(event.block, player.getCurrentEquippedItem())) {						
						ItemStack tool = player.getCurrentEquippedItem();
						if(tool.hasTagCompound()) {
							NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
							 if (tags.hasKey("Pearls")) {
								 float speed = tags.getIntArray("Pearls")[0]/50;
								 event.newSpeed = event.originalSpeed + speed;
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void preInit() {
		FluidDictionary.instance.addFluid("aluminum.molten", FluidType.Aluminum.fluid);
		FluidDictionary.instance.addFluid("bronze.molten", FluidType.Bronze.fluid);
		FluidDictionary.instance.addFluid("copper.molten", FluidType.Copper.fluid);
		FluidDictionary.instance.addFluid("glass.molten", FluidType.Glass.fluid);
		FluidDictionary.instance.addFluid("gold.molten", FluidType.Gold.fluid);
		FluidDictionary.instance.addFluid("iron.molten", FluidType.Iron.fluid);
		FluidDictionary.instance.addFluid("lead.molten", FluidType.Lead.fluid);
		FluidDictionary.instance.addFluid("nickel.molten", FluidType.Nickel.fluid);
		FluidDictionary.instance.addFluid("silver.molten", FluidType.Silver.fluid);
		FluidDictionary.instance.addFluid("steel.molten", FluidType.Steel.fluid);
		FluidDictionary.instance.addFluid("tin.molten", FluidType.Tin.fluid);
		FluidDictionary.instance.addFluid("electrum.molten", FluidType.Electrum.fluid);
		FluidDictionary.instance.addFluid("cobalt.molten", FluidType.Cobalt.fluid);
	}

	@Override
	public void postInit() {
		int level = Loader.isModLoaded("IguanaTweaks")? 5: 4;
		TConstructRegistry.addToolMaterial(titanium_id, "Titanium", level, 600, 1500, 2, 1.5F, 2, 0.0F, "", "");
		TConstructClientRegistry.addMaterialRenderMapping(titanium_id, "Mariculture", "titanium", true);
		TConstructRegistry.addBowMaterial(titanium_id, 768, 40, 1.2F);
		TConstructRegistry.addArrowMaterial(titanium_id, 5.0F, 0.25F, 100.0F);
		
		addRecipes();
		addModifiers();
	}
}