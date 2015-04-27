package mariculture.plugins.minetweaker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.fishery.FishingHandler;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Fishing")
public class Fishing {
    private static RodType getRodTypeFromString(String name) {
        for (RodType type : RodType.rodTypes) {
            if (type.name.equalsIgnoreCase(name)) return type;
        }

        return RodType.DIRE;
    }

    //Adding Fishing Loot
    @ZenMethod
    public static void addJunk(IItemStack loot, double chance, @Optional String type, @Optional boolean exact, @Optional int[] dimension) {
        addLoot(MineTweakerMC.getItemStack(loot), chance, type, exact, dimension, Rarity.JUNK);
    }

    @ZenMethod
    public static void addGood(IItemStack loot, double chance, @Optional String type, @Optional boolean exact, @Optional int[] dimension) {
        addLoot(MineTweakerMC.getItemStack(loot), chance, type, exact, dimension, Rarity.GOOD);
    }

    @ZenMethod
    public static void addRare(IItemStack loot, double chance, @Optional String type, @Optional boolean exact, @Optional int[] dimension) {
        addLoot(MineTweakerMC.getItemStack(loot), chance, type, exact, dimension, Rarity.RARE);
    }

    private static void addLoot(ItemStack stack, double chance, String type, boolean exact, int[] dimension, Rarity rarity) {
        if (dimension == null || dimension.length == 0) dimension = new int[] { Short.MAX_VALUE };
        if (type == null) type = "dire";
        for (int dim : dimension) {
            MineTweakerAPI.apply(new AddLoot(new Loot(stack, chance, rarity, dim, getRodTypeFromString(type), exact)));
        }
    }

    private static class AddLoot implements IUndoableAction {
        private final Loot loot;

        public AddLoot(Loot loot) {
            this.loot = loot;
        }

        @Override
        public void apply() {
            FishingHandler.fishing_loot.get(loot.rarity).add(loot);
        }

        @Override
        public void undo() {
            FishingHandler.fishing_loot.get(loot.rarity).remove(loot);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Adding " + loot.loot.getDisplayName() + " to mariculture fishing loot with rarity: " + loot.rarity.name().toLowerCase();
        }

        @Override
        public String describeUndo() {
            return "Removing" + loot.loot.getDisplayName() + " from mariculture fishing loot with rarity: " + loot.rarity.name().toLowerCase();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    //Removing Fishing Loot, will remove it from every single list
    @ZenMethod
    public static void removeLoot(IItemStack loot) {
        MineTweakerAPI.apply(new RemoveLoot(MineTweakerMC.getItemStack(loot)));
    }

    private static class RemoveLoot implements IUndoableAction {
        private HashMap<Rarity, Loot> loot;
        private final ItemStack stack;

        public RemoveLoot(ItemStack stack) {
            this.loot = new HashMap();
            this.stack = stack;
        }

        @Override
        public void apply() {
            loot.clear();
            apply(Rarity.JUNK);
            apply(Rarity.GOOD);
            apply(Rarity.RARE);
        }

        //Performs the apply function on all rarity types
        public void apply(Rarity rarity) {
            Iterator<Loot> it = FishingHandler.fishing_loot.get(rarity).iterator();
            while (it.hasNext()) {
                Loot loot = it.next();
                if (loot.loot.isItemEqual(stack)) {
                    this.loot.put(rarity, loot);
                    it.remove();
                }
            }
        }

        @Override
        public void undo() {
            for (Map.Entry<Rarity, Loot> entry : loot.entrySet()) {
                undo(entry.getKey(), entry.getValue());
            }
        }

        //Undoes the action on the applicable rarities
        public void undo(Rarity rarity, Loot loot) {
            if (loot != null) {
                FishingHandler.fishing_loot.get(rarity).add(loot);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describe() {
            return "Removing " + stack.getDisplayName() + " from mariculture fishing loot";
        }

        @Override
        public String describeUndo() {
            return "Restoring" + stack.getDisplayName() + " to mariculture fishing loot";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
