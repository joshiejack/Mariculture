package mariculture.plugins.nei;

import java.util.HashMap;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import net.minecraft.util.ResourceLocation;

public class NEINuggetCasterRecipeHandler extends NEICastingRecipeHandler {
    @Override
    public String getOverlayIdentifier() {
        return "nuggetCaster";
    }

    @Override
    public Class thisClass() {
        return NEINuggetCasterRecipeHandler.class;
    }

    @Override
    public HashMap<String, RecipeCasting> getRecipes() {
        return MaricultureHandlers.casting.getNuggetRecipes();
    }

    @Override
    public String getRecipeName() {
        return "Nugget Caster";
    }

    @Override
    public String getGuiTexture() {
        return new ResourceLocation(Mariculture.modid, "textures/gui/nei/nuggetCaster.png").toString();
    }

    @Override
    public TankSize getTankType() {
        return TankSize.NUGGET_CASTER;
    }
}
