package mariculture.core.items;

import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.Modules;
import mariculture.lib.helpers.ItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;
import squeek.applecore.api.food.ItemFoodProxy;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "squeek.applecore.api.food.IEdible", modid = "AppleCore")
public class ItemFood extends ItemMCMeta implements IEdible {
    @Optional.Method(modid = "AppleCore")
    @Override
    public FoodValues getFoodValues(ItemStack stack) {
        int level = getFoodLevel(stack.getItemDamage());
        float sat = getFoodSaturation(stack.getItemDamage());
        return new FoodValues(level, sat);
    }

    @Optional.Method(modid = "AppleCore")
    public void onEatenCompatibility(ItemStack stack, EntityPlayer player) {
        player.getFoodStats().func_151686_a(new ItemFoodProxy(this), stack);
    }

    private int getFoodLevel(int dmg) {
        switch (dmg) {
            case FoodMeta.FISH_FINGER:
                return 2;
            case FoodMeta.CALAMARI:
                return 4;
            case FoodMeta.CALAMARI_HALF:
                return 3;
            case FoodMeta.SMOKED_SALMON:
                return 10;
            case FoodMeta.CAVIAR:
                return 3;
            case FoodMeta.CUSTARD:
                return 1;
            case FoodMeta.FISH_N_CUSTARD:
                return 4;
            case FoodMeta.KELP_WRAP:
                return 1;
            case FoodMeta.SUSHI:
                return 6;
            case FoodMeta.MISO_SOUP_1:
                return 4;
            case FoodMeta.MISO_SOUP_2:
                return 5;
            case FoodMeta.MISO_SOUP_3:
                return 6;
            case FoodMeta.OYSTER:
                return 20;
            default:
                return 1;
        }
    }

    private float getFoodSaturation(int dmg) {
        switch (dmg) {
            case FoodMeta.FISH_FINGER:
                return 0.5F;
            case FoodMeta.CALAMARI:
                return 0.85F;
            case FoodMeta.CALAMARI_HALF:
                return 0.85F;
            case FoodMeta.SMOKED_SALMON:
                return 1F;
            case FoodMeta.CAVIAR:
                return 0.25F;
            case FoodMeta.CUSTARD:
                return 0.3F;
            case FoodMeta.FISH_N_CUSTARD:
                return 0.9F;
            case FoodMeta.KELP_WRAP:
                return 0.1F;
            case FoodMeta.SUSHI:
                return 0.7F;
            case FoodMeta.MISO_SOUP_1:
                return 1.9F;
            case FoodMeta.MISO_SOUP_2:
                return 1.1F;
            case FoodMeta.MISO_SOUP_3:
                return 1.2F;
            case FoodMeta.OYSTER:
                return 5.0F;
            default:
                return 0.3F;
        }
    }

    private ItemStack getLeftovers(int meta) {
        switch (meta) {
            case FoodMeta.CALAMARI:
                return new ItemStack(Core.food, 1, FoodMeta.CALAMARI_HALF);
            case FoodMeta.CALAMARI_HALF:
                return new ItemStack(Items.bowl);
            case FoodMeta.CUSTARD:
                return new ItemStack(Items.bowl);
            case FoodMeta.FISH_N_CUSTARD:
                return new ItemStack(Items.bowl);
            case FoodMeta.MISO_SOUP_1:
                return new ItemStack(Items.bowl);
            case FoodMeta.MISO_SOUP_2:
                return new ItemStack(Core.food, 1, FoodMeta.MISO_SOUP_1);
            case FoodMeta.MISO_SOUP_3:
                return new ItemStack(Core.food, 1, FoodMeta.MISO_SOUP_2);
            default:
                return null;
        }
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        int meta = stack.getItemDamage();
        if (meta == FoodMeta.DEBUG_FOOD) {
            player.getFoodStats().addExhaustion(100);
            player.getFoodStats().addStats(-10, 0F);
            return stack;
        } else {
            if (!player.capabilities.isCreativeMode) --stack.stackSize;
            ItemStack bowl = getLeftovers(meta);
            if (bowl != null && stack.stackSize > 0) ItemHelper.addToPlayerInventory(player, bowl);
            if(Extra.NERF_FOOD) {
                onEatenCompatibility(stack, player);
            } else {
                int level = getFoodLevel(stack.getItemDamage());
                float sat = getFoodSaturation(stack.getItemDamage());
                player.getFoodStats().addStats(level, sat);
            }

            world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            if (!world.isRemote && player.shouldHeal() && meta == FoodMeta.KELP_WRAP) player.heal(2);

            return bowl != null && stack.stackSize == 0 ? bowl : stack;
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case FoodMeta.FISH_FINGER:
                return 16;
            case FoodMeta.FISH_N_CUSTARD:
                return 48;
            case FoodMeta.MISO_SOUP_1:
                return 64;
            case FoodMeta.MISO_SOUP_2:
                return 64;
            case FoodMeta.MISO_SOUP_3:
                return 64;
            case FoodMeta.OYSTER:
                return 128;
            case FoodMeta.DEBUG_FOOD:
                return 4;
            default:
                return 32;
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.eat;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.canEat(false) || stack.getItemDamage() == FoodMeta.DEBUG_FOOD) {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return stack;
    }

    @Override
    public int getMetaCount() {
        return FoodMeta.COUNT;
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case FoodMeta.FISH_FINGER:
                return "fishFinger";
            case FoodMeta.CALAMARI:
                return "calamari";
            case FoodMeta.CALAMARI_HALF:
                return "calamari2";
            case FoodMeta.SMOKED_SALMON:
                return "smokedSalmon";
            case FoodMeta.CAVIAR:
                return "caviar";
            case FoodMeta.CUSTARD:
                return "custard";
            case FoodMeta.FISH_N_CUSTARD:
                return "fishNCustard";
            case FoodMeta.KELP_WRAP:
                return Modules.isActive(Modules.worldplus) ? "kelpWrap" : "cactusWrap";
            case FoodMeta.SUSHI:
                return "sushi";
            case FoodMeta.MISO_SOUP_1:
                return "misoSoup";
            case FoodMeta.MISO_SOUP_2:
                return "misoSoup2";
            case FoodMeta.MISO_SOUP_3:
                return "misoSoup3";
            case FoodMeta.OYSTER:
                return "oyster";
            case FoodMeta.DEBUG_FOOD:
                return "debug";
            default:
                return "food";
        }
    }

    @Override
    public boolean isActive(int meta) {
        if (meta == FoodMeta.OYSTER) return true;
        else return Modules.isActive(Modules.fishery);
    }

    @Override
    public boolean isValidTab(CreativeTabs creative, int meta) {
        if (meta == FoodMeta.OYSTER) return creative == MaricultureTab.tabCore;
        if (meta == FoodMeta.KELP_WRAP) return creative == MaricultureTab.tabWorld;
        return creative == MaricultureTab.tabFishery;
    }
}
