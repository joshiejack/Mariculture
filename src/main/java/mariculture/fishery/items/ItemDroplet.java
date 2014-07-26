package mariculture.fishery.items;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.DropletMeta;
import mariculture.core.util.Fluids;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDroplet extends ItemMariculture {
    public ItemDroplet() {
        setCreativeTab(MaricultureTab.tabFishery);
    }

    @Override
    public int getMetaCount() {
        return DropletMeta.COUNT;
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case DropletMeta.AQUA:
                return "aqua";
            case DropletMeta.ATTACK:
                return "attack";
            case DropletMeta.ELECTRIC:
                return "electric";
            case DropletMeta.ENDER:
                return "ender";
            case DropletMeta.NETHER:
                return "nether";
            case DropletMeta.HEALTH:
                return "health";
            case DropletMeta.MAGIC:
                return "magic";
            case DropletMeta.POISON:
                return "poison";
            case DropletMeta.WATER:
                return "water";
            case DropletMeta.EARTH:
                return "earth";
            case DropletMeta.FROZEN:
                return "frozen";
            case DropletMeta.PLANT:
                return "plant";
            default:
                return "water";
        }
    }

    @Override
    public String getPotionEffect(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case DropletMeta.POISON:
                return PotionHelper.spiderEyeEffect;
            case DropletMeta.HEALTH:
                return PotionHelper.ghastTearEffect;
            case DropletMeta.NETHER:
                return PotionHelper.magmaCreamEffect;
            case DropletMeta.ATTACK:
                return PotionHelper.gunpowderEffect;
            default:
                return super.getPotionEffect(stack);
        }
    }

    public FluidStack getFluidStack(int dmg) {
        switch (dmg) {
            case DropletMeta.AQUA:
                return Fluids.getStack(Fluids.hp_water, 250);
            case DropletMeta.ATTACK:
                return null; // Add Destroying Liquid
            case DropletMeta.ELECTRIC:
                return null; // Add Electric Liquid
            case DropletMeta.ENDER:
                return null; // Add Ender Liquid
            case DropletMeta.NETHER:
                return new FluidStack(FluidRegistry.LAVA, 100);
            case DropletMeta.HEALTH:
                return null; // Add Blood Liquid
            case DropletMeta.MAGIC:
                return null; // Add Magical Liquid
            case DropletMeta.POISON:
                return null; // Add Poison Liquid
            case DropletMeta.WATER:
                return new FluidStack(FluidRegistry.WATER, 1000);
            case DropletMeta.EARTH:
                return Fluids.getStack(Fluids.dirt, 100);
            case DropletMeta.FROZEN:
                return null; //Add Molten Ice Liquid
            case DropletMeta.PLANT:
                return null; //Add Chlorophyll Liquid    
            default:
                return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[getMetaCount()];
        for (int i = 0; i < icons.length; i++)
            if (isActive(i)) {
                String name = getName(new ItemStack(this, 1, i));
                icons[i] = iconRegister.registerIcon(Mariculture.modid + ":droplet" + name.substring(0, 1).toUpperCase() + name.substring(1));
            }
    }
}
