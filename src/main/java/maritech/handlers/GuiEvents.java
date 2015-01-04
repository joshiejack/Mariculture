package maritech.handlers;

import mariculture.core.events.GetGuiEvent;
import maritech.gui.ContainerAutofisher;
import maritech.gui.ContainerExtractor;
import maritech.gui.ContainerFLUDDStand;
import maritech.gui.ContainerIncubator;
import maritech.gui.ContainerInjector;
import maritech.gui.ContainerPressureVessel;
import maritech.gui.GuiAutofisher;
import maritech.gui.GuiExtractor;
import maritech.gui.GuiFLUDDStand;
import maritech.gui.GuiIncubator;
import maritech.gui.GuiInjector;
import maritech.gui.GuiPressureVessel;
import maritech.tile.TileAutofisher;
import maritech.tile.TileExtractor;
import maritech.tile.TileFLUDDStand;
import maritech.tile.TileIncubator;
import maritech.tile.TileInjector;
import maritech.tile.TilePressureVessel;
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
