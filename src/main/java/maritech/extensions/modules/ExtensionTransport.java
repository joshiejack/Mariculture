package maritech.extensions.modules;

import static mariculture.core.helpers.RecipeHelper.addShaped;
import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EntityIds;
import maritech.entity.EntitySpeedBoat;
import maritech.items.ItemSpeedBoat;
import maritech.util.IModuleExtension;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ExtensionTransport implements IModuleExtension {
    public static Item speedBoat;

    @Override
    public void preInit() {
        speedBoat = new ItemSpeedBoat().setUnlocalizedName("boat.speed");
        if (MaricultureTab.tabWorld != null) {
            MaricultureTab.tabWorld.setIcon(new ItemStack(speedBoat), true);
        }

        EntityRegistry.registerModEntity(EntitySpeedBoat.class, "speedBoat", EntityIds.SPEED_BOAT, Mariculture.instance, 80, 3, false);
    }

    @Override
    public void init() {
        addShaped(new ItemStack(speedBoat), new Object[] { "G F", "AAA", 'G', Blocks.glass_pane, 'F', new ItemStack(Core.crafting, 1, CraftingMeta.COOLER), 'A', "ingotAluminum" });
    }

    @Override
    public void postInit() {
        return;
    }
}
