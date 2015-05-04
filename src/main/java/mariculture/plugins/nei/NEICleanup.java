package mariculture.plugins.nei;

import java.util.Map.Entry;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.config.FishMechanics;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DropletMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RockMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TickingMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.WaterMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemDroplet;
import mariculture.lib.base.BlockBaseMeta;
import mariculture.lib.base.ItemBaseMeta;
import mariculture.magic.JewelryHandler;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import mariculture.magic.jewelry.parts.JewelryBinding;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import mariculture.plugins.PluginEnchiridion;
import mariculture.plugins.nei.NEIAnvilRecipeHandler.RecipeJewelry;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import codechicken.nei.api.API;
import cpw.mods.fml.common.Loader;

public class NEICleanup {
    public static void cleanup() {
        //Hide all Air Blocks
        API.hideItem(new ItemStack(Core.air, 1, OreDictionary.WILDCARD_VALUE));

        //All the block metas
        for (int meta = 0; meta < 16; meta++) {
            hide(meta, FoodMeta.COUNT, Core.food);
            hide(meta, GlassMeta.COUNT, Core.glass);
            hide(meta, LimestoneMeta.COUNT, Core.limestone);
            hide(meta, MachineMeta.COUNT, Core.machines);
            hide(meta, MachineMultiMeta.COUNT, Core.machinesMulti);
            hide(meta, MetalMeta.COUNT, Core.metals);
            hide(meta, PearlColor.COUNT, Core.pearlBlock);
            hide(meta, PearlColor.COUNT, Core.pearls);
            hide(meta, MachineRenderedMeta.COUNT, Core.renderedMachines);
            hide(meta, MachineRenderedMultiMeta.COUNT, Core.renderedMachinesMulti);
            hide(meta, RockMeta.COUNT, Core.rocks);
            hide(meta, GroundMeta.COUNT, Core.sands);
            hide(meta, TankMeta.COUNT, Core.tanks);
            hide(meta, TickingMeta.COUNT, Core.ticking);
            hide(meta, TransparentMeta.COUNT, Core.transparent);
            hide(meta, WaterMeta.COUNT, Core.water);
            hide(meta, WoodMeta.COUNT, Core.woods);
            hide(meta, 0, Core.worked);
            if (Modules.isActive(Modules.fishery)) {
                hide(meta, BaitMeta.COUNT, Fishery.bait);
                hide(meta, PearlColor.COUNT, Fishery.lampsOn);
            }

            if (Modules.isActive(Modules.worldplus)) {
                hide(meta, CoralMeta.COUNT, WorldPlus.plantStatic);
                hide(meta, CoralMeta.COUNT, WorldPlus.plantGrowable);
            }

            if (Loader.isModLoaded("Enchiridion")) {
                hide(meta, GuideMeta.COUNT, PluginEnchiridion.guides);
            }
        }

        //Hide all the custom blocks
        if (Modules.isActive(Modules.factory)) {
            API.hideItem(new ItemStack(Factory.customBlock, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customFence, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customFlooring, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customGate, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customLight, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customSlabs, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customSlabsDouble, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customStairs, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customWall, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customRFBlock, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(Factory.customRFWall, 1, OreDictionary.WILDCARD_VALUE));
        }

        //Hide the fish or show one
        if (Modules.isActive(Modules.fishery)) { //Hide all off neon lamps
            for (int i = 0; i < DropletMeta.COUNT; i++) {
                if (((ItemDroplet) Fishery.droplet).isActive(i)) {
                    API.addItemListEntry(new ItemStack(Fishery.droplet, 1, i));
                }
            }

            API.hideItem(new ItemStack(Fishery.lampsOff, 1, OreDictionary.WILDCARD_VALUE));

            if (FishMechanics.DISABLE_FISH) {
                API.hideItem(new ItemStack(Fishery.fishy));
            } else {
                for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
                    FishSpecies fishy = species.getValue();
                    ItemStack fish = Fishing.fishHelper.makePureFish(fishy);
                    API.addItemListEntry(fish);
                }
            }
        }

        add(CraftingMeta.COUNT, Core.crafting);
        add(BottleMeta.COUNT, Core.bottles);
        add(MaterialsMeta.COUNT, Core.materials);
        add(UpgradeMeta.COUNT, Core.upgrade);
        add(BucketMeta.COUNT, Core.buckets);

        //Add the jewelry
        if (Modules.isActive(Modules.magic)) {
            addJewelry((ItemJewelry) Magic.ring);
            addJewelry((ItemJewelry) Magic.bracelet);
            addJewelry((ItemJewelry) Magic.necklace);
        }
    }

    private static void add(int max, Item item) {
        for (int i = 0; i < max; i++) {
            if (!(item instanceof ItemBaseMeta) || ((item instanceof ItemBaseMeta && ((ItemBaseMeta) item).isActive(i)))) {
                API.addItemListEntry(new ItemStack(item, 1, i));
            }
        }
    }

    private static void hide(int meta, int max, Block block) {
        if (meta >= max || (block instanceof BlockBaseMeta && !((BlockBaseMeta) block).isActive(meta))) {
            API.hideItem(new ItemStack(block, 1, meta));
        }
    }

    private static void hide(int meta, int max, Item item) {
        if (meta >= max || (item instanceof ItemBaseMeta && !((ItemBaseMeta) item).isActive(meta))) {
            API.hideItem(new ItemStack(item, 1, meta));
        }
    }

    private static void addJewelry(ItemJewelry item) {
        JewelryType type = item.getType();
        for (Entry<String, JewelryBinding> binding : JewelryBinding.list.entrySet()) {
            if (binding.getValue().ignore) {
                continue;
            }
            for (Entry<String, JewelryMaterial> material : JewelryMaterial.list.entrySet()) {
                if (material.getValue().ignore) {
                    continue;
                }
                JewelryBinding bind = binding.getValue();
                JewelryMaterial mat = material.getValue();
                ItemStack worked = JewelryHandler.createJewelry(item, bind, mat);
                ItemStack output = JewelryHandler.createJewelry(item, binding.getValue(), material.getValue());
                int hits = (int) (bind.getHitsBase(type) * mat.getHitsModifier(type));
                NEIAnvilRecipeHandler.jewelry.put(output, new RecipeJewelry(MaricultureHandlers.anvil.createWorkedItem(worked, hits), hits));
            }
        }
    }
}
