package mariculture.core.items;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.core.config.GeneralStuff;
import mariculture.core.events.MaricultureEvents;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.MCTranslate;
import mariculture.lib.helpers.ClientHelper;
import mariculture.lib.util.Text;
import mariculture.world.EntityRockhopper;
import mariculture.world.WorldPlus;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrafting extends ItemMCMeta implements IEnergyContainerItem {
    @Override
    public int getMetaCount() {
        return CraftingMeta.COUNT;
    }

    @Override
    public String getName(ItemStack stack) {
        int meta = stack.getItemDamage();
        switch (meta) {
            case CraftingMeta.GOLDEN_SILK:
                return "goldenSilk";
            case CraftingMeta.GOLDEN_THREAD:
                return "goldenThread";
            case CraftingMeta.POLISHED_STICK:
                return "polishedStick";
            case CraftingMeta.NEOPRENE:
                return "neoprene";
            case CraftingMeta.PLASTIC:
                return "plastic";
            case CraftingMeta.LENS:
                return "scubaLens";
            case CraftingMeta.ALUMINUM_SHEET:
                return "aluminumSheet";
            case CraftingMeta.HEATER:
                return "heater";
            case CraftingMeta.COOLER:
                return "cooler";
            case CraftingMeta.CARBIDE:
                return "carbide";
            case CraftingMeta.WHEEL:
                return "wheel";
            case CraftingMeta.WICKER:
                return "wicker";
            case CraftingMeta.GOLD_SHEET:
                return "goldSheet";
            case CraftingMeta.DRAGON_EGG:
                return "dragonEgg";
            case CraftingMeta.POLISHED_TITANIUM:
                return "titaniumRod";
            case CraftingMeta.BLANK_PLAN:
                return "plan_blank";
            case CraftingMeta.TITANIUM_SHEET:
                return "titaniumSheet";
            case CraftingMeta.LENS_GLASS:
                return "snorkelLens";
            case CraftingMeta.BURNT_BRICK:
                return "burntBrick";
            case CraftingMeta.TITANIUM_ROD:
                return "titaniumRodBasic";
            case CraftingMeta.LIFE_CORE:
                return "lifeCore";
            case CraftingMeta.SEEDS_KELP:
                return "kelpSeeds";
            case CraftingMeta.CREATIVE_BATTERY:
                return "batteryCreative";
            case CraftingMeta.THERMOMETER:
                return "thermometer";
            case CraftingMeta.ROCKHOPPER_EGG:
                return "rockhopperEgg";
        }

        return MaricultureEvents.getItemName(this, meta, "machines");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = getUnlocalizedName().replace("item", "").replace("_", ".");
        String name = getName(stack).replaceAll("(.)([A-Z])", "$1$2").toLowerCase();
        return Text.localize(unlocalized.replace("mariculture.", MaricultureEvents.getMod(stack.getItem(), stack.getItemDamage(), "mariculture") + ".") + "." + name);
    }

    public boolean spawnEnderDragon(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        if (world.isRemote) return true;
        else {
            EntityDragon dragon = new EntityDragon(world);
            dragon.setPosition(player.posX, player.posY + 10, player.posZ);
            world.spawnEntityInWorld(dragon);
            stack.stackSize--;
        }

        return true;
    }

    private void displayTemperature(boolean isSneaking, World world, int x, int y, int z) {
        if (world.isRemote) {
            String prefix = "";
            int temperature = 0;
            if (isSneaking) {
                prefix = MCTranslate.translate("temperature.generic");
                temperature = MaricultureHandlers.environment.getBiomeTemperature(world, x, y, z);
            } else {
                prefix = MCTranslate.translate("temperature.precise");
                temperature = MaricultureHandlers.environment.getTemperature(world, x, y, z);
            }

            if (world.isDaytime()) ClientHelper.addToChat(MCTranslate.translate("time") + " " + MCTranslate.translate("time.day"));
            else ClientHelper.addToChat(MCTranslate.translate("time") + " " + MCTranslate.translate("time.night"));
            ClientHelper.addToChat(prefix + ": " + temperature + mariculture.lib.util.Text.DEGREES);
            ClientHelper.addToChat(MCTranslate.translate("environment.salinity") + ": " + MCTranslate.translate("salinity." + MaricultureHandlers.environment.getSalinity(world, x, z).name().toLowerCase()));
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
        int dmg = stack.getItemDamage();
        if (dmg == CraftingMeta.DRAGON_EGG && GeneralStuff.ENABLE_ENDER_SPAWN) return spawnEnderDragon(stack, player, world, x, y, z);
        else if (dmg == CraftingMeta.THERMOMETER) {
            displayTemperature(player.isSneaking(), world, x, y, z);
        } else if (Modules.isActive(Modules.worldplus) && dmg == CraftingMeta.SEEDS_KELP) {
            if (Item.getItemFromBlock(WorldPlus.plantGrowable).onItemUse(new ItemStack(WorldPlus.plantGrowable, 1, 1), player, world, x, y, z, side, par8, par9, par10)) {
                stack.stackSize--;
            }
        } else if (Modules.isActive(Modules.worldplus) && dmg == CraftingMeta.ROCKHOPPER_EGG) {
            if (!world.isRemote) {
                EntityRockhopper rockhopper = new EntityRockhopper(world);
                rockhopper.setPosition(x + 0.5D, y + 1D, z + 0.5D);
                world.spawnEntityInWorld(rockhopper);
            }

        }

        return true;
    }

    @Override
    public boolean isActive(int meta) {
        switch (meta) {
            case CraftingMeta.DRAGON_EGG:
                return MaricultureHandlers.HIGH_TECH_ENABLED && Modules.isActive(Modules.fishery);
            case CraftingMeta.BLANK_PLAN:
                return Modules.isActive(Modules.factory);
            case CraftingMeta.POLISHED_STICK:
            case CraftingMeta.POLISHED_TITANIUM:
                return Modules.isActive(Modules.fishery);
            case CraftingMeta.NEOPRENE:
            case CraftingMeta.PLASTIC:
            case CraftingMeta.LENS:
            case CraftingMeta.LIFE_CORE:
            case CraftingMeta.CREATIVE_BATTERY:
                return MaricultureHandlers.HIGH_TECH_ENABLED;
            case CraftingMeta.SEEDS_KELP:
            case CraftingMeta.ROCKHOPPER_EGG:
                return Modules.isActive(Modules.worldplus);
            default:
                return true;
        }
    }

    @Override
    public boolean isValidTab(CreativeTabs creative, int meta) {
        if (meta == CraftingMeta.BLANK_PLAN) return creative == MaricultureTab.tabFactory;
        if (meta == CraftingMeta.SEEDS_KELP || meta == CraftingMeta.ROCKHOPPER_EGG) return creative == MaricultureTab.tabWorld;
        if (meta == CraftingMeta.THERMOMETER) return creative == MaricultureTab.tabFishery;
        return creative == MaricultureTab.tabCore;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        String path = this.path != null ? this.path : mod + ":";
        icons = new IIcon[getMetaCount()];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = register.registerIcon(MaricultureEvents.getMod(this, i, "mariculture") + ":" + getName(new ItemStack(this, 1, i)));
        }
    }

    //Used for the creative battery ;D//
    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
        return container.getItemDamage() == CraftingMeta.CREATIVE_BATTERY ? 10000000 : 0;
    }

    @Override
    public int getEnergyStored(ItemStack container) {
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {
        return 0;
    }
}
