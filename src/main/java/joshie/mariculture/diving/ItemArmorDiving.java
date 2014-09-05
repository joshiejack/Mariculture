package joshie.mariculture.diving;

import joshie.lib.util.Library;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.OreDicHelper;
import joshie.mariculture.core.items.ItemMCBaseArmor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorDiving extends ItemMCBaseArmor {
    public ItemArmorDiving(ArmorMaterial material, int j, int k) {
        super(material, j, k);
        setCreativeTab(MaricultureTab.tabWorld);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == Diving.divingPants) return "mariculture:" + "textures/armor/diving" + "_2.png";
        return "mariculture:" + "textures/armor/diving" + "_1.png";
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() == Diving.divingHelmet && OreDicHelper.convert(stack2).equals("ingotCopper")) return true;
        if (stack1.getItem() == Diving.divingBoots && OreDicHelper.convert(stack2).equals("ingotIron")) return true;
        if ((stack1.getItem() == Diving.divingTop || stack1.getItem() == Diving.divingPants) && stack2.getItem() == Items.leather) return true;
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (Library.DEBUG_ON) {
            int size = 32;
            if (!world.isRemote) {
                for (int x = (int) (player.posX - size); x < player.posX + size; x++) {
                    for (int z = (int) (player.posZ - size); z < player.posZ + size; z++) {
                        for (int y = 0; y < 155; y++)
                            if (player.isSneaking()) {
                                if (world.getBlock(x, y, z) == Blocks.stone || world.getBlock(x, y, z) == Blocks.dirt || world.getBlock(x, y, z) == Blocks.grass || world.getBlock(x, y, z) == Blocks.sandstone || world.getBlock(x, y, z) == Blocks.gravel || world.getBlock(x, y, z).getMaterial() == Material.water || world.getBlock(x, y, z) == Blocks.sand) {
                                    world.setBlockToAir(x, y, z);
                                }
                            } else if (world.getBlock(x, y, z) == Core.limestone) {
                                world.setBlockToAir(x, y, z);
                            }
                    }
                }
            }
        }

        return stack;
    }
}