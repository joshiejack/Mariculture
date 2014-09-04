package joshie.maritech.extensions.modules;

import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.lib.ItemLib.goldPlastic;
import static joshie.mariculture.core.lib.ItemLib.life;
import static joshie.mariculture.core.lib.ItemLib.plasticLens;
import static joshie.mariculture.core.lib.ItemLib.tank;
import static joshie.mariculture.core.lib.ItemLib.transparent;
import joshie.mariculture.core.lib.RenderIds;
import joshie.maritech.items.ItemFLUDD;
import joshie.maritech.util.IModuleExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ExtensionFactory implements IModuleExtension {
    private static ArmorMaterial armorFLUDD = EnumHelper.addArmorMaterial("FLUDD", 0, new int[] { 0, 0, 0, 0 }, 0);

    public static Item fludd;

    @Override
    public void preInit() {
        fludd = new ItemFLUDD(armorFLUDD, RenderIds.FLUDD, 1).setUnlocalizedName("fludd");
    }

    @Override
    public void init() {
        addShaped(((ItemFLUDD) fludd).build(0), new Object[] { " E ", "PGP", "LUL", 'E', plasticLens, 'P', goldPlastic, 'G', transparent, 'L', tank, 'U', life });
    }

    @Override
    public void postInit() {
        return;
    }
}
