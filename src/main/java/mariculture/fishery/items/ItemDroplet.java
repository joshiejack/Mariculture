package mariculture.fishery.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.items.ItemMCMeta;
import mariculture.core.lib.DropletMeta;
import mariculture.core.util.Fluids;
import mariculture.core.util.MCTranslate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDroplet extends ItemMCMeta {
    public ItemDroplet() {
        setCreativeTab(MaricultureTab.tabFishery);
    }

    @Override
    public int getMetaCount() {
        return DropletMeta.COUNT;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
        int amount = fluid == null ? 0 : fluid.amount;
        list.add(MCTranslate.translate("contains") + " " + amount + MCTranslate.translate("mb") + " " + MCTranslate.translate("of") + " " + FluidHelper.getFluidName(fluid));
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
            case DropletMeta.USELESS:
                return "useless";
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
                return Fluids.getBalancedStack("hp_water");
            case DropletMeta.ATTACK:
                return Fluids.getBalancedStack("gunpowder"); // Add Destroying Liquid (Exploding Concentrate)
            case DropletMeta.ELECTRIC:
                return Fluids.getBalancedStack("flux"); // Add Electric Liquid (Electrified Goo)
            case DropletMeta.ENDER:
                return Fluids.getBalancedStack("ender"); // Add Ender Liquid (TE Ender/TiC Ender)
            case DropletMeta.NETHER:
                return new FluidStack(FluidRegistry.LAVA, 100);
            case DropletMeta.HEALTH:
                return Fluids.getBalancedStack("blood"); // Add Blood Liquid (BM Life Essence)
            case DropletMeta.MAGIC:
                return Fluids.getBalancedStack("mana"); // Add Magical Liquid (TE Mana)
            case DropletMeta.POISON:
                return Fluids.getBalancedStack("poison"); // Add Poison Liquid (BOP Poison)
            case DropletMeta.WATER:
                return new FluidStack(FluidRegistry.WATER, 1000);
            case DropletMeta.EARTH:
                return Fluids.getBalancedStack("dirt");
            case DropletMeta.FROZEN:
                return Fluids.getBalancedStack("ice"); //Add Molten Ice Liquid (TE Cryotheum)
            case DropletMeta.PLANT:
                return Fluids.getBalancedStack("chlorophyll"); //Add Chlorophyll Liquid
            default:
                return null;
        }
    }
    
    @Override
    public boolean isActive(int meta) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[getMetaCount()];
        for (int i = 0; i < icons.length; i++) {
            if (isActive(i)) {
                String name = getName(new ItemStack(this, 1, i));
                icons[i] = iconRegister.registerIcon(Mariculture.modid + ":droplet" + name.substring(0, 1).toUpperCase() + name.substring(1));
            }
        }
    }
}
