package mariculture.plugins.bloodmagic;

import java.util.Random;

import mariculture.api.fishery.RodType;
import mariculture.fishery.Fish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;

public class BloodRodType extends RodType {
    public BloodRodType(int quality, double junk, double good, double rare, int enchantment) {
        super(quality, junk, good, rare, enchantment);
    }

    @Override
    public boolean caughtAlive(String species) {
        if (species.equals(Fish.angler.getSpecies())) return true;
        if (species.equals(Fish.stickleback.getSpecies())) return true;
        if (species.equals(Fish.manOWar.getSpecies())) return true;
        if (species.equals(Fish.tuna.getSpecies())) return true;
        if (species.equals(Fish.salmon.getSpecies())) return true;
        if (species.equals(Fish.lung.getSpecies())) return true;
        return false;
    }

    @Override
    public float getDamage() {
        return 4.0F;
    }

    @Override
    public boolean canFish(World world, int x, int y, int z, EntityPlayer player, ItemStack stack) {
        if (stack.hasTagCompound()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(stack.stackTagCompound.getString("ownerName"));
            return entityOwner != null;
        } else return false;
    }

    @Override
    public ItemStack damage(World world, EntityPlayer player, ItemStack stack, int fish, Random rand) {
        if (player != null) {
            SoulNetworkHandler.syphonAndDamageFromNetwork(stack, player, fish * 250);
        } else if (!world.isRemote && SoulNetworkHandler.syphonFromNetwork(stack, fish * 250) != 0) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(stack.stackTagCompound.getString("ownerName"));
            if (entityOwner != null) {
                entityOwner.addPotionEffect(new PotionEffect(Potion.confusion.id, 500));
            }
        }

        return stack;
    }
}
