package mariculture.factory.tile;

import mariculture.core.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileCustom extends TileEntity {
    private float hardness, resist;
    private Block[] theBlocks = new Block[6];
    private int[] theBlockMetas = new int[6];
    private int[] theSides = new int[6];
    private String name = "CustomTile";

    private int size() {
        return theBlocks.length;
    }

    public String name() {
        return name;
    }

    public Block theBlocks(int i) {
        return theBlocks[i] != null ? theBlocks[i] : Blocks.stone;
    }

    public int theBlockMetas(int i) {
        return theBlockMetas.length > i ? theBlockMetas[i] : 0;
    }

    public int theBlockSides(int i) {
        return theSides.length > i ? theSides[i] : 0;
    }

    public float getHardness() {
        return hardness;
    }

    public float getResistance() {
        return resist;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
    
    public void readData(NBTTagCompound nbt) {
        for (int i = 0; i < 6; i++) {
            Block block = (Block) Block.blockRegistry.getObject(nbt.getString("BlockIdentifier" + i));
            theBlocks[i] = block != null? block: Blocks.stone;
        }

        resist = nbt.getFloat("BlockResistance");
        hardness = nbt.getFloat("BlockHardness");
        theBlockMetas = nbt.getIntArray("BlockMetas");
        theSides = nbt.getIntArray("BlockSides");
        name = nbt.getString("Name");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readData(nbt);
    }

    public boolean isNameNull() {
        return name == null || name.equals("");
    }
    
    public void writeData(NBTTagCompound nbt) {
        for (int i = 0; i < 6; i++) {
            nbt.setString("BlockIdentifier" + i, Block.blockRegistry.getNameForObject(theBlocks[i]));
        }

        nbt.setFloat("BlockResistance", resist);
        nbt.setFloat("BlockHardness", hardness);
        nbt.setIntArray("BlockMetas", theBlockMetas);
        nbt.setIntArray("BlockSides", theSides);
        nbt.setString("Name", isNameNull()? "invalid": name);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeData(nbt);
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

    public void updateHardness() {
        hardness = 0F;
        for (int i = 0; i < 6; i++) {
            hardness += theBlocks(i).getBlockHardness(worldObj, xCoord, yCoord, zCoord);
        }

        hardness /= 6;
    }

    public void updateResistance() {
        resist = 0F;
        for (int i = 0; i < 6; i++) {
            resist += theBlocks(i).getExplosionResistance(null, worldObj, xCoord, yCoord, zCoord, 0, 0, 0);
        }

        resist /= 6;
    }

    public void set(Block[] blocks, int[] metas, int[] sides, String name) {
        theBlocks = blocks;
        theBlockMetas = metas;
        theSides = sides;
        this.name = name;
        updateHardness();
        updateResistance();
        updateRender();
    }

    public boolean setSide(int side, Block block, int meta, int sideTexture) {
        boolean ret = false;
        if (size() == 6) {
            if (theBlocks[side] != block || theBlockMetas[side] != meta || theSides[side] != sideTexture) {
                ret = true;
            }

            theBlocks[side] = block;
            theBlockMetas[side] = meta;
            theSides[side] = sideTexture;
            updateHardness();
            updateResistance();
            updateRender();
        }

        return ret;
    }

    public void updateRender() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        if (!worldObj.isRemote) {
            PacketHandler.updateRender(this);
        }
    }
}
