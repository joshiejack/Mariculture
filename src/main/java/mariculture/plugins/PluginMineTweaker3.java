package mariculture.plugins;

import mariculture.core.handlers.CrucibleHandler;
import mariculture.core.helpers.OreDicHelper;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.minetweaker.Anvil;
import mariculture.plugins.minetweaker.Casting;
import mariculture.plugins.minetweaker.Crucible;
import mariculture.plugins.minetweaker.Fishing;
import mariculture.plugins.minetweaker.Vat;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PluginMineTweaker3 extends Plugin {
    public PluginMineTweaker3(String name) {
        super(name);
    }

    public static String getKey(Object object) {
        if (object instanceof ItemStack) {
            return OreDicHelper.convert(object);
        } else if (object instanceof FluidStack) {
            return CrucibleHandler.getName((FluidStack) object);
        } else if (object instanceof String) {
            return (String) object;
        }
        
        return "null";
    }

    @Override
    public void preInit() {
        MineTweakerAPI.registerClass(Anvil.class);
        MineTweakerAPI.registerClass(Casting.class);
        MineTweakerAPI.registerClass(Crucible.class);
        MineTweakerAPI.registerClass(Fishing.class);
        MineTweakerAPI.registerClass(Vat.class);
    }

    @Override
    public void init() {}

    @Override
    public void postInit() {}
}
