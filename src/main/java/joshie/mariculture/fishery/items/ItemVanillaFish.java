package joshie.mariculture.fishery.items;

import java.util.List;
import java.util.Map.Entry;

import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.config.Vanilla;
import joshie.mariculture.core.lib.Extra;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.util.Translate;
import joshie.mariculture.fishery.FishyHelper;
import joshie.mariculture.plugins.PluginHungerOverhaul;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemVanillaFish extends ItemFishFood {
    public static final int LAST_VANILLA = ItemFishFood.FishType.PUFFERFISH.ordinal() + 1;

    public ItemVanillaFish(boolean bool) {
        super(false);
        setCreativeTab(MaricultureTab.tabFishery);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Fishing.fishHelper == null) return super.getItemStackDisplayName(stack);
        if (Vanilla.VANILLA_TEXTURES && stack.getItemDamage() < LAST_VANILLA) return super.getItemStackDisplayName(stack);
        FishSpecies species = Fishing.fishHelper.getSpecies(stack.getItemDamage());
        if (species == null) return Translate.translate("anyFish");
        return StatCollector.translateToLocal("fish.data.dead") + " " + Fishing.fishHelper.getSpecies(stack.getItemDamage()).getName();
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (Vanilla.VANILLA_TEXTURES && stack.getItemDamage() < LAST_VANILLA) return super.getIcon(stack, pass);
        FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
        return fish != null ? fish.getIcon(1) : super.getIcon(stack, pass);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if (Vanilla.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.getMaxItemUseDuration(stack);
        else {
            FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
            return fish != null ? fish.getFoodDuration() : 32;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (Vanilla.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.onItemRightClick(stack, world, player);
        else {
            FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
            if (fish != null) {
                if (player.canEat(fish.canAlwaysEat()) && fish.getFoodStat() >= 0) {
                    player.setItemInUse(stack, getMaxItemUseDuration(stack));
                }

                return fish.onRightClick(world, player, stack, itemRand);
            } else return super.onItemRightClick(stack, world, player);
        }
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        if (Vanilla.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.onEaten(stack, world, player);
        else {
            FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
            if (fish != null) {
                --stack.stackSize;
                int food = fish.getFoodStat();
                float sat = fish.getFoodSaturation();
                if (Extra.NERF_FOOD) {
                    food = Math.max(1, food / 2);
                    sat = Math.max(0.0F, sat / 10);
                }

                player.getFoodStats().addStats(food, sat);
                world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                fish.onConsumed(world, player);
                return stack;
            } else return super.onEaten(stack, world, player);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (Extra.NERF_FOOD) {
            FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
            if (fish != null) {
                int level = fish.getFoodStat();
                float sat = fish.getFoodSaturation();
                level = Math.max(1, level / 2);
                sat = Math.max(0.0F, sat / 10);
                PluginHungerOverhaul.addInformation(level, sat, list);
            }
        }
    }

    @Override
    public String getPotionEffect(ItemStack stack) {
        if (Vanilla.VANILLA_STATS && stack.getItemDamage() < LAST_VANILLA) return super.getPotionEffect(stack);
        else {
            if (Fishing.fishHelper == null) {
                Fishing.fishHelper = new FishyHelper();
            }
            FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
            if (fish != null) return fish.getPotionEffect(stack);
            else return super.getPotionEffect(stack);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!player.capabilities.isCreativeMode) {
            FishSpecies species = Fishing.fishHelper.getSpecies(stack.getItemDamage());
            if (species != null) if (species.destroyOnAttack()) {
                stack.stackSize--;
            }

            if (stack.stackSize == 0) {
                player.setCurrentItemOrArmor(0, null);
            }
        }

        return false;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        FishSpecies species = Fishing.fishHelper.getSpecies(stack.getItemDamage());
        if (species != null) return species.getModifiers(field_111210_e, super.getAttributeModifiers(stack));
        else return super.getAttributeModifiers(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        if (Modules.isActive(Modules.fishery)) {
            for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
                FishSpecies fishy = species.getValue();
                if (fishy.getRawForm(1).getItem() instanceof ItemVanillaFish) {
                    list.add(fishy.getRawForm(1));
                }
            }
        } else {
            super.getSubItems(item, creative, list);
        }
    }
}
