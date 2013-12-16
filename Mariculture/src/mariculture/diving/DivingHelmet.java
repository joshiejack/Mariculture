package mariculture.diving;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DivingHelmet {
	private static final float LEVEL = 3.5F;
	
	public static boolean init(EntityPlayer player) {
		if(player.worldObj.isRemote) {
			if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.divingHelmet)) {
				if (player.isInsideOfMaterial(Material.water)) {
					activate(player);
				}  else {
					deactivate(player);
				}
			} else {
				deactivate(player);
			}
		}
		
		return false;
	}
	
	private static void activate(EntityPlayer player) {
		if(Minecraft.getMinecraft().thePlayer == player) {
			float gamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
			if(gamma <= 1) {						
				ItemStack mask = PlayerHelper.getArmor(player, ArmorSlot.HAT, Diving.divingHelmet);
				if(mask != null) {
					if(!mask.hasTagCompound()) {
						mask.setTagCompound(new NBTTagCompound());
					}
										
					mask.stackTagCompound.setFloat("gamma", gamma);
						
				}
			}
				
			Minecraft.getMinecraft().gameSettings.gammaSetting=LEVEL;
		}
	}
	
	private static void deactivate(EntityPlayer player) {
		if(Minecraft.getMinecraft().thePlayer == player) {
			if(Minecraft.getMinecraft().gameSettings.gammaSetting > 1F) {
				float gamma = 0.75F;
				ItemStack mask = PlayerHelper.getArmor(player, ArmorSlot.HAT, Diving.divingHelmet);
				if(mask != null) {
					if(mask.hasTagCompound()) {
						gamma = mask.stackTagCompound.getFloat("gamma");
						
					}
				}
				
				Minecraft.getMinecraft().gameSettings.gammaSetting=gamma;
			}
		}
	}
}
