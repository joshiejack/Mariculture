package joshie.maritech.extensions.blocks;

import joshie.lib.helpers.DirectionHelper;
import joshie.lib.helpers.ItemHelper;
import joshie.mariculture.api.util.CachedCoords;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.core.lib.MetalMeta;
import joshie.mariculture.core.network.PacketHandler;
import joshie.mariculture.core.util.Fluids;
import joshie.maritech.extensions.modules.ExtensionFactory;
import joshie.maritech.items.ItemFLUDD;
import joshie.maritech.tile.TileFLUDDStand;
import joshie.maritech.tile.TileGenerator;
import joshie.maritech.tile.TileRotor;
import joshie.maritech.tile.TileRotorAluminum;
import joshie.maritech.tile.TileRotorCopper;
import joshie.maritech.tile.TileRotorTitanium;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExtensionRenderedMachine extends ExtensionBlocksBase {
    @Override
    public String getName(int meta, String name) {
        switch (meta) {
            case MachineRenderedMeta.FLUDD_STAND:
                return "fludd";
            case MachineRenderedMeta.ROTOR_COPPER:
                return "rotorCopper";
            case MachineRenderedMeta.ROTOR_ALUMINUM:
                return "rotorAluminum";
            case MachineRenderedMeta.ROTOR_TITANIUM:
                return "rotorTitanium";
        }

        return name;
    }

    @Override
    public String getMod(int meta, String name) {
        switch (meta) {
            case MachineRenderedMeta.FLUDD_STAND:
            case MachineRenderedMeta.ROTOR_COPPER:
            case MachineRenderedMeta.ROTOR_ALUMINUM:
            case MachineRenderedMeta.ROTOR_TITANIUM:
                return "maritech";
        }

        return name;
    }

    @Override
    public int getToolLevel(int meta, int level) {
        switch (meta) {
            case MachineRenderedMeta.ROTOR_COPPER:
            case MachineRenderedMeta.ROTOR_ALUMINUM:
                return 1;
            case MachineRenderedMeta.ROTOR_TITANIUM:
                return 2;
        }

        return level;
    }

    @Override
    public float getHardness(int meta, float hardness) {
        switch (meta) {
            case MachineRenderedMeta.FLUDD_STAND:
                return 1F;
            case MachineRenderedMeta.ROTOR_COPPER:
                return 5F;
            case MachineRenderedMeta.ROTOR_ALUMINUM:
                return 6.5F;
            case MachineRenderedMeta.ROTOR_TITANIUM:
                return 15F;
        }

        return hardness;
    }

    @Override
    public TileEntity getTileEntity(int meta, TileEntity tile) {
        switch (meta) {
            case MachineRenderedMeta.FLUDD_STAND:
                return new TileFLUDDStand();
            case MachineRenderedMeta.ROTOR_COPPER:
                return new TileRotorCopper();
            case MachineRenderedMeta.ROTOR_ALUMINUM:
                return new TileRotorAluminum();
            case MachineRenderedMeta.ROTOR_TITANIUM:
                return new TileRotorTitanium();
        }

        return tile;
    }

    @Override
    public void onTilePlaced(ItemStack stack, TileEntity tile, EntityLivingBase entity, int direction) {
        if (tile instanceof TileFLUDDStand) {
            TileFLUDDStand fludd = (TileFLUDDStand) tile;
            fludd.setFacing(ForgeDirection.getOrientation(direction));

            int water = 0;
            if (stack.hasTagCompound()) {
                water = stack.stackTagCompound.getInteger("water");
            }

            fludd.tank.setCapacity(ItemFLUDD.STORAGE);
            fludd.tank.setFluidID(Fluids.getFluidID("hp_water"));
            fludd.tank.setFluidAmount(water);
            PacketHandler.updateRender(fludd);
        }

        if (tile instanceof TileRotor) {
            ((TileRotor) tile).setFacing(DirectionHelper.getFacingFromEntity(entity));
        }
    }

    @Override
    public boolean onBlockBroken(int meta, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileFLUDDStand) {
            TileFLUDDStand stand = (TileFLUDDStand) tile;
            ItemStack fludd = new ItemStack(ExtensionFactory.fludd);
            fludd.setTagCompound(new NBTTagCompound());
            fludd.stackTagCompound.setInteger("water", stand.tank.getFluidAmount());
            ItemHelper.spawnItem(world, x, y, z, fludd);

            return world.setBlockToAir(x, y, z);
        } else if (tile instanceof TileRotor) {
            CachedCoords cord = ((TileRotor) tile).master;
            if (cord != null) {
                world.setBlock(x, y, z, Core.metals, MetalMeta.BASE_IRON, 2);
                TileEntity gen = world.getTileEntity(cord.x, cord.y, cord.z);
                if (gen instanceof TileGenerator) {
                    ((TileGenerator) tile).reset();
                }

                ItemHelper.spawnItem(world, x, y + 1, z, ((TileRotor) tile).getDrop());
                return true;
            } else {
                world.setBlock(x, y, z, Core.metals, MetalMeta.BASE_IRON, 2);
                ItemHelper.spawnItem(world, x, y + 1, z, ((TileRotor) tile).getDrop());
                return true;
            }
        }

        return false;
    }
}
