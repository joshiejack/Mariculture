package joshie.maritech;

import joshie.maritech.gui.ContainerAutofisher;
import joshie.maritech.gui.ContainerFLUDDStand;
import joshie.maritech.gui.ContainerIncubator;
import joshie.maritech.gui.ContainerPressureVessel;
import joshie.maritech.gui.GuiAutofisher;
import joshie.maritech.gui.GuiFLUDDStand;
import joshie.maritech.gui.GuiIncubator;
import joshie.maritech.gui.GuiPressureVessel;
import joshie.maritech.tile.TileAutofisher;
import joshie.maritech.tile.TileFLUDDStand;
import joshie.maritech.tile.TileIncubator;
import joshie.maritech.tile.TilePressureVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MTCommonProxy implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileAutofisher) return new ContainerAutofisher((TileAutofisher) tile, player.inventory);
            else if (tile instanceof TileIncubator) return new ContainerIncubator((TileIncubator) tile, player.inventory);
            else if (tile instanceof TileFLUDDStand) return new ContainerFLUDDStand((TileFLUDDStand) tile, player.inventory);
            else if (tile instanceof TilePressureVessel) return new ContainerPressureVessel((TilePressureVessel) tile, player.inventory);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileAutofisher) return new GuiAutofisher(player.inventory, (TileAutofisher) tile);
            else if (tile instanceof TileIncubator) return new GuiIncubator(player.inventory, (TileIncubator) tile);
            else if (tile instanceof TileFLUDDStand) return new GuiFLUDDStand(player.inventory, (TileFLUDDStand) tile);
            else if (tile instanceof TilePressureVessel) return new GuiPressureVessel(player.inventory, (TilePressureVessel) tile);
        }

        return null;
    }

    public void setupClient() {}
}