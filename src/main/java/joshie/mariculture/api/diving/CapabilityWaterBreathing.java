package joshie.mariculture.api.diving;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/** This capability should be returned for items that allow you to breathe underwater
 *  Mariculture will make use of this, and disable hardcore diving where applicable **/
public class CapabilityWaterBreathing {
    @CapabilityInject(IDisableHardcoreDiving.class)
    public static Capability<IDisableHardcoreDiving> BREATHING_CAPABILITY = null;

    public interface IDisableHardcoreDiving {
        /** If the player can breathe underwater
         *
         * @param player    the player
         * @param stack     the stack we are validating
         * @return  whether the player is wearing something that allows them to breathe underwater */
        default boolean canBreatheUnderwater(EntityPlayer player, ItemStack stack) {
            return true;
        }
    }

    /** The storage implementation of this capability **/
    public static class WaterBreathingStorage implements IStorage<IDisableHardcoreDiving> {
        @Override
        public NBTBase writeNBT(Capability capability, IDisableHardcoreDiving instance, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability capability, IDisableHardcoreDiving instance, EnumFacing side, NBTBase nbt) {}
    }


    /** The wrapper class, return this in your initCapabilities for your items, or your own
     *  Or your own implementation of course */
    public static class WaterbreathingWrapper implements IDisableHardcoreDiving, ICapabilityProvider {
        protected final ItemStack stack;

        public WaterbreathingWrapper(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return capability == BREATHING_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if (capability == BREATHING_CAPABILITY) {
                return BREATHING_CAPABILITY.cast(this);
            }

            return null;
        }
    }
}
