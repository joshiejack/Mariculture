package mariculture.plugins.nei;

import java.util.HashMap;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import net.minecraft.util.ResourceLocation;

public class NEIIngotCasterRecipeHandler extends NEICastingRecipeHandler {
	@Override
	public String getOverlayIdentifier() {
		return "caster";
	}

	@Override
	public Class thisClass() {
		return NEIIngotCasterRecipeHandler.class;
	}

	@Override
	public HashMap<String, RecipeCasting> getRecipes() {
		return MaricultureHandlers.casting.getIngotRecipes();
	}

	@Override
	public String getRecipeName() {
		return "Ingot Caster";
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation(Mariculture.modid, "textures/gui/nei/caster.png").toString();
	}
	
	@Override
	public TankSize getTankType() {
		return TankSize.CASTER;
	}
}
