package mariculture.core.gui;

import java.util.List;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.gui.feature.FeatureArrow;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Text;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GuiLiquifier extends GuiMariculture {
	private final TileLiquifier tile;

	public GuiLiquifier(InventoryPlayer player, TileLiquifier tile) {
		super(new ContainerLiquifier(tile, player), "liquifier", 10);
		this.tile = tile;
		features.add(new FeatureUpgrades());
		features.add(new FeatureTank(tile, 98, 19, TankSize.DOUBLE));
		features.add(new FeatureArrow(tile, 65, 41));
		features.add(new FeatureRedstone(tile));
		features.add(new FeatureEject(tile));
	}

	@Override
	public void addToolTip() {		
		super.addToolTip();
		if (mouseX >= 12 && mouseX <= 16 && mouseY >= 18 && mouseY <= 77) {
			tooltip.add(tile.getRealTemperature() + "\u00B0" + "C");
		}
	}

	@Override
	public void addItemToolTip(ItemStack stack, List<String> currenttip) {
		if (stack != null) {
			int meltingPoint = MaricultureHandlers.smelter.getMeltingPoint(stack);
			FuelInfo info = MaricultureHandlers.smelter.getFuelInfo(stack);

			if (meltingPoint > 0) {
				currenttip.add(Text.ORANGE + StatCollector.translateToLocal("mariculture.string.melting") + ": " + meltingPoint + "\u00B0" + "C");
			}
			
			if (MaricultureHandlers.smelter.getResult(stack, null, -1) != null) {
				RecipeSmelter result = MaricultureHandlers.smelter.getResult(stack, null, -1);
				if (result.fluid.amount > 0) {
					if(result.rands != null)
						currenttip.add(Text.INDIGO + StatCollector.translateToLocal("mariculture.string.randomMetal"));
					else
						currenttip.add(Text.INDIGO + FluidHelper.getName(result.fluid.getFluid()) + ": " + tile.getFluidAmount(stack, result.fluid.amount) + "mB");
				}
				
				if(result.output != null && result.chance > 0) {
					int chance = (int) (((float)1 / result.chance) * 100);
					currenttip.add(Text.GREY + chance + StatCollector.translateToLocal("mariculture.string.percent") + result.output.getDisplayName());
				}
			}

			if (info != null) {
				currenttip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.maxTemp") + ": " + info.maxTemp + "\u00B0" + "C");
			}
		}
	}
	
	@Override
	public void drawBackground(int x, int y) {
		int temp = tile.getTemperatureScaled(60);
		this.drawTexturedModalRect(x + 12, y + 18 + 60 - temp, 251, 60 - temp, 5, temp);
	}

	@Override
	public int getX() {
		return 16;
	}
}
