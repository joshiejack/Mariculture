package joshie.mariculture.plugins.nei;

import java.util.HashMap;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.api.core.RecipeCasting;
import joshie.mariculture.core.gui.feature.FeatureTank.TankSize;
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
