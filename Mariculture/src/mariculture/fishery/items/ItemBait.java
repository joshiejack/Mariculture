package mariculture.fishery.items;

import java.util.List;

import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Mariculture;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.FoodMeta;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBait extends ItemMariculture {
	public ItemBait(int i) {
		super(i);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 8;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.canEat(false)) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;

		int fill = (((Fishing.bait.getEffectiveness(stack)) + 1)/2 > 0)? ((Fishing.bait.getEffectiveness(stack)) + 1)/2: 1;
		float sat = fill/5;
		
		player.getFoodStats().addStats(fill, sat);

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		return stack;
	}
	
	@Override
	public int getMetaCount() {
		return BaitMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case BaitMeta.WORM: {
			name = "worm";
			break;
		}
		case BaitMeta.ANT: {
			name = "ant";
			break;
		}
		case BaitMeta.MAGGOT: {
			name = "maggot";
			break;
		}
		case BaitMeta.HOPPER: {
			name = "hopper";
			break;
		}
		default:
			name = "bait";
		}

		return name;
	}
}
