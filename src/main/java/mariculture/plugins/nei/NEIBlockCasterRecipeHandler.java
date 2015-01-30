package mariculture.plugins.nei;

import java.util.HashMap;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.util.MCTranslate;
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
        return MCTranslate.translate("machines.single.rendered.blockcaster");
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
