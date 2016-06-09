package joshie.mariculture.modules.debug;

import joshie.mariculture.modules.EventContainer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/** This is for assistance when fishing, killing a chicken
 *  will yield whatever fishing would as it's drops, allowing
 *  one to test the loot rates of fishing more easily than, you could otherwise*/
@EventContainer(modules = "debug, fishing")
public class DebugFishing {
    private static final Random rand = new Random();

    @SubscribeEvent
    public void onKillChicken(LivingDeathEvent event) {
        if (Debug.CHICKEN_FISHING > 0) {
            EntityPlayer angler = event.getSource().getEntity() instanceof EntityPlayer ? (EntityPlayer) event.getSource().getEntity() : null;
            if (angler != null && !angler.worldObj.isRemote && event.getEntityLiving() instanceof EntityChicken) {
                for (int i = 0; i < Debug.CHICKEN_FISHING; i++) {
                    LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) angler.worldObj);
                    lootcontext$builder.withLuck((float) EnchantmentHelper.getLuckOfSeaModifier(angler) + angler.getLuck());
                    lootcontext$builder.withPlayer(angler); //Added by Mariculture
                    lootcontext$builder.withLootedEntity(event.getEntityLiving()); //Added by Mariculture

                    for (ItemStack itemstack : angler.worldObj.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(this.rand, lootcontext$builder.build())) {
                        EntityItem entityitem = new EntityItem(angler.worldObj, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, itemstack);
                        double d0 = angler.posX - event.getEntityLiving().posX;
                        double d1 = angler.posY - event.getEntityLiving().posY;
                        double d2 = angler.posZ - event.getEntityLiving().posZ;
                        double d3 = (double) MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
                        double d4 = 0.1D;
                        entityitem.motionX = d0 * d4;
                        entityitem.motionY = d1 * d4 + (double) MathHelper.sqrt_double(d3) * 0.08D;
                        entityitem.motionZ = d2 * d4;
                        angler.worldObj.spawnEntityInWorld(entityitem);
                        angler.worldObj.spawnEntityInWorld(new EntityXPOrb(angler.worldObj, angler.posX, angler.posY + 0.5D, angler.posZ + 0.5D, this.rand.nextInt(6) + 1));
                    }
                }
            }
        }
    }
}
