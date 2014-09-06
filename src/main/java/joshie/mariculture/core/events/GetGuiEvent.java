package joshie.mariculture.core.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;

public class GetGuiEvent extends Event {
    public final Side side;
    public final EntityPlayer player;
    public final TileEntity tile;
    public Object ret;

    public GetGuiEvent(Side side, EntityPlayer player, TileEntity tile) {
        this.side = side;
        this.player = player;
        this.tile = tile;
    }
}
