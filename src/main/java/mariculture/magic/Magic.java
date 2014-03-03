package mariculture.magic;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EnchantIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.UtilMeta;
import mariculture.magic.enchantments.EnchantmentBlink;
import mariculture.magic.enchantments.EnchantmentElemental;
import mariculture.magic.enchantments.EnchantmentFallDamage;
import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentHealth;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentLuck;
import mariculture.magic.enchantments.EnchantmentNeverHungry;
import mariculture.magic.enchantments.EnchantmentOneRing;
import mariculture.magic.enchantments.EnchantmentRestore;
import mariculture.magic.enchantments.EnchantmentResurrection;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import mariculture.magic.enchantments.EnchantmentStepUp;
import mariculture.magic.jewelry.ItemBracelet;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemNecklace;
import mariculture.magic.jewelry.ItemRing;
import mariculture.magic.jewelry.parts.JewelryPart;
import mariculture.magic.jewelry.parts.PartDiamond;
import mariculture.magic.jewelry.parts.PartGold;
import mariculture.magic.jewelry.parts.PartGoldString;
import mariculture.magic.jewelry.parts.PartGoldThread;
import mariculture.magic.jewelry.parts.PartIron;
import mariculture.magic.jewelry.parts.PartOneRing;
import mariculture.magic.jewelry.parts.PartPearlBlack;
import mariculture.magic.jewelry.parts.PartPearlBlue;
import mariculture.magic.jewelry.parts.PartPearlBrown;
import mariculture.magic.jewelry.parts.PartPearlGold;
import mariculture.magic.jewelry.parts.PartPearlGreen;
import mariculture.magic.jewelry.parts.PartPearlOrange;
import mariculture.magic.jewelry.parts.PartPearlPink;
import mariculture.magic.jewelry.parts.PartPearlPurple;
import mariculture.magic.jewelry.parts.PartPearlRed;
import mariculture.magic.jewelry.parts.PartPearlSilver;
import mariculture.magic.jewelry.parts.PartPearlWhite;
import mariculture.magic.jewelry.parts.PartPearlYellow;
import mariculture.magic.jewelry.parts.PartString;
import mariculture.magic.jewelry.parts.PartWool;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class Magic extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
	public static JewelryPart pearlBlack;
	public static JewelryPart pearlBlue;
	public static JewelryPart pearlBrown;
	public static JewelryPart pearlGold;
	public static JewelryPart pearlGreen;
	public static JewelryPart pearlOrange;
	public static JewelryPart pearlPink;
	public static JewelryPart pearlPurple;
	public static JewelryPart pearlRed;
	public static JewelryPart pearlSilver;
	public static JewelryPart pearlWhite;
	public static JewelryPart pearlYellow;
	
	public static Enchantment elemental;
	public static Enchantment spider;
	public static Enchantment blink;
	public static Enchantment fall;
	public static Enchantment flight;
	public static Enchantment glide;
	public static Enchantment health;
	public static Enchantment jump;
	public static Enchantment hungry;
	public static Enchantment oneRing;
	public static Enchantment repair;
	public static Enchantment resurrection;
	public static Enchantment speed;
	public static Enchantment stepUp;
	public static Enchantment luck;
	
	public static Item basicMirror;
	public static Item magicMirror;
	public static Item celestialMirror;
	public static Item ring;
	public static Item bracelet;
	public static Item necklace;
	public static Item magnet;
	
	public void registerEnchants() {
		if(EnchantIds.spider > 0) { spider = new EnchantmentSpider(EnchantIds.spider, 4, EnumEnchantmentType.all); }
		if(EnchantIds.blink > 0) { blink = new EnchantmentBlink(EnchantIds.blink, 1, EnumEnchantmentType.all); }
		if(EnchantIds.fall > 0) { fall = new EnchantmentFallDamage(EnchantIds.fall, 10, EnumEnchantmentType.all); }
		if(EnchantIds.flight > 0) { flight = new EnchantmentFlight(EnchantIds.flight, 1, EnumEnchantmentType.all); }
		if(EnchantIds.glide > 0) { glide = new EnchantmentGlide(EnchantIds.glide, 8, EnumEnchantmentType.all); }
		if(EnchantIds.health > 0) { health = new EnchantmentHealth(EnchantIds.health, 8, EnumEnchantmentType.all); }
		if(EnchantIds.jump > 0) { jump = new EnchantmentJump(EnchantIds.jump, 12, EnumEnchantmentType.all); }
		if(EnchantIds.hungry > 0) { hungry = new EnchantmentNeverHungry(EnchantIds.hungry, 8, EnumEnchantmentType.all); }
		if(EnchantIds.oneRing > 0) { oneRing = new EnchantmentOneRing(EnchantIds.oneRing, 0, EnumEnchantmentType.all); }
		if(EnchantIds.repair > 0) { repair = new EnchantmentRestore(EnchantIds.repair, 6, EnumEnchantmentType.all); }
		if(EnchantIds.resurrection > 0) { resurrection = new EnchantmentResurrection(EnchantIds.resurrection, 1, EnumEnchantmentType.all); }
		if(EnchantIds.speed > 0) { speed = new EnchantmentSpeed(EnchantIds.speed, 10, EnumEnchantmentType.all); }
		if(EnchantIds.stepUp > 0) { stepUp = new EnchantmentStepUp(EnchantIds.stepUp, 9, EnumEnchantmentType.all); }
		if(EnchantIds.luck > 0) { luck = new EnchantmentLuck(EnchantIds.luck, 5, EnumEnchantmentType.all); }
		if(EnchantIds.elemental > 0) { elemental = new EnchantmentElemental(EnchantIds.elemental, 5, EnumEnchantmentType.all); }
	}

	@Override
	public void registerHandlers() {
		MaricultureHandlers.mirror = new MirrorHandler();
		MinecraftForge.EVENT_BUS.register(new MagicEventHandler());
	}

	@Override
	public void registerItems() {
		basicMirror = new ItemMirror("mirror").setUnlocalizedName("mirror.basic");
		magicMirror = new ItemMagicMirror(1, 30, "magicMirror", 15, 1000).setUnlocalizedName("mirror.magic");
		celestialMirror = new ItemMagicMirror(31, 60, "celestialMirror",20, 10000).setUnlocalizedName("mirror.celestial");
		ring = new ItemRing().setUnlocalizedName("ring");
		bracelet = new ItemBracelet().setUnlocalizedName("bracelet");
		necklace = new ItemNecklace().setUnlocalizedName("necklace");
		magnet = new ItemMobMagnet(100).setUnlocalizedName("mobMagnet");

		RegistryHelper.register(new Object[] { basicMirror, magicMirror, celestialMirror, ring, bracelet, necklace, magnet });
	}

	//Keep this order
	private void registerJewelry() {
		pearlBlack = new PartPearlBlack(0);
		pearlBlue = new PartPearlBlue(1);
		pearlBrown = new PartPearlBrown(2);
		pearlGold = new PartPearlGold(3);
		pearlGreen = new PartPearlGreen(4);
		pearlOrange = new PartPearlOrange(5);
		pearlPink = new PartPearlPink(6);
		pearlPurple = new PartPearlPurple(7);
		pearlRed = new PartPearlRed(8);
		pearlSilver = new PartPearlSilver(9);
		pearlWhite = new PartPearlWhite(10);
		pearlYellow = new PartPearlYellow(11);
		new PartDiamond(12);
		new PartGold(13);
		new PartIron(14);
		new PartGoldString(15);
		new PartString(16);
		new PartOneRing(17);
		new PartGoldThread(18);
		new PartWool(19);
	}

	@Override
	public void registerOther() {
		registerJewelry();
		registerEnchants();
		// Setup IIcon
		MaricultureTab.tabJewelry.icon = new ItemStack(basicMirror);
	}

	@Override
	public void addRecipes() {
		//Enchant Book
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.guides, 1, GuideMeta.ENCHANTS), new Object[] {
			Items.book, new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		//Basic Mirror
		RecipeHelper.addShapedRecipe(new ItemStack(basicMirror), new Object[] {
			" AA", "APA", "SA ", 'A', "ingotAluminum", 'P', Blocks.glass_pane, 'S', "ingotIron"
		});
		
		//Magic Mirror
		RecipeHelper.addShapedRecipe(new ItemStack(magicMirror), new Object[] {
			"PMP", "BEB", "PBP", 
			'B', new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF), 
			'M', basicMirror, 
			'E', Blocks.enchanting_table, 
			'P', new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE)
		});
			
		//Celestial Mirror
		ItemStack drop = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_MAGIC): new ItemStack(Items.ghast_tear);
		RecipeHelper.addShapedRecipe(new ItemStack(celestialMirror), new Object[] {
			"TST", "BMB", "GBG", 
			'B', new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF), 
			'M', magicMirror, 
			'S', Items.nether_star,
			'T', drop,
			'G', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD)
		});
			
		addJewelry(Magic.ring, Jewelry.RING, Jewelry.RING_PART1, Jewelry.RING_PART2);
		addJewelry(Magic.bracelet, Jewelry.BRACELET, Jewelry.BRACELET_PART1, Jewelry.BRACELET_PART2);
		addJewelry(Magic.necklace, Jewelry.NECKLACE, Jewelry.NECKLACE_PART1, Jewelry.NECKLACE_PART2);
		addDungeonChestLoot();
		
		//Mob Magnet Crafting
		if(Extra.MOB_MAGNET) {
			RecipeHelper.addShapedRecipe(new ItemStack(magnet), new Object[] {
				"III", "I I", "M M", 'I', "ingotIron", 'M', Items.ender_pearl
			});
		}
	}

	private void addJewelry(Item jewelry, int type, String partOne, String partTwo) {
		for (int i = 0; i < JewelryPart.materialList.size(); i++) {
			for (int j = 0; j < JewelryPart.materialList.size(); j++) {
				if (JewelryPart.materialList.get(i).isValid(type) && JewelryPart.materialList.get(j).isValid(type)) {
					if (JewelryPart.materialList.get(i).getPartType(type).equals(partOne)) {
						if (JewelryPart.materialList.get(j).getPartType(type).equals(partTwo)) {
							ItemStack output = ItemJewelry.buildJewelry(jewelry, i, j);
							output = JewelryPart.materialList.get(i).addEnchantments(output);
							if (i != j) {
								output = JewelryPart.materialList.get(j).addEnchantments(output);
							}
							ItemStack input1 = JewelryPart.materialList.get(i).getItemStack();
							ItemStack input2 = JewelryPart.materialList.get(j).getItemStack();
							int Multiply1 = (type == Jewelry.RING)? 1: (type == Jewelry.BRACELET)? 3: 7;
							int Multiply2 = (type == Jewelry.RING)? 7: (type == Jewelry.BRACELET)? 2: 1;
							int frame = JewelryPart.materialList.get(i).getHits(type) * Multiply1;
							int other = JewelryPart.materialList.get(j).getHits(type) * Multiply2;
							int hits = frame + other;
							if (input1 != null && input2 != null && output != null) {
								JewelryHandler.addJewelry(output, input1, input2, type, hits);
							}
						}
					}
				}
			}
		}
		
		for (int i = 0; i < JewelryPart.materialList.size(); i++) {
			for (int j = 0; j < JewelryPart.materialList.size(); j++) {				
				if (JewelryPart.materialList.get(i).isValid(type) && JewelryPart.materialList.get(j).isValid(type)) {
					if (JewelryPart.materialList.get(i).getPartType(type).equals(partOne)) {
						if (JewelryPart.materialList.get(j).getPartType(type).equals(partTwo)) {
							ItemStack output = ItemJewelry.buildJewelry(jewelry, i, j);
							output = JewelryPart.materialList.get(i).addEnchantments(output);
							if (i != j) {
								output = JewelryPart.materialList.get(j).addEnchantments(output);
							}
							
							String name = output.getUnlocalizedName().substring(5);
							name += "." + JewelryPart.materialList.get(i).getPartName();
							if(!JewelryPart.materialList.get(i).isSingle())
								name += "." + JewelryPart.materialList.get(j).getPartName();
							MaricultureRegistry.register(name, output);
						}
					}
				}
			}
		}
	}

	private void addDungeonChestLoot() {
		if(EnchantHelper.exists(oneRing)) {
			ItemStack oneRing = MaricultureRegistry.get("ring.oneRing");
			if (oneRing != null) {
				ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(oneRing, 1, 3, 3));
			}
		}
	}
}
