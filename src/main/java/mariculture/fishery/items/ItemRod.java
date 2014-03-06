package mariculture.fishery.items;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRod extends ItemFishingRod {
	private int enchant;
	public ItemRod(EnumRodQuality quality, int max, int enchant) {
		this.enchant = enchant;
		this.setMaxStackSize(1);
		this.setCreativeTab(MaricultureTab.tabMariculture);
		if(max > 0) this.setMaxDamage(max);
		Fishing.rodHandler.registerRod(this, quality);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return Fishing.rodHandler.handleRightClick(stack, world, player);
    }
	
	@Override
	public int getItemEnchantability() {
		return enchant;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)));
	}
}
