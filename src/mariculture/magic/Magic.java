package mariculture.magic;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EnchantIds;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.UtilMeta;
import mariculture.magic.enchantments.EnchantmentBlink;
import mariculture.magic.enchantments.EnchantmentClock;
import mariculture.magic.enchantments.EnchantmentFallDamage;
import mariculture.magic.enchantments.EnchantmentFire;
import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentHealth;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentNeverHungry;
import mariculture.magic.enchantments.EnchantmentOneRing;
import mariculture.magic.enchantments.EnchantmentPoison;
import mariculture.magic.enchantments.EnchantmentPunch;
import mariculture.magic.enchantments.EnchantmentRestore;
import mariculture.magic.enchantments.EnchantmentResurrection;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
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
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

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
	
	public static Enchantment spider;
	public static Enchantment blink;
	public static Enchantment clock;
	public static Enchantment fall;
	public static Enchantment fire;
	public static Enchantment flight;
	public static Enchantment glide;
	public static Enchantment health;
	public static Enchantment jump;
	public static Enchantment hungry;
	public static Enchantment oneRing;
	public static Enchantment poison;
	public static Enchantment punch;
	public static Enchantment repair;
	public static Enchantment resurrection;
	public static Enchantment speed;
	
	public static Item basicMirror;
	public static Item magicMirror;
	public static Item celestialMirror;
	public static Item ring;
	public static Item bracelet;
	public static Item necklace;
	
	public void registerEnchants() {
		if(EnchantIds.spider > 0) { spider = new EnchantmentSpider(EnchantIds.spider, 4, EnumEnchantmentType.all); }
		if(EnchantIds.blink > 0) { blink = new EnchantmentBlink(EnchantIds.blink, 1, EnumEnchantmentType.all); }
		if(EnchantIds.clock > 0) { clock = new EnchantmentClock(EnchantIds.clock, 2, EnumEnchantmentType.all); }
		if(EnchantIds.fall > 0) { fall = new EnchantmentFallDamage(EnchantIds.fall, 10, EnumEnchantmentType.all); }
		if(EnchantIds.fire > 0) { fire = new EnchantmentFire(EnchantIds.fire, 16, EnumEnchantmentType.all); }
		if(EnchantIds.flight > 0) { flight = new EnchantmentFlight(EnchantIds.flight, 1, EnumEnchantmentType.all); }
		if(EnchantIds.glide > 0) { glide = new EnchantmentGlide(EnchantIds.glide, 8, EnumEnchantmentType.all); }
		if(EnchantIds.health > 0) { health = new EnchantmentHealth(EnchantIds.health, 8, EnumEnchantmentType.all); }
		if(EnchantIds.jump > 0) { jump = new EnchantmentJump(EnchantIds.jump, 12, EnumEnchantmentType.all); }
		if(EnchantIds.hungry > 0) { hungry = new EnchantmentNeverHungry(EnchantIds.hungry, 8, EnumEnchantmentType.all); }
		if(EnchantIds.oneRing > 0) { oneRing = new EnchantmentOneRing(EnchantIds.oneRing, 0, EnumEnchantmentType.all); }
		if(EnchantIds.poison > 0) { poison = new EnchantmentPoison(EnchantIds.poison, 16, EnumEnchantmentType.all); }
		if(EnchantIds.punch > 0) { punch = new EnchantmentPunch(EnchantIds.punch, 16, EnumEnchantmentType.all); }
		if(EnchantIds.repair > 0) { repair = new EnchantmentRestore(EnchantIds.repair, 6, EnumEnchantmentType.all); }
		if(EnchantIds.resurrection > 0) { resurrection = new EnchantmentResurrection(EnchantIds.resurrection, 1, EnumEnchantmentType.all); }
		if(EnchantIds.speed > 0) { speed = new EnchantmentSpeed(EnchantIds.speed, 10, EnumEnchantmentType.all); }
	}

	@Override
	public void registerHandlers() {
		MaricultureHandlers.mirror = new MirrorHandler();
		MinecraftForge.EVENT_BUS.register(new MagicEventHandler());
		GameRegistry.registerPlayerTracker(new PlayerTrackerHandler());
		TickRegistry.registerScheduledTickHandler(new EnchantUpdateTicker(), Side.SERVER);
		TickRegistry.registerScheduledTickHandler(new EnchantUpdateTicker(), Side.CLIENT);
	}

	@Override
	public void registerItems() {
		basicMirror = new ItemMirror(ItemIds.basicMirror, "mirror").setUnlocalizedName("mirror.basic");
		magicMirror = new ItemMagicMirror(ItemIds.magicMirror, 1, 30, "mirror").setUnlocalizedName("mirror.magic");
		celestialMirror = new ItemMagicMirror(ItemIds.celestialMirror, 31, 60, "celestialMirror").setUnlocalizedName("mirror.celestial");
		ring = new ItemRing(ItemIds.ring).setUnlocalizedName("ring");
		bracelet = new ItemBracelet(ItemIds.bracelet).setUnlocalizedName("bracelet");
		necklace = new ItemNecklace(ItemIds.necklace).setUnlocalizedName("necklace");

		RegistryHelper.register(new Object[] { basicMirror, magicMirror, celestialMirror, ring, bracelet, necklace });
	}

	//Keep this order
	private void registerJewelry() {
		new PartPearlBlack();
		new PartPearlBlue();
		new PartPearlBrown();
		new PartPearlGold();
		new PartPearlGreen();
		new PartPearlOrange();
		new PartPearlPink();
		new PartPearlPurple();
		new PartPearlRed();
		new PartPearlSilver();
		new PartPearlWhite();
		new PartPearlYellow();
		new PartDiamond();
		new PartGold();
		new PartIron();
		new PartGoldString();
		new PartString();
		new PartOneRing();
		new PartGoldThread();
		new PartWool();
	}

	@Override
	public void registerOther() {
		registerJewelry();
		registerEnchants();
		// Setup Icon
		MaricultureTab.tabJewelry.icon = new ItemStack(magicMirror);
	}

	@Override
	public void addRecipes() {
		//Enchant Book
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.guides, 1, GuideMeta.ENCHANTMENTS), new Object[] {
			Item.book, new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		//Basic Mirror
		RecipeHelper.addShapedRecipe(new ItemStack(basicMirror), new Object[] {
			" AA", "APA", "SA ", 'A', "ingotAluminum", 'P', Block.thinGlass, 'S', "ingotIron"
		});
		
		//Magic Mirror
		RecipeHelper.addShapedRecipe(new ItemStack(magicMirror), new Object[] {
			"PMP", "BEB", "PBP", 
			'B', new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF), 
			'M', basicMirror, 
			'E', Block.enchantmentTable, 
			'P', new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE)
		});
			
		//Celestial Mirror
		ItemStack drop = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_MAGIC): new ItemStack(Item.ghastTear);
		RecipeHelper.addShapedRecipe(new ItemStack(celestialMirror), new Object[] {
			"TST", "BMB", "GBG", 
			'B', new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF), 
			'M', magicMirror, 
			'S', Item.netherStar,
			'T', drop,
			'G', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD)
		});
			
		addJewelry(Magic.ring.itemID, Jewelry.RING, Jewelry.RING_PART1, Jewelry.RING_PART2);
		addJewelry(Magic.bracelet.itemID, Jewelry.BRACELET, Jewelry.BRACELET_PART1, Jewelry.BRACELET_PART2);
		addJewelry(Magic.necklace.itemID, Jewelry.NECKLACE, Jewelry.NECKLACE_PART1, Jewelry.NECKLACE_PART2);
		addDungeonChestLoot();
	}

	private void addJewelry(int id, int type, String partOne, String partTwo) {
		for (int i = 0; i < JewelryPart.materialList.size(); i++) {
			for (int j = 0; j < JewelryPart.materialList.size(); j++) {
				if (JewelryPart.materialList.get(i).isValid(type) && JewelryPart.materialList.get(j).isValid(type)) {
					if (JewelryPart.materialList.get(i).getPartType(type).equals(partOne)) {
						if (JewelryPart.materialList.get(j).getPartType(type).equals(partTwo)) {
							ItemStack output = ItemJewelry.buildJewelry(id, i, j);
							output = JewelryPart.materialList.get(i).addEnchantments(output);
							if (i != j) {
								output = JewelryPart.materialList.get(j).addEnchantments(output);
							}
							ItemStack input1 = JewelryPart.materialList.get(i).getItemStack();
							ItemStack input2 = JewelryPart.materialList.get(j).getItemStack();
							if (input1 != null && input2 != null && output != null) {
								JewelryHandler.addJewelry(output, input1, input2, type);
							}
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
