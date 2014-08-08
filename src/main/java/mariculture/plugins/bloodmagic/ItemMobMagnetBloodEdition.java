package mariculture.plugins.bloodmagic;

import java.util.List;

import mariculture.core.handlers.LogHandler;
import mariculture.magic.ItemMobMagnet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;

public class ItemMobMagnetBloodEdition extends ItemMobMagnet {
    public ItemMobMagnetBloodEdition(int dmg) {
        super(dmg);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (!stack.hasTagCompound()) {
            if (!stack.stackTagCompound.getString("ownerName").equals("")) {
                list.add("Current owner: " + stack.stackTagCompound.getString("ownerName"));
            }

            list.add(StatCollector.translateToLocal("mariculture.string.noBound1"));
            list.add(StatCollector.translateToLocal("mariculture.string.noBound2"));
            return;
        } else {
            if (!stack.stackTagCompound.getString("ownerName").equals("")) {
                list.add("Current owner: " + stack.stackTagCompound.getString("ownerName"));
            }

            list.add(StatCollector.translateToLocal("mariculture.string.bound"));
            list.add(stack.stackTagCompound.getString("MobName"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!stack.hasTagCompound()) return stack;

        try {
            SoulNetworkHandler.checkAndSetItemOwner(stack, player);
            boolean teleported = false;
            Entity entity = EntityList.createEntityByName(stack.stackTagCompound.getString("MobName"), world);
            List<EntityMob> enemies = world.getEntitiesWithinAABB(entity.getClass(), player.boundingBox.expand(32D, 32D, 32D));
            int x = (int) player.posX;
            int y = (int) (player.posY + 1);
            int z = (int) player.posZ;
            for (Object i : enemies)
                if (i instanceof EntityLivingBase) {
                    EntityLivingBase living = (EntityLivingBase) i;
                    living.setPositionAndUpdate(x, y, z);
                    teleported = true;
                    SoulNetworkHandler.syphonAndDamageFromNetwork(stack, player, (int) (100 * living.getHealth()));
                }

            if (teleported) {
                world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
            }
        } catch (Exception e) {
            LogHandler.log(Level.WARN, "Mob Magnet Failed to find class for the target entities!");
        }

        return stack;
    }
}
