package mariculture.core.items;

import java.util.List;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemBuckets extends ItemMCMeta {
    public ItemBuckets() {
        maxStackSize = 1;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return new ItemStack(Items.bucket);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
        int amount = fluid == null ? 0 : fluid.amount;
        list.add(FluidHelper.getFluidName(fluid));
        FluidHelper.getFluidQty(list, fluid, -1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, false);
        if (movingobjectposition == null) return stack;
        else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k)) return stack;

                if (movingobjectposition.sideHit == 0) {
                    --j;
                }

                if (movingobjectposition.sideHit == 1) {
                    ++j;
                }

                if (movingobjectposition.sideHit == 2) {
                    --k;
                }

                if (movingobjectposition.sideHit == 3) {
                    ++k;
                }

                if (movingobjectposition.sideHit == 4) {
                    --i;
                }

                if (movingobjectposition.sideHit == 5) {
                    ++i;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) return stack;

                if (tryPlaceContainedLiquid(stack, world, i, j, k) && !player.capabilities.isCreativeMode) return new ItemStack(Items.bucket);

            }

            return stack;
        }
    }

    public boolean tryPlaceContainedLiquid(ItemStack stack, World world, int x, int y, int z) {
        Block block = getFluid(stack.getItemDamage());
        if (block == null) return false;
        Material material = world.getBlock(x, y, z).getMaterial();
        boolean flag = !material.isSolid();
        if (!world.isAirBlock(x, y, z) && !flag) return false;
        else {
            if (!world.isRemote && flag && !material.isLiquid()) {
                world.func_147480_a(x, y, z, true);
            }

            world.setBlock(x, y, z, block, 0, 3);
            return true;
        }
    }

    public Block getFluid(int meta) {
        if (meta == BucketMeta.PRESSURE) return Fluids.getFluidBlock("hp_water");
        else if (meta == BucketMeta.DIRT) return Blocks.dirt;
        if (Modules.isActive(Modules.fishery)) {
            if (meta == BucketMeta.FISH_OIL) return Fluids.getFluidBlock("fish_oil");
            if (meta == BucketMeta.CUSTARD) return Fluids.getFluidBlock("custard");
            if (meta == BucketMeta.BLOOD) return Fluids.getFluidBlock("blood");
            if (meta == BucketMeta.CHLOROPHYLL) return Fluids.getFluidBlock("chlorophyll");
            if (meta == BucketMeta.ENDER) return Fluids.getFluidBlock("ender");
            if (meta == BucketMeta.FLUX) return Fluids.getFluidBlock("flux");
            if (meta == BucketMeta.GUNPOWDER) return Fluids.getFluidBlock("gunpowder");
            if (meta == BucketMeta.ICE) return Fluids.getFluidBlock("ice");
            if (meta == BucketMeta.MANA) return Fluids.getFluidBlock("mana");
            if (meta == BucketMeta.POISON) return Fluids.getFluidBlock("poison");
            if (meta == BucketMeta.WIND) return Fluids.getFluidBlock("wind");
        }

        return null;
    }

    public ItemStack getBucket(Block block) {
        if (block == Fluids.getFluidBlock("hp_water")) return new ItemStack(this, 1, BucketMeta.PRESSURE);
        else if (Modules.isActive(Modules.fishery)) {
            if (block == Fluids.getFluidBlock("fish_oil")) return new ItemStack(this, 1, BucketMeta.FISH_OIL);
            else if (block == Fluids.getFluidBlock("custard")) return new ItemStack(this, 1, BucketMeta.CUSTARD);
            else if (block == Fluids.getFluidBlock("blood")) return new ItemStack(this, 1, BucketMeta.BLOOD);
            else if (block == Fluids.getFluidBlock("chlorophyll")) return new ItemStack(this, 1, BucketMeta.CHLOROPHYLL);
            else if (block == Fluids.getFluidBlock("ender")) return new ItemStack(this, 1, BucketMeta.ENDER);
            else if (block == Fluids.getFluidBlock("flux")) return new ItemStack(this, 1, BucketMeta.FLUX);
            else if (block == Fluids.getFluidBlock("gunpowder")) return new ItemStack(this, 1, BucketMeta.GUNPOWDER);
            else if (block == Fluids.getFluidBlock("ice")) return new ItemStack(this, 1, BucketMeta.ICE);
            else if (block == Fluids.getFluidBlock("mana")) return new ItemStack(this, 1, BucketMeta.MANA);
            else if (block == Fluids.getFluidBlock("poison")) return new ItemStack(this, 1, BucketMeta.POISON);
            else if (block == Fluids.getFluidBlock("wind")) return new ItemStack(this, 1, BucketMeta.WIND);
            else return null;
        } else return null;
    }

    @Override
    public int getMetaCount() {
        return BucketMeta.COUNT;
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case BucketMeta.CUSTARD:
                return "custardBucket";
            case BucketMeta.DIRT:
                return "dirt";
            case BucketMeta.FISH_OIL:
                return "fishoil";
            case BucketMeta.PRESSURE:
                return "hpwater";
            case BucketMeta.BLOOD:
                return "blood";
            case BucketMeta.CHLOROPHYLL:
                return "chlorophyll";
            case BucketMeta.ENDER:
                return "ender";
            case BucketMeta.FLUX:
                return "flux";
            case BucketMeta.GUNPOWDER:
                return "gunpowder";
            case BucketMeta.ICE:
                return "ice";
            case BucketMeta.MANA:
                return "mana";
            case BucketMeta.POISON:
                return "poison";
            case BucketMeta.WIND:
                return "wind";
            case BucketMeta.IRON:
                return "bucketIron";
            case BucketMeta.GOLD:
                return "bucketGold";
            case BucketMeta.COPPER:
                return "bucketCopper";
            case BucketMeta.TIN:
                return "bucketTin";
            case BucketMeta.SILVER:
                return "bucketSilver";
            case BucketMeta.LEAD:
                return "bucketLead";
            case BucketMeta.BRONZE:
                return "bucketBronze";
            case BucketMeta.STEEL:
                return "bucketSteel";
            case BucketMeta.ALUMINUM:
                return "bucketAluminum";
            case BucketMeta.TITANIUM:
                return "bucketTitaniumBucket";
            case BucketMeta.MAGNESIUM:
                return "bucketMagnesium";
            case BucketMeta.NICKEL:
                return "bucketNickel";
            case BucketMeta.GLASS:
                return "bucketGlass";
            case BucketMeta.FISH_FOOD:
                return "bucketFishFood";
            case BucketMeta.RUTILE:
                return "bucketRutile";
            case BucketMeta.QUICKLIME:
                return "bucketQuicklime";
            case BucketMeta.SALT:
                return "bucketSalt";
            case BucketMeta.ELECTRUM:
                return "bucketElectrum";
            case BucketMeta.COBALT:
                return "bucketCobalt";
            case BucketMeta.ARDITE:
                return "bucketArdite";
            case BucketMeta.OSMIUM:
                return "bucketOsmium";
            case BucketMeta.PLATINUM:
                return "bucketPlatinum";
            case BucketMeta.ZINC:
                return "bucketZinc";
            default:
                return "container";
        }
    }
    
    @Override
    public boolean isActive(int meta) {
       if (FluidContainerRegistry.getFluidForFilledItem(new ItemStack(this, 1, meta)) == null) {
           return false;
       }
        
        switch (meta) {
            case BucketMeta.BRONZE:
                return OreDictionary.getOres("ingotBronze").size() > 0;
            case BucketMeta.LEAD:
                return OreDictionary.getOres("ingotLead").size() > 0;
            case BucketMeta.NICKEL:
                return OreDictionary.getOres("ingotNickel").size() > 0;
            case BucketMeta.SILVER:
                return OreDictionary.getOres("ingotSilver").size() > 0;
            case BucketMeta.STEEL:
                return OreDictionary.getOres("ingotSteel").size() > 0;
            case BucketMeta.TIN:
                return OreDictionary.getOres("ingotTin").size() > 0;
            case BucketMeta.ZINC:
                return OreDictionary.getOres("ingotZinc").size() > 0;
            case BucketMeta.ARDITE:
                return OreDictionary.getOres("ingotArdite").size() > 0;
            case BucketMeta.COBALT:
                return OreDictionary.getOres("ingotCobalt").size() > 0;
            case BucketMeta.PLATINUM:
                return OreDictionary.getOres("ingotPlatinum").size() > 0;
            case BucketMeta.OSMIUM:
                return OreDictionary.getOres("ingotOsmium").size() > 0;
            case BucketMeta.ELECTRUM:
                return OreDictionary.getOres("ingotElectrum").size() > 0;
            case BucketMeta.FISH_OIL:
                return Modules.isActive(Modules.fishery);
            case BucketMeta.FISH_FOOD:
                return Modules.isActive(Modules.fishery);
            default:
                return true;
        }
    }
}