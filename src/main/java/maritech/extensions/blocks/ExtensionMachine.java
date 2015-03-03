package maritech.extensions.blocks;

import mariculture.api.core.MaricultureTab;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketHandler;
import maritech.network.PacketSponge;
import maritech.tile.TileAutofisher;
import maritech.tile.TileExtractor;
import maritech.tile.TileGenerator;
import maritech.tile.TileInjector;
import maritech.tile.TileSluice;
import maritech.tile.TileSluiceAdvanced;
import maritech.tile.TileSponge;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;

public class ExtensionMachine extends ExtensionBlocksBase {
    @Override
    public String getName(int meta, String name) {
        switch (meta) {
            case MachineMeta.SLUICE:
                return "sluice";
            case MachineMeta.SPONGE:
                return "sponge";
            case MachineMeta.AUTOFISHER:
                return "autofisher";
            case MachineMeta.SLUICE_ADVANCED:
                return "sluiceAdvanced";
            case MachineMeta.GENERATOR:
                return "generator";
            case MachineMeta.EXTRACTOR:
                return "extractor";
            case MachineMeta.INJECTOR:
                return "injector";
        }

        return name;
    }
    
    @Override
    public String getMod(int meta, String mod) {
        switch (meta) {
            case MachineMeta.SLUICE:
            case MachineMeta.SPONGE:
            case MachineMeta.AUTOFISHER:
            case MachineMeta.SLUICE_ADVANCED:
            case MachineMeta.GENERATOR:
            case MachineMeta.EXTRACTOR:
            case MachineMeta.INJECTOR:
                return "maritech";
        }

        return mod;
    }

    @Override
    public void onTilePlaced(ItemStack stack, TileEntity tile, EntityLivingBase entity, int direction) {
        if (tile instanceof TileGenerator) {
            ((TileGenerator) tile).reset();
        }
    }

    private void clearWater(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial() == Material.water) {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean onBlockBroken(int meta, World world, int x, int y, int z) {
        if (meta == MachineMeta.SLUICE) {
            clearWater(world, x + 1, y, z);
            clearWater(world, x - 1, y, z);
            clearWater(world, x, y, z + 1);
            clearWater(world, x, y, z - 1);
        }

        return false;
    }

    @Override
    public boolean isActive(int meta, boolean isActive) {
        switch (meta) {
            case MachineMeta.AUTOFISHER:
            case MachineMeta.EXTRACTOR:
            case MachineMeta.INJECTOR:
                return Modules.isActive(Modules.fishery);
            case MachineMeta.SLUICE:
            case MachineMeta.SPONGE:
            case MachineMeta.SLUICE_ADVANCED:
            case MachineMeta.GENERATOR:
                return Modules.isActive(Modules.factory);
        }

        return isActive;
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta, boolean isValid) {
        return meta == MachineMeta.UNPACKER ? tab == MaricultureTab.tabFishery : isValid;
    }

    @Override
    public String getToolType(int meta, String tooltype) {
        switch (meta) {
            case MachineMeta.SLUICE:
            case MachineMeta.SPONGE:
            case MachineMeta.SLUICE_ADVANCED:
            case MachineMeta.GENERATOR:
            case MachineMeta.EXTRACTOR:
            case MachineMeta.INJECTOR:
                return "pickaxe";
        }

        return tooltype;
    }

    @Override
    public int getToolLevel(int meta, int level) {
        switch (meta) {
            case MachineMeta.SLUICE_ADVANCED:
            case MachineMeta.EXTRACTOR:
            case MachineMeta.INJECTOR:
                return 2;
            case MachineMeta.SLUICE:
            case MachineMeta.SPONGE:
            case MachineMeta.GENERATOR:
                return 1;
        }

        return level;
    }

    @Override
    public float getHardness(int meta, float hardness) {
        switch (meta) {
            case MachineMeta.EXTRACTOR:
            case MachineMeta.INJECTOR:
            case MachineMeta.SLUICE:
                return 5F;
            case MachineMeta.GENERATOR:
            case MachineMeta.SPONGE:
                return 2.5F;
            case MachineMeta.AUTOFISHER:
                return 2F;
            case MachineMeta.SLUICE_ADVANCED:
                return 7.5F;
        }

        return hardness;
    }

    @Override
    public TileEntity getTileEntity(int meta, TileEntity tile) {
        switch (meta) {
            case MachineMeta.SLUICE:
                return new TileSluice();
            case MachineMeta.SPONGE:
                return new TileSponge();
            case MachineMeta.AUTOFISHER:
                return new TileAutofisher();
            case MachineMeta.SLUICE_ADVANCED:
                return new TileSluiceAdvanced();
            case MachineMeta.GENERATOR:
                return new TileGenerator();
            case MachineMeta.EXTRACTOR:
                return new TileExtractor();
            case MachineMeta.INJECTOR:
                return new TileInjector();
        }

        return tile;
    }

    @Override
    public boolean onRightClickBlock(World world, int x, int y, int z, EntityPlayer player) {
        if (!player.isSneaking()) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileSluice || tile instanceof TileGenerator) return false;
            else if (tile instanceof TileSponge) {
                if (world.isRemote && player instanceof EntityClientPlayerMP) {
                    PacketHandler.sendToServer(new PacketSponge(x, y, z, true));
                } else if (player.getCurrentEquippedItem() != null && !world.isRemote) {
                    Item currentItem = player.getCurrentEquippedItem().getItem();
                    if (currentItem instanceof IEnergyContainerItem && !world.isRemote) {
                        int powerAdd = ((IEnergyContainerItem) currentItem).extractEnergy(player.getCurrentEquippedItem(), 5000, true);
                        int reduce = ((IEnergyReceiver) tile).receiveEnergy(ForgeDirection.UNKNOWN, powerAdd, false);
                        ((IEnergyContainerItem) currentItem).extractEnergy(player.getCurrentEquippedItem(), reduce, false);
                    }
                }

                return true;
            }
        }

        return false;
    }
}
