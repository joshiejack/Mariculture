package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.network.PacketSponge;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasGUI;
import mariculture.factory.blocks.TileDictionary;
import mariculture.factory.blocks.TileFishSorter;
import mariculture.factory.blocks.TileSawmill;
import mariculture.factory.blocks.TileSluice;
import mariculture.factory.blocks.TileSponge;
import mariculture.fishery.blocks.TileAutofisher;
import mariculture.fishery.blocks.TileIncubator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockUtil extends BlockMachineMulti {
	private IIcon[] incubatorIcons;
	private IIcon sluiceBack;
	private IIcon sluiceUp;
	private IIcon sluiceDown;
	private IIcon[] liquifierIcons;
	private IIcon[] fishSorter;

	public BlockUtil() {
		super(Material.piston);
	}
	
	@Override
	public String getToolType(int meta) {
		switch(meta) {
			case UtilMeta.AUTOFISHER:
				return "axe";
			case UtilMeta.BOOKSHELF:
				return "axe";
			case UtilMeta.FISH_SORTER:
				return "axe";
			case UtilMeta.SAWMILL:
				return "axe";
			default:
				return "pickaxe";
		}
	}

	@Override
	public int getToolLevel(int meta) {
		switch(meta) {
		case UtilMeta.AUTOFISHER:
			return 0;
		case UtilMeta.BOOKSHELF:
			return 0;
		case UtilMeta.FISH_SORTER:
			return 0;
		case UtilMeta.SAWMILL:
			return 0;
		default:
			return 1;
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case UtilMeta.AUTOFISHER:
			return 1.5F;
		case UtilMeta.BOOKSHELF:
			return 1F;
		case UtilMeta.DICTIONARY:
			return 2F;
		case UtilMeta.INCUBATOR_BASE:
			return 10F;
		case UtilMeta.INCUBATOR_TOP:
			return 6F;
		case UtilMeta.LIQUIFIER:
			return 5F;
		case UtilMeta.SAWMILL:
			return 2F;
		case UtilMeta.SLUICE:
			return 4F;
		case UtilMeta.SPONGE:
			return 3F;
		case UtilMeta.FISH_SORTER:
			return 1F;
		}

		return 3F;
	}
	
	public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == UtilMeta.BOOKSHELF ? 5 : 0;
    }
	
	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockBreak();
		}
		
		super.onBlockExploded(world, x, y, z, explosion);
    }

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return true;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < getMetaCount()) {
			switch (meta) {
			case UtilMeta.LIQUIFIER:
				return side > 1 ? icons[meta] : Core.oreBlocks.getIcon(side, OresMeta.BASE_BRICK);
			case UtilMeta.SAWMILL:
				return side > 1 ? icons[meta] : Core.woodBlocks.getIcon(side, WoodMeta.BASE_WOOD);
			case UtilMeta.AUTOFISHER:
				return side > 1 ? icons[meta] : Core.woodBlocks.getIcon(side, WoodMeta.BASE_WOOD);
			case UtilMeta.DICTIONARY:
				return side > 1 ? icons[meta] : Core.woodBlocks.getIcon(side, WoodMeta.BASE_WOOD);
			case UtilMeta.SLUICE:
				return side == 4 ? icons[meta] : Core.oreBlocks.getIcon(side, OresMeta.BASE_IRON);
			case UtilMeta.SPONGE:
				return side > 1 ? icons[meta] : Core.oreBlocks.getIcon(side, OresMeta.BASE_IRON);
			case UtilMeta.FISH_SORTER:
				return fishSorter[side];
			default:
				return icons[meta];
			}
		}

		return icons[0];
	}

	@Override
	public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
		if(block.getTileEntity(x, y, z) instanceof TileBookshelf) {
			return Blocks.bookshelf.getIcon(side, 0);
		}
		
		if (block.getTileEntity(x, y, z) instanceof TileSluice) {
			TileSluice tile = (TileSluice) block.getTileEntity(x, y, z);
			if(tile.direction.ordinal() == side)
				return side > 1? icons[UtilMeta.SLUICE]: sluiceUp;
			else if(tile.direction.getOpposite().ordinal() == side)
				return side > 1? sluiceBack: sluiceDown;
			else
				return Core.oreBlocks.getIcon(side, OresMeta.BASE_IRON);
		}
		
		if (side > 1) {
			if (block.getTileEntity(x, y, z) instanceof TileLiquifier) {
				TileLiquifier smelter = (TileLiquifier) block.getTileEntity(x, y, z);
				if(smelter.master == null) {
					return this.getIcon(side, block.getBlockMetadata(x, y, z));
				} else {
					if(smelter.isMaster()) {
						return liquifierIcons[1];
					} else {
						return liquifierIcons[0];
					}
				}
			}

			if (block.getTileEntity(x, y, z) instanceof TileIncubator && side > 1) {
				TileIncubator incubator = (TileIncubator) block.getTileEntity(x, y, z);
				if (incubator.isBase(x, y - 1, z)) {
					if (incubator.isTop(x, y, z) && incubator.isTop(x, y + 1, z)) {
						if (!incubator.isTop(x, y + 2, z)) {
							return incubatorIcons[0];
						}
					}
				}

				if (incubator.isBase(x, y - 2, z)) {
					if (incubator.isTop(x, y, z) && incubator.isTop(x, y - 1, z)) {
						if (!incubator.isTop(x, y + 1, z)) {
							return incubatorIcons[1];
						}
					}
				}
			}
		}

		return this.getIcon(side, block.getBlockMetadata(x, y, z));
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		int facing = BlockPistonBase.determineOrientation(world, x, y, z, entity);
		if (tile != null) {			
			if(tile instanceof TileSluice) {
				((TileSluice)tile).direction = ForgeDirection.getOrientation(facing);
				world.markBlockForUpdate(x, y, z);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null && world.getBlockMetadata(x, y, z) != UtilMeta.INCUBATOR_TOP || player.isSneaking()) {
			return false;
		}
		
		if(tile instanceof TileSluice)
			return false;
		
		if(tile instanceof TileMultiBlock) {
			TileMultiBlock multi = (TileMultiBlock) tile;
			if(multi.master != null) {
				player.openGui(Mariculture.instance, -1, world, multi.master.xCoord, multi.master.yCoord, multi.master.zCoord);
				return true;
			}
			
			return false;
		}
		
		if(tile instanceof IHasGUI) {
			player.openGui(Mariculture.instance, -1, world, x, y, z);
			return true;
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

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case UtilMeta.INCUBATOR_BASE:
			return new TileIncubator();
		case UtilMeta.INCUBATOR_TOP:
			return new TileIncubator();
		case UtilMeta.AUTOFISHER:
			return new TileAutofisher();
		case UtilMeta.LIQUIFIER:
			return new TileLiquifier();
		case UtilMeta.BOOKSHELF:
			return new TileBookshelf();
		case UtilMeta.SAWMILL:
			return new TileSawmill();
		case UtilMeta.SLUICE:
			return new TileSluice();
		case UtilMeta.DICTIONARY:
			return new TileDictionary();
        case UtilMeta.SPONGE:
            return new TileSponge();
        case UtilMeta.FISH_SORTER:
        	return new TileFishSorter();
		}

		return null;

	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		super.breakBlock(world, x, y, z, block, meta);
		
		if (meta == UtilMeta.SLUICE) {
			clearWater(world, x + 1, y, z);
			clearWater(world, x - 1, y, z);
			clearWater(world, x, y, z + 1);
			clearWater(world, x, y, z - 1);
		}
	}

	private void clearWater(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial() == Material.water) {
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case UtilMeta.AUTOFISHER:
			return Modules.fishery.isActive();
		case UtilMeta.SAWMILL:
			return Modules.factory.isActive();
		case UtilMeta.INCUBATOR_BASE:
			return Modules.fishery.isActive();
		case UtilMeta.INCUBATOR_TOP:
			return Modules.fishery.isActive();
		case UtilMeta.SLUICE:
			return Modules.factory.isActive();
		case UtilMeta.SPONGE:
			return Modules.factory.isActive();
		case UtilMeta.DICTIONARY:
			return Modules.factory.isActive();
		case UtilMeta.FISH_SORTER:
			return Modules.factory.isActive();
		case UtilMeta.UNUSED:
			return false;
		default:
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		incubatorIcons = new IIcon[2];
		incubatorIcons[0] = iconRegister.registerIcon(Mariculture.modid + ":incubatorBottom");
		incubatorIcons[1] = iconRegister.registerIcon(Mariculture.modid + ":incubatorTopTop");

		liquifierIcons = new IIcon[2];
		liquifierIcons[0] = iconRegister.registerIcon(Mariculture.modid + ":liquifierTop");
		liquifierIcons[1] = iconRegister.registerIcon(Mariculture.modid + ":liquifierBottom");
		
		fishSorter = new IIcon[6];
		for(int i = 0; i < 6; i++) {
			fishSorter[i] = iconRegister.registerIcon(Mariculture.modid + ":fishsorter" + (i + 1));
		}
		
		sluiceBack = iconRegister.registerIcon(Mariculture.modid + ":sluiceBack");
		sluiceUp = iconRegister.registerIcon(Mariculture.modid + ":sluiceUp");
		sluiceDown = iconRegister.registerIcon(Mariculture.modid + ":sluiceDown");

		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			if(isActive(i)) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)));
			}
		}
	}

	@Override
	public int getMetaCount() {
		return UtilMeta.COUNT;
	}
}
