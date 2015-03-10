package mariculture.fishery.items;

import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.ICaughtAliveModifier;
import mariculture.api.fishery.IGenderFixator;
import mariculture.core.items.ItemMCBaseArmor;
import mariculture.fishery.Fishery;
import mariculture.fishery.FishyHelper;
import mariculture.fishery.render.ModelFishingHat;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorFishingHat extends ItemMCBaseArmor implements ICaughtAliveModifier, IGenderFixator {
	private IIcon pink;
	private IIcon blue;
	
	private Random rand = new Random();

	public ItemArmorFishingHat(ArmorMaterial material, int j, int k) {
		super(material, j, k);
		setCreativeTab(MaricultureTab.tabFishery);
	}

	@Override
	public double getModifier() {
		return 5D;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.hasTagCompound()) {
			String texture = stack.getTagCompound().getString("Texture");
			return "mariculture:" + "textures/armor/" + texture + ".png";
		}

		return "mariculture:" + "textures/armor/fishinghat.png";
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int damage) {
		return 1;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {		
		if (!stack.hasTagCompound()) {
			return super.getIcon(stack, pass);
		} else {
			String texture = stack.getTagCompound().getString("Texture");
			if (texture.equals("fishinghat_pink")) {
				return pink;
			} else return blue;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
		return new ModelFishingHat();
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public Integer getGender(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return stack.getTagCompound().getInteger("Gender");
		}

		return rand.nextInt(2);
	}
	
	@Override
	public void registerIcons(IIconRegister register) {
		super.registerIcons(register);
		blue = register.registerIcon("mariculture:fishinghat_blue");
		pink = register.registerIcon("mariculture:fishinghat_pink");
    }
	
	public static ItemStack getGirl() {
		ItemStack girl = new ItemStack(Fishery.fishinghat);
		girl.setTagCompound(new NBTTagCompound());
		girl.getTagCompound().setString("Texture", "fishinghat_pink");
		girl.getTagCompound().setInteger("Gender", FishyHelper.FEMALE);
		return girl;
	}
	
	public static ItemStack getBoy() {
		ItemStack boy = new ItemStack(Fishery.fishinghat);
		boy.setTagCompound(new NBTTagCompound());
		boy.getTagCompound().setString("Texture", "fishinghat_blue");
		boy.getTagCompound().setInteger("Gender", FishyHelper.MALE);
		return boy;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item));
		list.add(getGirl());
		list.add(getBoy());
	}
}