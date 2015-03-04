package mariculture.core.gui;

import mariculture.core.network.PacketHandler;
import mariculture.core.util.IMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class ContainerMachine extends ContainerMariculture {
    protected IMachine tile;

    public ContainerMachine(IMachine machine) {
        super((TileEntity) machine);
        tile = machine;
    }

    public int getSizeInventory() {
        return ((IInventory) tile).getSizeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ((IInventory) tile).isUseableByPlayer(player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); i++) {
            Object crafter = crafters.get(i);
            if (crafter instanceof EntityPlayerMP) {
                if (tile.hasChanged()) {
                    PacketHandler.sendGUIUpdate((EntityPlayerMP) crafter, windowId, tile.getGUIData());
                }
            }
        }
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        tile.setGUIData(par1, par2);
    }
}
