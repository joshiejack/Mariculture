package joshie.maritech.handlers;

import joshie.mariculture.core.events.GetGuiEvent;
import joshie.maritech.gui.ContainerAutofisher;
import joshie.maritech.gui.ContainerExtractor;
import joshie.maritech.gui.ContainerFLUDDStand;
import joshie.maritech.gui.ContainerIncubator;
import joshie.maritech.gui.ContainerInjector;
import joshie.maritech.gui.ContainerPressureVessel;
import joshie.maritech.gui.GuiAutofisher;
import joshie.maritech.gui.GuiExtractor;
import joshie.maritech.gui.GuiFLUDDStand;
import joshie.maritech.gui.GuiIncubator;
import joshie.maritech.gui.GuiInjector;
import joshie.maritech.gui.GuiPressureVessel;
import joshie.maritech.tile.TileAutofisher;
import joshie.maritech.tile.TileExtractor;
import joshie.maritech.tile.TileFLUDDStand;
import joshie.maritech.tile.TileIncubator;
import joshie.maritech.tile.TileInjector;
import joshie.maritech.tile.TilePressureVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

public class GuiEvents {
    @SubscribeEvent
    public void getServerGuiElement(GetGuiEvent event) {
        EntityPlayer player = event.player;
        TileEntity tile = event.tile;
        if (tile != null) {
            if (event.side == Side.SERVER) {
                if (tile instanceof TileAutofisher) event.ret = new ContainerAutofisher((TileAutofisher) tile, player.inventory);
                else if (tile instanceof TileIncubator) event.ret = new ContainerIncubator((TileIncubator) tile, player.inventory);
                else if (tile instanceof TileFLUDDStand) event.ret = new ContainerFLUDDStand((TileFLUDDStand) tile, player.inventory);
                else if (tile instanceof TilePressureVessel) event.ret = new ContainerPressureVessel((TilePressureVessel) tile, player.inventory);
                else if (tile instanceof TileExtractor) event.ret = new ContainerExtractor((TileExtractor) tile, player.inventory);
                else if (tile instanceof TileInjector) event.ret = new ContainerInjector((TileInjector) tile, player.inventory);
            } else if (event.side == Side.CLIENT) {
                if (tile instanceof TileAutofisher) event.ret = new GuiAutofisher(player.inventory, (TileAutofisher) tile);
                else if (tile instanceof TileIncubator) event.ret = new GuiIncubator(player.inventory, (TileIncubator) tile);
                else if (tile instanceof TileFLUDDStand) event.ret = new GuiFLUDDStand(player.inventory, (TileFLUDDStand) tile);
                else if (tile instanceof TilePressureVessel) event.ret = new GuiPressureVessel(player.inventory, (TilePressureVessel) tile);
                else if (tile instanceof TileExtractor) event.ret = new GuiExtractor(player.inventory, (TileExtractor) tile);
                else if (tile instanceof TileInjector) event.ret = new GuiInjector(player.inventory, (TileInjector) tile);
            }
        }
    }
}
