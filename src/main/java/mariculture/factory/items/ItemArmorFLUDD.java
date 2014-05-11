package mariculture.factory.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.lib.RenderMeta;
import mariculture.core.util.IItemRegistry;
import mariculture.factory.render.ModelFLUDD;
import mariculture.factory.render.RenderFLUDDSquirt;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorFLUDD extends ItemArmor implements IItemRegistry {
	public static final int HOVER = 0;
	public static final int ROCKET = 1;
	public static final int TURBO = 2;
	public static final int SQUIRT = 3;
	public static final int STORAGE = 20000;

	public ItemArmorFLUDD(final int i, final EnumArmorMaterial material, final int j, final int k) {
		super(i, material, j, k);
		this.setCreativeTab(MaricultureTab.tabMariculture);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		final int i1 = world.getBlockId(x, y, z);

		if (i1 == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			par7 = 1;
		} else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID) {
			if (par7 == 0) {
				--y;
			}

			if (par7 == 1) {
				++y;
			}

			if (par7 == 2) {
				--z;
			}

			if (par7 == 3) {
				++z;
			}

			if (par7 == 4) {
				--x;
			}

			if (par7 == 5) {
				++x;
			}
		}

		if (!player.canPlayerEdit(x, y, z, par7, stack)) {
			return false;
		} else if (stack.stackSize == 0) {
			return false;
		} else {
			if (stack.hasTagCompound() && !world.isRemote) {
				int blockID = Core.rendered.blockID;

				if (world.canPlaceEntityOnSide(blockID, x, y, z, false, par7, (Entity) null, stack)) {
					Block block = Block.blocksList[blockID];
					int j1 = block.onBlockPlaced(world, x, y, z, par7, par8, par9, par10, RenderMeta.FLUDD_STAND);

					world.setBlock(x, y, z, blockID, j1, 3);

					if (world.getBlockId(x, y, z) == blockID) {
						Block.blocksList[blockID].onBlockPlacedBy(world, x, y, z, player, stack);
						Block.blocksList[blockID].onPostBlockPlaced(world, x, y, z, j1);
					}

					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getPlaceSound(),
							(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
					--stack.stackSize;
				}
			}

			return true;
		}
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)));
		RenderFLUDDSquirt.icon = iconRegister.registerIcon(Mariculture.modid + ":" + "water");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		ItemStack stack = new ItemStack(j, 1, 0);
		stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setInteger("mode", 0);
		stack.stackTagCompound.setInteger("water", STORAGE);
		list.add(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if (stack.hasTagCompound()) {
			list.add(stack.stackTagCompound.getInteger("water") + " " + StatCollector.translateToLocal("mariculture.string.water"));
		}
	}

	public static int getMode(final ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.getInteger("water") > 0) {
				return stack.stackTagCompound.getInteger("mode");
			}
		}
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return new ModelFLUDD((float) (1.0 / 20.0));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		return "mariculture:" + "textures/blocks/fludd_texture.png";
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, j)), new ItemStack(this.itemID, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return "fludd";
	}

	public ItemStack build() {
		ItemStack fludd = new ItemStack(this);
		fludd.setTagCompound(new NBTTagCompound());
		fludd.stackTagCompound.setInteger("mode", 0);
		fludd.stackTagCompound.setInteger("water", 0);
		return fludd;
	}
}