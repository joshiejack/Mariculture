package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.util.IHasMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMachine extends BlockContainer implements IItemRegistry, IHasMeta {
	protected IIcon[] icons;
	
	public BlockMachine(Material material) {
		super(material);
		setCreativeTab(MaricultureTab.tabMariculture);
		setHarvestLevels();
	}
	
	public void setHarvestLevels() {
		for(int i = 0; i < getMetaCount(); i++) {
			setHarvestLevel(getToolType(i), getToolLevel(i), UtilMeta.INCUBATOR_BASE);
		}
	}
	
	public abstract String getToolType(int meta);
	public abstract int getToolLevel(int meta);
	public abstract boolean isActive(int meta);
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BlockHelper.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if(player.capabilities.isCreativeMode)
			return world.setBlockToAir(x, y, z);
		if(doesDrop(world.getBlockMetadata(x, y, z)) || world.isRemote) {
			return super.removedByPlayer(world, player, x, y, z);
		} else {
			return onBlockDropped(world, x, y, z);
		}
    }
	
	public Item getItemDropped(int meta, Random random, int j) {
		return !doesDrop(meta)? null: super.getItemDropped(meta, random, j);
    }
	
	//Whether this block drops or not
	public boolean doesDrop(int meta) {
		return true;
	}
	
	//Call by removed by player for when the block is dropped
	public boolean onBlockDropped(World world, int x, int y, int z) { 
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this, 1, j)), new ItemStack(this, 1, j));
		}
	}

	@Override
	public String getName(ItemStack stack) {
		return RegistryHelper.getName(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creative, List list) {
		for (int meta = 0; meta < getMetaCount(); ++meta) {
			if (isActive(meta)) {
				list.add(new ItemStack(item, 1, meta));
			}
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta < getMetaCount()) {
			return icons[meta];
		} else { 
			return icons[0];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)));
		}
	}
	
	@Override
	public Class<? extends ItemBlock> getItemClass() {
		return null;
	}
}
