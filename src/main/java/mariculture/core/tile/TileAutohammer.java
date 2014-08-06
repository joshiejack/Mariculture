package mariculture.core.tile;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.tile.base.TileStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAutohammer extends TileStorage {
    public TileAutohammer() {
        inventory = new ItemStack[4];
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (onTick(10) && canConsume()) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] != null) {
                    ForgeDirection dir = ForgeDirection.values()[i + 2];
                    TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord - 1, zCoord + dir.offsetZ);
                    if (tile instanceof TileAnvil) {
                        TileAnvil anvil = (TileAnvil) tile;
                        FakePlayer player = PlayerHelper.getFakePlayer(worldObj);
                        player.addExperience(getExperience());
                        setExperience(((TileAnvil) tile).workItem(player, inventory[i]));
                    }
                }
            }
        }
    }

    private void setExperience(int xp) {
        
    }

    private int getExperience() {
        return 0;
    }

    private boolean canConsume() {
        return true;
    }

    private void consumeXP() {

    }
}
