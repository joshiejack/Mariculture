package mariculture.fishery.gui;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.gui.GuiStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.gui.feature.FeatureScanner;
import mariculture.core.util.Text;
import mariculture.fishery.Fish;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiScanner extends GuiStorage {
    public static FontRenderer font;
    private ItemStack drop;
    private ItemStack fish;

    public GuiScanner(IInventory playerInv, InventoryStorage storage, World world, String gui) {
        super(playerInv, storage, world, gui, 30);
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

    public void drawText(String str, int x, int z) {
        fontRendererObj.drawString(str, x, z, 4210752);
    }

    private String getBoolean(int i) {
        return i == 0 ? Text.localize("fish.data.false") : Text.localize("fish.data.true");
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
        String activeSpecies = active.isDominant() ? Text.ORANGE + active.getName() : Text.INDIGO + active.getName();
        String inactiveSpecies = inactive.isDominant() ? Text.ORANGE + inactive.getName() : Text.INDIGO + inactive.getName();
        String[] lifespan = Fish.lifespan.getScannedDisplay(fish);
        String[] fertility = Fish.fertility.getScannedDisplay(fish);
        String[] production = Fish.production.getScannedDisplay(fish);
        String[] foodUsage = Fish.foodUsage.getScannedDisplay(fish);
        String[] tankSize = Fish.tankSize.getScannedDisplay(fish);
        String[] up = Fish.up.getScannedDisplay(fish);
        String[] down = Fish.down.getScannedDisplay(fish);
        String[] north = Fish.north.getScannedDisplay(fish);
        String[] east = Fish.east.getScannedDisplay(fish);
        String[] south = Fish.south.getScannedDisplay(fish);
        String[] west = Fish.west.getScannedDisplay(fish);
        drawText(Text.localize("fish.data.species"), -75, 19);

        drawText(lifespan[0], -75, 29);
        drawText(fertility[0], -75, 39);
        drawText(production[0], -75, 49);
        drawText(foodUsage[0], -75, 59);
        drawText(tankSize[0], -75, 69);
        drawText(up[0], -75, 79);
        drawText(down[0], -75, 89);
        drawText(north[0], -75, 99);
        drawText(east[0], -75, 109);
        drawText(south[0], -75, 119);
        drawText(west[0], -75, 129);

        // Column 'Dominant'
        drawText(Text.BOLD + "Active", 20, 10);
        drawText(activeSpecies, 20, 20);
        drawText(lifespan[1], 20, 30);
        drawText(fertility[1], 20, 40);
        drawText(production[1], 20, 50);
        drawText(foodUsage[1], 20, 60);
        drawText(tankSize[1], 20, 70);
        drawText(up[1], 20, 80);
        drawText(down[1], 20, 90);
        drawText(north[1], 20, 100);
        drawText(east[1], 20, 110);
        drawText(south[1], 20, 120);
        drawText(west[1], 20, 130);

        // Column Recessive
        drawText(Text.BOLD + "Inactive", 140, 10);
        drawText(inactiveSpecies, 140, 20);
        drawText(lifespan[2], 140, 30);
        drawText(fertility[2], 140, 40);
        drawText(production[2], 140, 50);
        drawText(foodUsage[2], 140, 60);
        drawText(tankSize[2], 140, 70);
        drawText(up[2], 140, 80);
        drawText(down[2], 140, 90);
        drawText(north[2], 140, 100);
        drawText(east[2], 140, 110);
        drawText(south[2], 140, 120);
        drawText(west[2], 140, 130);
        GL11.glPopMatrix();
    }
}
