package mariculture.plugins;

import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.RodType.RodTypeFlux;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PluginRedstoneArsenal extends Plugin {
    public static RodType INFUSED;

    public PluginRedstoneArsenal(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        if (Modules.isActive(Modules.fishery)) {
            INFUSED = new RodTypeInfused(75, 1D, 12D, 4.5D, 0);
            Fishing.fishing.registerRod(getItem("tool.fishingRodFlux"), INFUSED);
            Fishing.fishing.addBait(getItem("material", 0), 80);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.ANT), INFUSED);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.MAGGOT), INFUSED);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.BEE), INFUSED);
            Fishing.fishing.addBaitForQuality(getItem("material", 0), INFUSED);
        }
    }

    @Override
    public void postInit() {
        return;
    }

    private static class RodTypeInfused extends RodTypeFlux {
        public RodTypeInfused(int quality, double junk, double good, double rare, int enchantment) {
            super("INFUSED", quality, junk, good, rare, enchantment);
        }

        @Override
        public boolean canUseBaitManually() {
            return false;
        }

        @Override
        public ItemStack damage(World world, EntityPlayer player, ItemStack stack, int fish, Random rand) {
            if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("Energy")) return stack;

            if (stack.stackTagCompound.getInteger("Energy") <= 0) return stack;

            int energy = stack.stackTagCompound.getInteger("Energy");
            int energyExtracted = Math.min(energy, stack.stackTagCompound.getByte("Empowered") == 1 ? 500 : 200);
            energy -= energyExtracted;
            stack.stackTagCompound.setInteger("Energy", energy);

            return stack;
        }
    }
}
