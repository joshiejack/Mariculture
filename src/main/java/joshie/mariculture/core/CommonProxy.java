package joshie.mariculture.core;

import joshie.mariculture.core.gui.ContainerBookshelf;
import joshie.mariculture.core.gui.ContainerCrucible;
import joshie.mariculture.core.gui.GuiBookshelf;
import joshie.mariculture.core.gui.GuiCrucible;
import joshie.mariculture.core.items.ItemStorage;
import joshie.mariculture.core.tile.TileBookshelf;
import joshie.mariculture.core.tile.TileCrucible;
import joshie.mariculture.factory.gui.ContainerDictionary;
import joshie.mariculture.factory.gui.ContainerFLUDDStand;
import joshie.mariculture.factory.gui.ContainerFishSorter;
import joshie.mariculture.factory.gui.ContainerPressureVessel;
import joshie.mariculture.factory.gui.ContainerSawmill;
import joshie.mariculture.factory.gui.GuiDictionary;
import joshie.mariculture.factory.gui.GuiFLUDDStand;
import joshie.mariculture.factory.gui.GuiFishSorter;
import joshie.mariculture.factory.gui.GuiPressureVessel;
import joshie.mariculture.factory.gui.GuiSawmill;
import joshie.mariculture.factory.tile.TileDictionaryItem;
import joshie.mariculture.factory.tile.TileFLUDDStand;
import joshie.mariculture.factory.tile.TileFishSorter;
import joshie.mariculture.factory.tile.TilePressureVessel;
import joshie.mariculture.factory.tile.TileSawmill;
import joshie.mariculture.fishery.gui.ContainerAutofisher;
import joshie.mariculture.fishery.gui.ContainerFeeder;
import joshie.mariculture.fishery.gui.ContainerFishTank;
import joshie.mariculture.fishery.gui.ContainerIncubator;
import joshie.mariculture.fishery.gui.ContainerSift;
import joshie.mariculture.fishery.gui.GuiAutofisher;
import joshie.mariculture.fishery.gui.GuiFeeder;
import joshie.mariculture.fishery.gui.GuiFishTank;
import joshie.mariculture.fishery.gui.GuiIncubator;
import joshie.mariculture.fishery.gui.GuiSift;
import joshie.mariculture.fishery.tile.TileAutofisher;
import joshie.mariculture.fishery.tile.TileFeeder;
import joshie.mariculture.fishery.tile.TileFishTank;
import joshie.mariculture.fishery.tile.TileIncubator;
import joshie.mariculture.fishery.tile.TileSifter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == ItemStorage.GUI_ID && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemStorage) {
            ItemStorage storage = (ItemStorage) player.getCurrentEquippedItem().getItem();
            return storage.getGUIContainer(player);
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileAutofisher) return new ContainerAutofisher((TileAutofisher) tile, player.inventory);
            else if (tile instanceof TileCrucible) return new ContainerCrucible((TileCrucible) tile, player.inventory);
            else if (tile instanceof TileIncubator) return new ContainerIncubator((TileIncubator) tile, player.inventory);
            else if (tile instanceof TileSifter) return new ContainerSift((TileSifter) tile, player.inventory);
            else if (tile instanceof TileFeeder) return new ContainerFeeder((TileFeeder) tile, player.inventory);
            else if (tile instanceof TileBookshelf) return new ContainerBookshelf((TileBookshelf) tile, player.inventory);
            else if (tile instanceof TileSawmill) return new ContainerSawmill((TileSawmill) tile, player.inventory);
            else if (tile instanceof TileFLUDDStand) return new ContainerFLUDDStand((TileFLUDDStand) tile, player.inventory);
            else if (tile instanceof TilePressureVessel) return new ContainerPressureVessel((TilePressureVessel) tile, player.inventory);
            else if (tile instanceof TileDictionaryItem) return new ContainerDictionary((TileDictionaryItem) tile, player.inventory);
            else if (tile instanceof TileFishSorter) return new ContainerFishSorter((TileFishSorter) tile, player.inventory);
            else if (tile instanceof TileFishTank) return new ContainerFishTank((TileFishTank) tile, player.inventory);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == ItemStorage.GUI_ID && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemStorage) {
            ItemStorage storage = (ItemStorage) player.getCurrentEquippedItem().getItem();
            return storage.getGUIElement(player);
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileAutofisher) return new GuiAutofisher(player.inventory, (TileAutofisher) tile);
            else if (tile instanceof TileCrucible) return new GuiCrucible(player.inventory, (TileCrucible) tile);
            else if (tile instanceof TileIncubator) return new GuiIncubator(player.inventory, (TileIncubator) tile);
            else if (tile instanceof TileSifter) return new GuiSift(player.inventory, (TileSifter) tile);
            else if (tile instanceof TileFeeder) return new GuiFeeder(player.inventory, (TileFeeder) tile);
            else if (tile instanceof TileBookshelf) return new GuiBookshelf(player.inventory, (TileBookshelf) tile);
            else if (tile instanceof TileSawmill) return new GuiSawmill(player.inventory, (TileSawmill) tile);
            else if (tile instanceof TileFLUDDStand) return new GuiFLUDDStand(player.inventory, (TileFLUDDStand) tile);
            else if (tile instanceof TilePressureVessel) return new GuiPressureVessel(player.inventory, (TilePressureVessel) tile);
            else if (tile instanceof TileDictionaryItem) return new GuiDictionary(player.inventory, (TileDictionaryItem) tile);
            else if (tile instanceof TileFishSorter) return new GuiFishSorter(player.inventory, (TileFishSorter) tile);
            else if (tile instanceof TileFishTank) return new GuiFishTank(player.inventory, (TileFishTank) tile);
        }

        return null;
    }

    public void setupClient() {}
}