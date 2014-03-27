package mariculture.core.gui;

import java.util.List;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.gui.feature.FeatureArrow;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.tile.TileCrucible;
import mariculture.core.util.Text;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GuiLiquifier extends GuiMariculture {
	private final TileCrucible tile;

	public GuiLiquifier(InventoryPlayer player, TileCrucible tile) {
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
		if (mouseX >= 12 && mouseX <= 16 && mouseY >= 18 && mouseY <= 77) {
			tooltip.add(tile.getRealTemperature() + "\u00B0" + "C");
		}
	}

	@Override
	public void addItemToolTip(ItemStack stack, List<String> currenttip) {
		if (stack != null) {
			int meltingPoint = MaricultureHandlers.smelter.getMeltingPoint(stack);
			FuelInfo info = MaricultureHandlers.smelter.getFuelInfo(stack);
			if(FluidContainerRegistry.isFilledContainer(stack)) {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
				info = MaricultureHandlers.smelter.getFuelInfo(fluid);
			}

			if (meltingPoint > 0) {
				currenttip.add(Text.ORANGE + StatCollector.translateToLocal("mariculture.string.melting") + ": " + meltingPoint + "\u00B0" + "C");
			}
			
			if (MaricultureHandlers.smelter.getResult(stack, -1) != null) {
				RecipeSmelter result = MaricultureHandlers.smelter.getResult(stack, -1);
				if(result.input2 == null) {
					if (result.fluid.amount > 0) {
						if(result.rands != null)
							currenttip.add(Text.INDIGO + StatCollector.translateToLocal("mariculture.string.randomMetal"));
						else
							currenttip.add(Text.INDIGO + FluidHelper.getName(result.fluid.getFluid()) + ": " + result.fluid.amount + "mB");
					}
					
					if(result.output != null && result.chance > 0) {
						int chance = (int) (((float)1 / result.chance) * 100);
						currenttip.add(Text.GREY + chance + StatCollector.translateToLocal("mariculture.string.percent") + result.output.getDisplayName());
					}
				}
			}

			if (info != null) {
				if(FluidContainerRegistry.isFilledContainer(stack)) {
					currenttip.add(Text.DARK_AQUA + StatCollector.translateToLocal("mariculture.string.asFluid"));
					currenttip.add(Text.WHITE + StatCollector.translateToLocal("mariculture.string.perTempFluid") + ": " + info.maxTempPer + "\u00B0" + "C");
				} else {
					currenttip.add(Text.DARK_GREEN + StatCollector.translateToLocal("mariculture.string.asSolid"));
					currenttip.add(Text.WHITE + StatCollector.translateToLocal("mariculture.string.perTempSolid") + ": " + info.maxTempPer + "\u00B0" + "C");
				}	
				
				currenttip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.maxTemp") + ": " + info.maxTemp + "\u00B0" + "C");
			}
		}
	}
	
	@Override
	public void drawBackground(int x, int y) {
		int temp = tile.getTemperatureScaled(60);
		drawTexturedModalRect(x + 12, y + 18 + 60 - temp, 251, 60 - temp, 5, temp);
		
		int burn = tile.getBurnTimeRemainingScaled();
		drawTexturedModalRect(x + 38, y + 43 + 12 - burn, 242, 60 + 12 - burn, 14, burn);
	}

	@Override
	public int getX() {
		return 16;
	}
}
