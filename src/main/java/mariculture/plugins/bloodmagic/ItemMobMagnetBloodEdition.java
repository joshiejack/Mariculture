package mariculture.plugins.bloodmagic;

import java.util.List;
import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.magic.ItemMobMagnet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;

public class ItemMobMagnetBloodEdition extends ItemMobMagnet {
	public ItemMobMagnetBloodEdition(int i, int dmg) {
		super(i, dmg);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!stack.hasTagCompound())
			return stack;
		
		try {
			EnergyItems.checkAndSetItemOwner(stack, player);
			String entity = stack.stackTagCompound.getString("MobClass").trim();
			Class clazz = Class.forName(stack.stackTagCompound.getString("MobClass").trim());
			
			boolean teleported = false;
			List<EntityMob> enemies = world.getEntitiesWithinAABB(clazz, player.boundingBox.expand(32D, 32D, 32D));
			int x = (int) player.posX;
			int y = (int) (player.posY + 1);
			int z = (int) player.posZ;
			for(Object i: enemies) {
				if (i instanceof EntityLivingBase) {
					EntityLivingBase living = (EntityLivingBase) i;
					living.setPositionAndUpdate(x, y, z);
					teleported = true;
					EnergyItems.syphonBatteries(stack, player, (int) (100 * living.getHealth()));
				}
			}
			
			if(teleported) {
				world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
			}
		} catch (Exception e) {
			LogHandler.log(Level.WARNING, "Mob Magnet Failed to find class for the target entities!");
		}
		
		return stack;
	}
}
