package joshie.mariculture.fishery.gui;

import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.gui.GuiStorage;
import joshie.mariculture.core.gui.InventoryStorage;
import joshie.mariculture.core.gui.feature.FeatureScanner;
import joshie.mariculture.fishery.Fish;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiScanner extends GuiStorage {
    private ItemStack drop;
    private ItemStack fish;

    public GuiScanner(IInventory playerInv, InventoryStorage storage, World world, String gui) {
        super(playerInv, storage, world, gui, 44);
        features.add(new FeatureScanner());
        nameHeight = -9;
    }

    @Override
    public int getX() {
        return 90;
    }

    @Override
    public String getName() {
        return "";
    }

    private void drawText(String str, int x, int z) {
        fontRendererObj.drawString(str, x, z, 4210752);
    }

    private String getBoolean(int i) {
        return i == 0 ? joshie.lib.util.Text.localize("fish.data.false") : joshie.lib.util.Text.localize("fish.data.true");
    }

    @Override
    public void drawForeground() {
        fish = storage.getStackInSlot(0);
        if (fish == null || !fish.hasTagCompound() || Fishing.fishHelper.isEgg(fish)) return;

        GL11.glPushMatrix();
        GL11.glScalef(0.675F, 0.675F, 0.675F);
        GL11.glTranslatef(-5F, 5F, 0F);
        FishSpecies active = FishSpecies.species.get(Fish.species.getDNA(fish));
        FishSpecies inactive = FishSpecies.species.get(Fish.species.getLowerDNA(fish));
        String activeSpecies = active.isDominant() ? joshie.lib.util.Text.ORANGE + active.getName() : joshie.lib.util.Text.INDIGO + active.getName();
        String inactiveSpecies = inactive.isDominant() ? joshie.lib.util.Text.ORANGE + inactive.getName() : joshie.lib.util.Text.INDIGO + inactive.getName();
        String[] lifespan = Fish.lifespan.getScannedDisplay(fish);
        String[] fertility = Fish.fertility.getScannedDisplay(fish);
        String[] production = Fish.production.getScannedDisplay(fish);
        String[] foodUsage = Fish.foodUsage.getScannedDisplay(fish);
        String[] tankSize = Fish.tankSize.getScannedDisplay(fish);
        String[] salinity = Fish.salinity.getScannedDisplay(fish);
        String[] temperature = Fish.temperature.getScannedDisplay(fish);
        String[] up = Fish.up.getScannedDisplay(fish);
        String[] down = Fish.down.getScannedDisplay(fish);
        String[] north = Fish.north.getScannedDisplay(fish);
        String[] east = Fish.east.getScannedDisplay(fish);
        String[] south = Fish.south.getScannedDisplay(fish);
        String[] west = Fish.west.getScannedDisplay(fish);
        drawText(joshie.lib.util.Text.localize("fish.data.species"), -75, 19);

        drawText(lifespan[0], -75, 29);
        drawText(fertility[0], -75, 39);
        drawText(production[0], -75, 49);
        drawText(foodUsage[0], -75, 59);
        drawText(tankSize[0], -75, 69);
        drawText(salinity[0], -75, 79);
        drawText(temperature[0], -75, 89);
        drawText(up[0], -75, 99);
        drawText(down[0], -75, 109);
        drawText(north[0], -75, 119);
        drawText(east[0], -75, 129);
        drawText(south[0], -75, 139);
        drawText(west[0], -75, 149);

        // Column 'Dominant'
        drawText(joshie.lib.util.Text.BOLD + "Active", 20, 10);
        drawText(activeSpecies, 20, 20);
        drawText(lifespan[1], 20, 30);
        drawText(fertility[1], 20, 40);
        drawText(production[1], 20, 50);
        drawText(foodUsage[1], 20, 60);
        drawText(tankSize[1], 20, 70);
        drawText(salinity[1], 20, 80);
        drawText(temperature[1], 20, 90);
        drawText(up[1], 20, 100);
        drawText(down[1], 20, 110);
        drawText(north[1], 20, 120);
        drawText(east[1], 20, 130);
        drawText(south[1], 20, 140);
        drawText(west[1], 20, 150);

        // Column Recessive
        drawText(joshie.lib.util.Text.BOLD + "Inactive", 140, 10);
        drawText(inactiveSpecies, 140, 20);
        drawText(lifespan[2], 140, 30);
        drawText(fertility[2], 140, 40);
        drawText(production[2], 140, 50);
        drawText(foodUsage[2], 140, 60);
        drawText(tankSize[2], 140, 70);
        drawText(salinity[2], 140, 80);
        drawText(temperature[2], 140, 90);
        drawText(up[2], 140, 100);
        drawText(down[2], 140, 110);
        drawText(north[2], 140, 120);
        drawText(east[2], 140, 130);
        drawText(south[2], 140, 140);
        drawText(west[2], 140, 150);
        GL11.glPopMatrix();
    }
}
