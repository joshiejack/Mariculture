package mariculture.core.tile;

import mariculture.Mariculture;
import mariculture.core.config.Machines.Client;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketParticle;
import mariculture.core.network.PacketParticle.Particle;
import mariculture.core.tile.base.TileStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAutohammer extends TileStorage {
    private int offset = -1;
    public boolean[] up;
    public float[] angle;

    public TileAutohammer() {
        inventory = new ItemStack[4];
        up = new boolean[4];
        angle = new float[4];
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    public boolean onTick(int i) {
        return (worldObj.getWorldTime() + offset) % i == 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (Client.HAMMER_ANIM && worldObj.isRemote) {
            if (offset < 0) {
                for(int i = 0; i < angle.length; i++) {
                    angle[i] = 5F + worldObj.rand.nextInt(4);
                }
                
                offset = 1;
            }
            
            for (int i = 0; i < angle.length; i++) {
                if (up[i]) {
                    if (angle[i] <= 5F) {
                        up[i] = false;
                    } else angle[i] -= 2F;
                } else {
                    if (angle[i] >= 56F) {
                        up[i] = true;
                    } else angle[i] += 2F;
                }
            }
        }

        if (!worldObj.isRemote) {
            if (offset < 0) {
                offset = worldObj.rand.nextInt(80);
            }

            if (onTick(50) && canConsume()) {
                for (int i = 0; i < inventory.length; i++) {
                    if (inventory[i] != null) {
                        ForgeDirection dir = ForgeDirection.values()[i + 2];
                        TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord - 1, zCoord + dir.offsetZ);
                        if (tile instanceof TileAnvil) {
                            TileAnvil anvil = (TileAnvil) tile;
                            FakePlayer player = PlayerHelper.getFakePlayer(worldObj);
                            player.addExperience(getExperience());
                            int ret = ((TileAnvil) tile).workItem(player, inventory[i]);
                            if (ret >= 5000) {
                                setExperience(player, ret - 5000);
                                //Perform huge explosion effect
                                worldObj.playSoundEffect(anvil.xCoord, anvil.yCoord, anvil.zCoord, Mariculture.modid + ":bang", 1.0F, 1.0F);
                                PacketHandler.sendAround(new PacketParticle(Particle.EXPLODE_LRG, 1, anvil.xCoord, anvil.yCoord + 0.5, anvil.zCoord), worldObj.provider.dimensionId, anvil.xCoord, tile.yCoord + 1, anvil.zCoord);
                            } else if (ret < 0) {
                                //Perform small explosion effect
                                worldObj.playSoundEffect(anvil.xCoord, anvil.yCoord, anvil.zCoord, Mariculture.modid + ":hammer", 1.0F, 1.0F);
                                PacketHandler.sendAround(new PacketParticle(Particle.EXPLODE_SML, 1, anvil.xCoord, anvil.yCoord + 0.5, anvil.zCoord), worldObj.provider.dimensionId, anvil.xCoord, tile.yCoord + 1, anvil.zCoord);
                            } else if (ret != 0) {
                                setExperience(player, ret);
                                //Perform small explosion effect
                                worldObj.playSoundEffect(anvil.xCoord, anvil.yCoord, anvil.zCoord, Mariculture.modid + ":hammer", 1.0F, 1.0F);
                                PacketHandler.sendAround(new PacketParticle(Particle.EXPLODE_SML, 21, anvil.xCoord, anvil.yCoord, anvil.zCoord), worldObj.provider.dimensionId, anvil.xCoord, tile.yCoord + 1, anvil.zCoord);
                            }

                            if (ret != 0) {
                                if (inventory[i].attemptDamageItem(1, worldObj.rand)) {
                                    inventory[i] = null;
                                }

                                markDirty();
                            }
                        }
                    }
                }
            }
        }
    }

    private void setExperience(EntityPlayer player, int xp) {

    }

    private int getExperience() {
        return 500;
    }

    private boolean canConsume() {
        return true;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (!worldObj.isRemote) {
            PacketHandler.syncInventory(this, inventory);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }
}
