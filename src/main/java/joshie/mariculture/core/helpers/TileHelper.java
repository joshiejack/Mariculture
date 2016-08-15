package joshie.mariculture.core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileHelper {
    public static IItemHandler getItemHandler(TileEntity tile) {
        return tile.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
    }

    public static ItemStack getStackInSlot(TileEntity tile, int slot) {
        return tile.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN).getStackInSlot(slot);
    }

    public static void sendRenderUpdate(TileEntity tile) {
        sendRenderUpdate(tile.getWorld(), tile.getPos(), tile);
    }

    public static void sendRenderUpdate(World world, BlockPos pos, TileEntity tile) {
        SPacketUpdateTileEntity packet = tile.getUpdatePacket();
        packet.getNbtCompound().setBoolean("Render", true);
        for(EntityPlayer player : world.playerEntities) {
            if (player instanceof EntityPlayerMP) {
                EntityPlayerMP mp = (EntityPlayerMP) player;
                if (Math.hypot(mp.posX - pos.getX() + 0.5, mp.posZ - pos.getZ() + 0.5) < 64) {
                    ((EntityPlayerMP) player).connection.sendPacket(packet);
                }
            }
        }
    }
}
