package mariculture.magic.jewelry;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.Text;
import mariculture.core.util.IItemRegistry;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.parts.JewelryPart;
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

public class ItemJewelry extends Item implements IItemRegistry {

	private Icon[] parts;
	private Icon[] special;
	private Icon blank;

	public ItemJewelry(int id) {
		super(id);
		this.setMaxDamage(100);
		this.setMaxStackSize(1);
		this.setCreativeTab(MaricultureTab.tabJewelry);
		setNoRepair();
        canRepair = false;
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

	public int getType() {
		return 0;
	}

	public String getTypeString() {
		return "blank";
	}

	public String getPart1() {
		return "blank";
	}

	public String getPart2() {
		return "blank";
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int meta) {
		return 3;
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if (stack.hasTagCompound()) {
			if (pass == 0) {
				int i = stack.stackTagCompound.getInteger("Part1");
				if (JewelryPart.materialList.get(i).isValid(getType())) {
					if (JewelryPart.materialList.get(i).isVisible(getType())) {
						return parts[i];
					}
				}
			} else if (pass == 1) {
				if (stack.stackTagCompound.hasKey("Part2")) {
					int i = stack.stackTagCompound.getInteger("Part2");
					if (JewelryPart.materialList.get(i).isValid(getType())) {
						if (JewelryPart.materialList.get(i).isVisible(getType())) {
							return parts[i];
						}
					}
				}
			} else if (pass == 2) {
				if (stack.stackTagCompound.hasKey("Extra")) {
					if(stack.stackTagCompound.getInteger("Extra") < special.length) {
						return special[stack.stackTagCompound.getInteger("Extra")];
					}
				}
			}
		}

		return blank;
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
       if(stack.hasTagCompound()) {
    	   //Jewel + Pearls/Iron
    	   int part1 = stack.stackTagCompound.getInteger("Part1");
    	   
    	   //String + Metals
    	   int part2 = stack.stackTagCompound.getInteger("Part2");
    	   double modifier = 1.0D;
    	   double base = 100;
    	   
    	   if(getType() == Jewelry.RING) {
    		   modifier = JewelryPart.materialList.get(part1).getDurabilityModifier(getType());
    		   base = JewelryPart.materialList.get(part2).getDurabilityBase(getType());
    	   } else {
    		   base = JewelryPart.materialList.get(part1).getDurabilityBase(getType());
    		   modifier = JewelryPart.materialList.get(part2).getDurabilityModifier(getType());
    	   }
    	   
    	   return (int) (base * modifier);
       }
        return 0;
    }

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.hasKey("Part2")) {
				int id = stack.stackTagCompound.getInteger("Part2");
				if (JewelryPart.materialList.get(id) != null) {
					String color = JewelryPart.materialList.get(id).getColor();
					String name = JewelryPart.materialList.get(id).getPartLang();

					list.add(color + StatCollector.translateToLocal("mariculture.string.with") + " " + StatCollector.translateToLocal(name));
                    int id2 = stack.stackTagCompound.getInteger("Part1");
                    boolean enabled = JewelryPart.materialList.get(id).isEnabled(getType()) && JewelryPart.materialList.get(id2).isEnabled(getType());
                    if(!enabled)
                        list.add(Text.DARK_RED + StatCollector.translateToLocal("mariculture.string.jewelry.disabled"));
				}
			}
		}

		if (EnchantHelper.getLevel(Magic.oneRing, stack) > 0) {
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line1"));
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line2"));
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line3"));
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line4"));
			list.add(" ");
		}

		if (EnchantHelper.getLevel(Magic.clock, stack) > 0) {
			if (stack.stackTagCompound.getInteger("Extra") == Jewelry.DAY) {
				list.add(Text.DARK_GREEN + "(" + StatCollector.translateToLocal("mariculture.string.keepDay") + ")");
			}

			if (stack.stackTagCompound.getInteger("Extra") == Jewelry.NIGHT) {
				list.add(Text.RED + "(" + StatCollector.translateToLocal("mariculture.string.keepNight") + ")");
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack.hasTagCompound() && !world.isRemote) {
			if (EnchantHelper.getLevel(Magic.clock, stack) > 0) {
				if (stack.stackTagCompound.getInteger("Extra") == Jewelry.NIGHT) {
					stack.stackTagCompound.setInteger("Extra", Jewelry.DAY);
				} else {
					stack.stackTagCompound.setInteger("Extra", Jewelry.NIGHT);
				}
			}
		}

		return stack;
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			int id = stack.stackTagCompound.getInteger("Part1");
			if (JewelryPart.materialList.get(id) != null) {
				String color = JewelryPart.materialList.get(id).getColor();
				String name = JewelryPart.materialList.get(id).getPartLang();

				return color + StatCollector.translateToLocal(name) + " "
						+ StatCollector.translateToLocal("part.jewelry." + getTypeString());
			}
		}

		return StatCollector.translateToLocal(this.getUnlocalizedName(stack));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister iconRegister) {
		blank = iconRegister.registerIcon(Mariculture.modid + ":jewelry/blank");
		special = new Icon[2];
		special[0] = iconRegister.registerIcon(Mariculture.modid + ":jewelry/day");
		special[1] = iconRegister.registerIcon(Mariculture.modid + ":jewelry/night");

		parts = new Icon[JewelryPart.materialList.size()];
		for (int i = 0; i < parts.length; i++) {
			if (JewelryPart.materialList.get(i).isValid(getType())) {
				if (JewelryPart.materialList.get(i).isVisible(getType())) {
					parts[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "jewelry/" + getTypeString() + "/"
							+ JewelryPart.materialList.get(i).getPartType(getType()) + "/"
							+ JewelryPart.materialList.get(i).getPartName());
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < JewelryPart.materialList.size(); i++) {
			boolean added = false;
			for (int j = 0; j < JewelryPart.materialList.size() && !added; j++) {
				if (JewelryPart.materialList.get(i).isValid(getType()) && JewelryPart.materialList.get(j).isValid(getType())) {
					if (JewelryPart.materialList.get(i).getPartType(getType()).equals(getPart1())) {
						if (JewelryPart.materialList.get(j).getPartType(getType()).equals(getPart2())) {
							int part1 = i;
							int part2 = j;
							if (JewelryPart.materialList.get(i).isSingle()) {
								part2 = part1;
							}
							ItemStack stack = buildJewelry(id, part1, part2);
							stack = JewelryPart.materialList.get(i).addEnchantments(stack);
							if (i != j) {
								stack = JewelryPart.materialList.get(j).addEnchantments(stack);
							}

                            if(JewelryPart.materialList.get(i).isEnabled(getType()) && JewelryPart.materialList.get(j).isEnabled(getType())) {
							    list.add(stack);
                            }

							added = JewelryPart.materialList.get(i).addOnce();
						}
					}
				}
			}
		}
	}

	public static ItemStack buildJewelry(int id, int part1, int part2) {
		ItemStack stack = new ItemStack(id, 1, 0);
		stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setInteger("Part1", part1);
		if (part1 != part2) {
			stack.stackTagCompound.setInteger("Part2", part2);
		}
		return stack;
	}
	
	public static int getPieceID(String piece) {
		for (int i = 0; i < JewelryPart.materialList.size(); i++) {
			JewelryPart part = JewelryPart.materialList.get(i);

			if (part.getPartName().equals(piece)) {
				return i;
			}
		}

		return 0;
	}

	@Override
	public void register() {		
		
	}

	@Override
	public int getMetaCount() {
		return 0;
	}

	@Override
	public String getName(ItemStack stack) {
		return null;
	}
}
