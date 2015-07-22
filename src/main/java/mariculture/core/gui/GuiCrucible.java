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
import mariculture.core.util.MCTranslate;
import mariculture.lib.util.Text;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GuiCrucible extends GuiMariculture {
    private final TileCrucible tile;

    public GuiCrucible(InventoryPlayer player, TileCrucible tile) {
        super(new ContainerCrucible(tile, player), "liquifier", 10);
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
        boolean ethereal = MaricultureHandlers.upgrades.hasUpgrade("ethereal", tile);
        if (stack != null) {
            int meltingPoint = MaricultureHandlers.crucible.getMeltingPoint(stack, ethereal);
            FuelInfo info = MaricultureHandlers.crucible.getFuelInfo(stack);
            if (FluidContainerRegistry.isFilledContainer(stack)) {
                FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
                info = MaricultureHandlers.crucible.getFuelInfo(fluid);
            }

            if (meltingPoint > 0) {
                currenttip.add(Text.ORANGE + MCTranslate.translate("melting") + ": " + meltingPoint + "\u00B0" + "C");
            }

            if (MaricultureHandlers.crucible.getResult(stack, null, -1, ethereal) != null) {
                RecipeSmelter result = MaricultureHandlers.crucible.getResult(stack, null, -1, ethereal);
                if (result.fluid.amount > 0) {
                    if (result.rands != null) {
                        currenttip.add(Text.INDIGO + MCTranslate.translate("randomMetal"));
                    } else {
                        currenttip.add(Text.INDIGO + FluidHelper.getName(result.fluid) + ": " + result.fluid.amount + "mB");
                    }
                }

                if (result.output != null && result.chance > 0) {
                    int chance = (int) ((float) 1 / result.chance * 100);
                    currenttip.add(Text.GREY + chance + MCTranslate.translate("percent") + result.output.getDisplayName());
                }

            }

            if (info != null) {
                if (FluidContainerRegistry.isFilledContainer(stack)) {
                    currenttip.add(Text.DARK_AQUA + MCTranslate.translate("asFluid"));
                    currenttip.add(Text.WHITE + MCTranslate.translate("perTempFluid") + ": " + info.maxTempPer + "\u00B0" + "C");
                } else {
                    currenttip.add(Text.DARK_GREEN + MCTranslate.translate("asSolid"));
                    currenttip.add(Text.WHITE + MCTranslate.translate("perTempSolid") + ": " + info.maxTempPer + "\u00B0" + "C");
                }

                currenttip.add(Text.GREY + MCTranslate.translate("maxTemp") + ": " + info.maxTemp + "\u00B0" + "C");
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
