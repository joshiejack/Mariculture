package joshie.mariculture.fishery;

import static joshie.mariculture.core.lib.ItemLib.enderPearl;
import static joshie.mariculture.core.lib.ItemLib.eyeOfEnder;
import static joshie.mariculture.core.lib.ItemLib.ironWheel;
import static joshie.mariculture.core.lib.ItemLib.voidBottle;
import static joshie.mariculture.core.lib.ItemLib.xpBottle;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.Loot;
import joshie.mariculture.api.fishery.Loot.Rarity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.core.Core;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FishingLoot {
    public static void add() {
        addOverworldLoot();
        addNetherLoot();
        addEndLoot();

        Fishing.fishing.addLoot(new Loot(new ItemStack(xpBottle), 35D, Rarity.GOOD));
        Fishing.fishing.addLoot(new Loot(ironWheel, 25D, Rarity.JUNK));
    }

    private static void addEndLoot() {
        Fishing.fishing.addLoot(new Loot(voidBottle, 25D, Rarity.JUNK, 1, RodType.DIRE));
        Fishing.fishing.addLoot(new Loot(new ItemStack(enderPearl), 15D, Rarity.JUNK, 1, RodType.OLD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(eyeOfEnder), 10D, Rarity.JUNK, 1, RodType.OLD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_11), 5D, Rarity.GOOD, 1, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_mellohi), 5D, Rarity.GOOD, 1, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_stal), 5D, Rarity.GOOD, 1, RodType.GOOD));
    }

    private static void addNetherLoot() {
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.gold_nugget), 45D, Rarity.JUNK, -1, RodType.DIRE));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.ghast_tear), 15D, Rarity.JUNK, -1, RodType.OLD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_13), 5D, Rarity.GOOD, -1, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_blocks), 5D, Rarity.GOOD, -1, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_chirp), 5D, Rarity.GOOD, -1, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.potionitem, 1, 8195), 5D, Rarity.JUNK, -1, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.nether_star), 1D, Rarity.RARE, -1, RodType.SUPER));
    }

    private static void addOverworldLoot() {
        for (int i = 0; i < 12; i++) {
            Fishing.fishing.addLoot(new Loot(new ItemStack(Core.pearls, 1, i), 2.5D, Rarity.JUNK, 0, RodType.OLD));
            Fishing.fishing.addLoot(new Loot(new ItemStack(Core.pearls, 1, i), 5D, Rarity.GOOD, 0, RodType.OLD));
            Fishing.fishing.addLoot(new Loot(new ItemStack(Core.pearls, 1, i), 2.5D, Rarity.RARE, 0, RodType.OLD));
        }

        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_far), 5D, Rarity.GOOD, 0, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_strad), 5D, Rarity.GOOD, 0, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_mall), 5D, Rarity.GOOD, 0, RodType.GOOD));
        Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_mall), 5D, Rarity.GOOD, 0, RodType.GOOD));
    }
}
