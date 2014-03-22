package mariculture.fishery.items;

import java.util.List;

import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishyFood extends Item {
	public ItemFishyFood(int i) {
		super(i);
		this.setCreativeTab(MaricultureTab.tabFish);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		if(Fishing.fishHelper == null) return super.getItemStackDisplayName(stack);
		return StatCollector.translateToLocal("fish.data.dead") + " " + Fishing.fishHelper.getSpecies(stack.getItemDamage()).getName();
	}

	@Override
	public Icon getIconFromDamage(int dmg) {
		FishSpecies fish = Fishing.fishHelper.getSpecies(dmg);
		return fish != null? fish.getIcon(): super.getIconFromDamage(dmg);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
		return fish != null? fish.getFoodDuration(): 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
		if(fish != null) {
			if(player.canEat(fish.canAlwaysEat())) {
				player.setItemInUse(stack, getMaxItemUseDuration(stack));
			}
				
			return fish.onRightClick(world, player, stack, itemRand);
		} else return super.onItemRightClick(stack, world, player);
    }

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
		if(fish != null) {
			--stack.stackSize;
			int food = fish.getFoodStat();
			float sat = fish.getFoodSaturation();
			if(Loader.isModLoaded("HungerOverhaul")) {
				food = Math.max(1, food/2);
				sat = Math.max(0.0F, sat/10);
			}
				
			player.getFoodStats().addStats(food, sat);
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			fish.onConsumed(world, player);
			return stack;
		} else return super.onEaten(stack, world, player);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		for (int i = 0; i < FishSpecies.speciesList.size(); ++i) {
			list.add(new ItemStack(j, 1, i));
		}
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
	}
}
