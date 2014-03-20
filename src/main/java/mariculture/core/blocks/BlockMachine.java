package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.WoodMeta;
import mariculture.core.network.PacketSponge;
import mariculture.factory.blocks.TileDictionaryItem;
import mariculture.factory.blocks.TileFishSorter;
import mariculture.factory.blocks.TileHFCU;
import mariculture.factory.blocks.TileSawmill;
import mariculture.factory.blocks.TileSluice;
import mariculture.factory.blocks.TileSponge;
import mariculture.fishery.blocks.TileAutofisher;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends BlockFunctional {
	private IIcon[] fishSorter;
	private IIcon sluiceBack;
	private IIcon sluiceUp;
	private IIcon sluiceDown;
	
	public BlockMachine() {
		super(Material.piston);
	}
	
	@Override
	public String getToolType(int meta) {
		switch (meta) {
			case MachineMeta.SLUICE: return "pickaxe";
			case MachineMeta.SPONGE: return "pickaxe";
			case MachineMeta.HFCU: 	 return "pickaxe";
			default:				 return "axe";
		}
	}

	@Override
	public int getToolLevel(int meta) {
		switch (meta) {
			case MachineMeta.SLUICE: return 1;
			case MachineMeta.SPONGE: return 1;
			case MachineMeta.HFCU:	 return 3;
			default:				 return 0;
		}
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
			case MachineMeta.BOOKSHELF: 		return 1F;
			case MachineMeta.DICTIONARY_ITEM: 	return 2F;
			case MachineMeta.SAWMILL:			return 2F;
			case MachineMeta.SLUICE: 			return 5F;
			case MachineMeta.SPONGE: 			return 2.5F;
			case MachineMeta.AUTOFISHER: 		return 2F;
			case MachineMeta.FISH_SORTER: 		return 1.5F;
			case MachineMeta.HFCU:				return 40F;
			default:							return 1F;
		}
	}
	
	@Override
	public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == MachineMeta.BOOKSHELF ? 5 : 0;
    }
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == MachineMeta.FISH_SORTER) return fishSorter[side];
		if(side < 2) {
			if(meta == MachineMeta.BOOKSHELF) return Blocks.planks.getIcon(side, meta);
			if(meta == MachineMeta.SLUICE) 	return Core.metals.getIcon(side, MetalMeta.BASE_IRON);
			if(meta == MachineMeta.SPONGE)	return Core.metals.getIcon(side, MetalMeta.BASE_IRON);
			if(meta == MachineMeta.HFCU)	return Core.metals.getIcon(side, MetalMeta.BASE_IRON);
			return Core.woods.getIcon(side, WoodMeta.BASE_WOOD);
		}

		return super.getIcon(side, meta);
	}
	
	@Override
	public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
		TileEntity tile = block.getTileEntity(x, y, z);
		if(tile instanceof TileBookshelf) return Blocks.bookshelf.getIcon(side, 0);
		else if(tile instanceof TileSluice) {
			TileSluice sluice = (TileSluice) tile;
			if(sluice.direction.ordinal() == side) return side > 1? icons[MachineMeta.SLUICE]: sluiceUp;
			else if(sluice.direction.getOpposite().ordinal() == side) return side > 1? sluiceBack: sluiceDown;
			else return Core.metals.getIcon(side, MetalMeta.BASE_IRON);
		} else return super.getIcon(block, x, y, z, side);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || player.isSneaking()) {
			return false;
		}
		
		if(tile instanceof TileHFCU) {
			return FluidHelper.handleFillOrDrain((TileHFCU)tile, player, ForgeDirection.UP);
		}
		
		if (tile instanceof TileSponge) {
			if(world.isRemote && player instanceof EntityClientPlayerMP) {
				Mariculture.packets.sendToServer(new PacketSponge(x, y, z, true));
			} else if(player.getCurrentEquippedItem() != null && !world.isRemote) {
				Item currentItem = player.getCurrentEquippedItem().getItem();
				if (currentItem instanceof IEnergyContainerItem && !world.isRemote) {
					int powerAdd = ((IEnergyContainerItem)currentItem).extractEnergy(player.getCurrentEquippedItem(), 5000, true);
					int reduce = ((IEnergyHandler)tile).receiveEnergy(ForgeDirection.UNKNOWN, powerAdd, false);
					((IEnergyContainerItem)currentItem).extractEnergy(player.getCurrentEquippedItem(), reduce, false);
				} 
			}
			
			return true;
		}

		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	//Clears water blocks around the sluice
	private void clearWater(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial() == Material.water) {
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		
		if (meta == MachineMeta.SLUICE) {
			clearWater(world, x + 1, y, z);
			clearWater(world, x - 1, y, z);
			clearWater(world, x, y, z + 1);
			clearWater(world, x, y, z - 1);
		}
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch (meta) {
			case MachineMeta.BOOKSHELF: 		return new TileBookshelf();
			case MachineMeta.DICTIONARY_ITEM: 	return new TileDictionaryItem();
			case MachineMeta.SAWMILL:			return new TileSawmill();
			case MachineMeta.SLUICE: 			return new TileSluice();
			case MachineMeta.SPONGE: 			return new TileSponge();
			case MachineMeta.AUTOFISHER: 		return new TileAutofisher();
			case MachineMeta.FISH_SORTER: 		return new TileFishSorter();
			case MachineMeta.HFCU:				return new TileHFCU();
			default:							return null;
		}
	}
	
	@Override
	public boolean isActive(int meta) {
		switch (meta) {
			case MachineMeta.BOOKSHELF: 		return true;
			case MachineMeta.DICTIONARY_ITEM: 	return Modules.isActive(Modules.factory);
			case MachineMeta.SAWMILL:			return Modules.isActive(Modules.factory);
			case MachineMeta.SLUICE: 			return Modules.isActive(Modules.factory);
			case MachineMeta.SPONGE: 			return Modules.isActive(Modules.factory);
			case MachineMeta.AUTOFISHER: 		return Modules.isActive(Modules.fishery);
			case MachineMeta.FISH_SORTER: 		return Modules.isActive(Modules.fishery);
			case MachineMeta.HFCU:				return Modules.isActive(Modules.factory);
			default:							return true;
		}
	}

	@Override
	public int getMetaCount() {
		return MachineMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {	
		super.registerBlockIcons(iconRegister);
		
		//Register other icons
		fishSorter = new IIcon[6];
		for(int i = 0; i < 6; i++) {
			fishSorter[i] = iconRegister.registerIcon(Mariculture.modid + ":fishsorter" + (i + 1));
		}
		
		sluiceBack = iconRegister.registerIcon(Mariculture.modid + ":sluiceBack");
		sluiceUp = iconRegister.registerIcon(Mariculture.modid + ":sluiceUp");
		sluiceDown = iconRegister.registerIcon(Mariculture.modid + ":sluiceDown");
	}
}
