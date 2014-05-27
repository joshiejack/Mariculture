package mariculture.fishery.items;

import java.util.List;
import java.util.Map.Entry;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.core.util.Text;
import mariculture.fishery.FishyHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemVanillaFish extends ItemFishFood {
	public static final int LAST_VANILLA = ItemFishFood.FishType.PUFFERFISH.ordinal() + 1;
	
	public ItemVanillaFish(boolean bool) {
		super(false);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(Fishing.fishHelper == null) return super.getItemStackDisplayName(stack);
		if(Extra.VANILLA_TEXTURES && stack.getItemDamage() < LAST_VANILLA) return super.getItemStackDisplayName(stack);
		FishSpecies species = Fishing.fishHelper.getSpecies(stack.getItemDamage());
		if(species == null) return Text.translate("anyFish");
		return StatCollector.translateToLocal("fish.data.dead") + " " + Fishing.fishHelper.getSpecies(stack.getItemDamage()).getName();
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if(Extra.VANILLA_TEXTURES && stack.getItemDamage() < LAST_VANILLA) return super.getIcon(stack, pass);
		FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
		return fish != null? fish.getIcon(1): super.getIcon(stack, pass);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		if(Extra.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.getMaxItemUseDuration(stack);
		else {
			FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
			return fish != null? fish.getFoodDuration(): 32;
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(Extra.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.onItemRightClick(stack, world, player);
		else {
			FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
			if(fish != null) {
				if(player.canEat(fish.canAlwaysEat())) {
					player.setItemInUse(stack, getMaxItemUseDuration(stack));
				}
				
				return fish.onRightClick(world, player, stack, itemRand);
			} else return super.onItemRightClick(stack, world, player);
		}
    }
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if(Extra.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.onEaten(stack, world, player);
		else {
			FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
			if(fish != null) {
				--stack.stackSize;
				int food = fish.getFoodStat();
				float sat = fish.getFoodSaturation();
				if(Extra.NERF_FOOD) {
					food = Math.max(1, food/2);
					sat = Math.max(0.0F, sat/10);
				}
				
				player.getFoodStats().addStats(food, sat);
				world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
				fish.onConsumed(world, player);
				return stack;
			} else return super.onEaten(stack, world, player);
		}
    }
	
	@Override
	public String getPotionEffect(ItemStack stack) {
		if(Extra.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.getPotionEffect(stack);
		else {
			if(Fishing.fishHelper == null) Fishing.fishHelper = new FishyHelper();
			FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
			if(fish != null) {
				return fish.getPotionEffect(stack);
			} else return super.getPotionEffect(stack);
		}
    }
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!player.capabilities.isCreativeMode) {
			FishSpecies species = Fishing.fishHelper.getSpecies(stack.getItemDamage());
			if(species != null) {
				if(species.destroyOnAttack()) {
					stack.stackSize--;
				}
			}
			
			if(stack.stackSize == 0) {
				player.setCurrentItemOrArmor(0, null);
			}
		}

		return false;
    }
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		FishSpecies species = Fishing.fishHelper.getSpecies(stack.getItemDamage());
		if(species != null) {
			return species.getModifiers(field_111210_e, super.getAttributeModifiers(stack));
		} else return super.getAttributeModifiers(stack);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creative, List list) {
		if(Modules.isActive(Modules.fishery)) {
			for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
				FishSpecies fishy = species.getValue();
				list.add(new ItemStack(item, 1, fishy.getID()));
			}
		} else {
			super.getSubItems(item, creative, list);
		}
	}
}
