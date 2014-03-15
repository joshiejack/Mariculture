package mariculture.fishery.items;

import java.util.List;

import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fish;
import mariculture.fishery.FishHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
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
		return StatCollector.translateToLocal("fish.data.dead") + " " + Fishing.fishHelper.getSpecies(stack.getItemDamage()).getName();
	}
	
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if(Extra.VANILLA_TEXTURES && dmg < LAST_VANILLA) return super.getIconFromDamage(dmg);
		FishSpecies fish = Fishing.fishHelper.getSpecies(dmg);
		return fish != null? fish.getIcon(): super.getIconFromDamage(dmg);
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
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if(Extra.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.onEaten(stack, world, player);
		else {
			FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
			if(fish != null) {
				--stack.stackSize;
				int food = !Loader.isModLoaded("HungerOverhaul")? fish.getFoodStat(): fish.getFoodStat() > 2? 2: fish.getFoodStat();
				float sat = !Loader.isModLoaded("HungerOverhaul")? fish.getFoodSaturation(): fish.getFoodSaturation()/10;
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
			FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
			if(fish != null) {
				return fish.getPotionEffect(stack);
			} else return super.getPotionEffect(stack);
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creative, List list) {
		for (int i = 0; i < FishSpecies.speciesList.size(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
