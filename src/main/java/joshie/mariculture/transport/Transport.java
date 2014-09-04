package joshie.mariculture.transport;

import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.RecipeHelper;
import joshie.mariculture.core.lib.CraftingMeta;
import joshie.mariculture.core.lib.EntityIds;
import joshie.mariculture.core.lib.Modules.RegistrationModule;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Transport extends RegistrationModule {
    public static Item speedBoat;

    @Override
    public void registerHandlers() {
        return;
    }

    @Override
    public void registerBlocks() {
        return;
    }

    @Override
    public void registerItems() {
        speedBoat = new ItemSpeedBoat().setUnlocalizedName("boat.speed");
    }

    @Override
    public void registerOther() {
        if (MaricultureTab.tabWorld != null) {
            MaricultureTab.tabWorld.setIcon(new ItemStack(speedBoat), true);
        }

        EntityRegistry.registerModEntity(EntitySpeedBoat.class, "speedBoat", EntityIds.SPEED_BOAT, Mariculture.instance, 80, 3, false);
    }

    @Override
    public void registerRecipes() {
        RecipeHelper.addShaped(new ItemStack(speedBoat), new Object[] { "G F", "AAA", 'G', Blocks.glass_pane, 'F', new ItemStack(Core.crafting, 1, CraftingMeta.COOLER), 'A', "ingotAluminum" });
    }
}
