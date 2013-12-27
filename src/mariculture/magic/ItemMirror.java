package mariculture.magic;

import java.util.List;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.helpers.MirrorHelper;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMirror extends Item implements IItemRegistry {

	private Icon[] icons;
	public static int mirrorSize = 32;

	public ItemMirror(final int i) {
		super(i);
		maxStackSize = 1;
		this.setUnlocalizedName("Mirror");
		this.setCreativeTab(MaricultureTab.tabJewelry);
		this.setMaxDamage(10);
		setHasSubtypes(true);
	}

	public static ItemStack itemStack;
	private static MirrorHelper saver = new MirrorHelper();
	private static EntityPlayer player;

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		world.playSoundAtEntity(entityplayer, Mariculture.modid + ":mirror", 1.0F, 1.0F);
		if (itemstack != null) {
			if (entityplayer.isSneaking()) {
				if (itemstack.hasTagCompound()) {
					if (itemstack.stackTagCompound.getBoolean("magic") == true
							&& !itemstack.stackTagCompound.getBoolean("ultimate")) {
						if (itemstack.stackTagCompound.getInteger("charge") < 30) {
							itemstack.stackTagCompound.setInteger("charge",
									itemstack.stackTagCompound.getInteger("charge") + 3);
						} else {
							itemstack.stackTagCompound.setInteger("charge", 3);
						}

						itemstack.setItemDamage(10 - (itemstack.stackTagCompound.getInteger("charge") / 3));
					}

					if (itemstack.stackTagCompound.getBoolean("ultimate")) {
						if (itemstack.stackTagCompound.getInteger("charge") < 60) {
							itemstack.stackTagCompound.setInteger("charge",
									itemstack.stackTagCompound.getInteger("charge") + 3);
						} else {
							itemstack.stackTagCompound.setInteger("charge", 30);
						}

						itemstack.setItemDamage(10 - (itemstack.stackTagCompound.getInteger("charge") / 3));
					}
				}
			} else {
				ItemMirror.itemStack = itemstack;
				ItemMirror.player = entityplayer;

				loadMirror(entityplayer, itemstack, world);

				boolean magic = false;

				if (itemstack.hasTagCompound()) {
					if (itemstack.stackTagCompound.getBoolean("magic") == true) {
						magic = true;
					}
				}

				if (!entityplayer.capabilities.isCreativeMode && !magic) {
					itemstack.damageItem(1, entityplayer);
					if (itemstack.getItemDamage() <= 1) {
						entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
					}
				}

			}

			if (world.isRemote && entityplayer.isSneaking()) {
				if(!itemstack.hasTagCompound()) {
					itemstack.setTagCompound(new NBTTagCompound());
				}
				
				if (itemstack.stackTagCompound.getBoolean("update") == false) {
					itemstack.stackTagCompound.setBoolean("update", true);
				} else {
					itemstack.stackTagCompound.setBoolean("update", false);
				}
			}

			return itemstack;
		}

		return null;
	}

	private static void loadMirror(EntityPlayer player, ItemStack itemstack, World world) {
		player.openGui(Mariculture.instance, GuiIds.MIRROR, world, 0, 0, 0);
	}

	public static ItemStack[] loadMirrorContents(final ItemStack itemStack, final int slot, final EntityPlayer player) {
		return MirrorHelper.instance().get(player);
	}

	public static void saveMirrorContents(final ItemStack itemStack, final ItemStack[] mirrorContents,
			final EntityPlayer player) {
		MirrorHelper.instance().save(player, mirrorContents);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public String getItemDisplayName(final ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().getBoolean("magic") == true) {
				return StatCollector.translateToLocal(getUnlocalizedName(stack)) + "("
						+ stack.getTagCompound().getInteger("charge") + ")";
			}
		}

		return ("" + StatCollector.translateToLocal(this.getUnlocalizedName(stack))).trim();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			if (itemstack.getTagCompound().getBoolean("magic") == true) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean getIsRepairable(final ItemStack firstItem, final ItemStack secondItem) {
		return secondItem.itemID == Core.materials.itemID && secondItem.getItemDamage() == MaterialsMeta.INGOT_ALUMINUM;
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.getBoolean("ultimate")) {
				return icons[1];
			}
		}

		return icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[2];
		icons[0] = iconRegister.registerIcon(Mariculture.modid + ":" + "mirror0");
		icons[1] = iconRegister.registerIcon(Mariculture.modid + ":" + "mirror1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		if (j == Magic.basicMirror.itemID) {
			ItemStack mirror = new ItemStack(j, 1, 0);
			list.add(mirror);
		}

		if (j == Magic.mirror.itemID) {
			ItemStack magicMirror = new ItemStack(j, 1, 0);
			if (!magicMirror.hasTagCompound()) {
				magicMirror.setTagCompound(new NBTTagCompound());
			}

			magicMirror.stackTagCompound.setBoolean("magic", true);
			magicMirror.stackTagCompound.setInteger("charge", 30);

			list.add(magicMirror);

		}

		if (j == Magic.celestialMirror.itemID) {
			ItemStack ultimateMirror = new ItemStack(j, 1, 0);
			if (!ultimateMirror.hasTagCompound()) {
				ultimateMirror.setTagCompound(new NBTTagCompound());
			}

			ultimateMirror.stackTagCompound.setBoolean("ultimate", true);
			ultimateMirror.stackTagCompound.setBoolean("magic", true);
			ultimateMirror.stackTagCompound.setInteger("charge", 60);
			list.add(ultimateMirror);
		}
	}

	@Override
	public void register() {
		if (this.itemID == Magic.basicMirror.itemID) {
			ItemStack mirror = new ItemStack(this.itemID, 1, 0);
			MaricultureRegistry.register("mirror", mirror);
		}

		if (this.itemID == Magic.mirror.itemID) {
			ItemStack magicMirror = new ItemStack(this.itemID, 1, 0);
			if (!magicMirror.hasTagCompound()) {
				magicMirror.setTagCompound(new NBTTagCompound());
			}

			magicMirror.stackTagCompound.setBoolean("magic", true);
			magicMirror.stackTagCompound.setInteger("charge", 30);
			MaricultureRegistry.register("magicMirror", magicMirror);

		}

		if (this.itemID == Magic.celestialMirror.itemID) {
			ItemStack ultimateMirror = new ItemStack(this.itemID, 1, 0);
			if (!ultimateMirror.hasTagCompound()) {
				ultimateMirror.setTagCompound(new NBTTagCompound());
			}

			ultimateMirror.stackTagCompound.setBoolean("ultimate", true);
			ultimateMirror.stackTagCompound.setBoolean("magic", true);
			ultimateMirror.stackTagCompound.setInteger("charge", 60);
			MaricultureRegistry.register("ultimateMirror", ultimateMirror);
		}
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return this.getUnlocalizedName().substring(5);
	}
}