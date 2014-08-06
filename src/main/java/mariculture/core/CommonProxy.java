package mariculture.core;

import mariculture.core.gui.ContainerBookshelf;
import mariculture.core.gui.ContainerCrucible;
import mariculture.core.gui.GuiBookshelf;
import mariculture.core.gui.GuiCrucible;
import mariculture.core.items.ItemStorage;
import mariculture.core.tile.TileBookshelf;
import mariculture.core.tile.TileCrucible;
import mariculture.factory.gui.ContainerDictionary;
import mariculture.factory.gui.ContainerFLUDDStand;
import mariculture.factory.gui.ContainerFishSorter;
import mariculture.factory.gui.ContainerPressureVessel;
import mariculture.factory.gui.ContainerSawmill;
import mariculture.factory.gui.GuiDictionary;
import mariculture.factory.gui.GuiFLUDDStand;
import mariculture.factory.gui.GuiFishSorter;
import mariculture.factory.gui.GuiPressureVessel;
import mariculture.factory.gui.GuiSawmill;
import mariculture.factory.tile.TileDictionaryItem;
import mariculture.factory.tile.TileFLUDDStand;
import mariculture.factory.tile.TileFishSorter;
import mariculture.factory.tile.TilePressureVessel;
import mariculture.factory.tile.TileSawmill;
import mariculture.fishery.gui.ContainerAutofisher;
import mariculture.fishery.gui.ContainerFeeder;
import mariculture.fishery.gui.ContainerFishTank;
import mariculture.fishery.gui.ContainerIncubator;
import mariculture.fishery.gui.ContainerSift;
import mariculture.fishery.gui.GuiAutofisher;
import mariculture.fishery.gui.GuiFeeder;
import mariculture.fishery.gui.GuiFishTank;
import mariculture.fishery.gui.GuiIncubator;
import mariculture.fishery.gui.GuiSift;
import mariculture.fishery.tile.TileAutofisher;
import mariculture.fishery.tile.TileFeeder;
import mariculture.fishery.tile.TileFishTank;
import mariculture.fishery.tile.TileIncubator;
import mariculture.fishery.tile.TileSifter;
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