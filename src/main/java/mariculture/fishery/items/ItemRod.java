package mariculture.fishery.items;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.core.util.IItemRegistry;
import mariculture.fishery.FishingRodHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRod extends ItemFishingRod implements IItemRegistry {
	private int enchant;
	public ItemRod() {
		this(EnumRodQuality.OLD, 127, 1);
	}
	
	public ItemRod(EnumRodQuality quality, int max, int enchant) {
		this.enchant = enchant;
		setMaxStackSize(1);
		setCreativeTab(MaricultureTab.tabMariculture);
		if(max > 0) this.setMaxDamage(max);
		if(Fishing.rodHandler == null) Fishing.rodHandler = new FishingRodHandler();
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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String theName, name = getUnlocalizedName().substring(5);
		String[] aName = name.split("\\.");
		if(aName.length == 2) theName = aName[0] + aName[1].substring(0, 1).toUpperCase() + aName[1].substring(1);
		else theName = name;
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + theName);
	}
	
	@Override
	public void register(Item item) {
		MaricultureRegistry.register(getName(new ItemStack(item)), new ItemStack(item));
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return getUnlocalizedName().substring(5);
	}
}
