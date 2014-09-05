package joshie.mariculture.plugins.nei;

import java.util.HashMap;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.api.core.RecipeCasting;
import joshie.mariculture.core.gui.feature.FeatureTank.TankSize;
import net.minecraft.util.ResourceLocation;

public class NEIBlockCasterRecipeHandler extends NEICastingRecipeHandler {
    @Override
    public String getOverlayIdentifier() {
        return "blockCaster";
    }

    @Override
    public Class thisClass() {
        return NEIBlockCasterRecipeHandler.class;
    }

    @Override
    public HashMap<String, RecipeCasting> getRecipes() {
        return MaricultureHandlers.casting.getBlockRecipes();
    }

    @Override
    public String getRecipeName() {
        return "Block Caster";
    }

    @Override
    public String getGuiTexture() {
        return new ResourceLocation(Mariculture.modid, "textures/gui/nei/blockCaster.png").toString();
    }

    @Override
    public TankSize getTankType() {
        return TankSize.BLOCK_CASTER;
    }
}
