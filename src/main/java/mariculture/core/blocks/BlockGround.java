package mariculture.core.blocks;

import java.util.ArrayList;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.blocks.base.BlockDecorative;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.Dye;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GroundMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGround extends BlockDecorative {
	public BlockGround() {
		super(Material.sand);
	}
	
	@Override
	public String getToolType(int meta) {
		return "shovel";
	}

	@Override
	public int getToolLevel(int meta) {
		return meta == GroundMeta.BUBBLES? 0: 1;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case GroundMeta.BUBBLES:
			return 0.5F;
		case GroundMeta.ANCIENT:
			return 0.7F;
		}

		return 3F;
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == GroundMeta.BUBBLES) {
			for(int i = y; BlockHelper.isWater(world, x, i + 1, z); i++) {
				for (int j = 0; j < 2; ++j)  {
	                float f = rand.nextFloat() - rand.nextFloat();
	                float f1 = rand.nextFloat() - rand.nextFloat();
	                float f2 = rand.nextFloat() - rand.nextFloat();
	                world.spawnParticle("bubble", x + 0.5D + (double)f, i + (double)f1, z + 0.5D + (double)f2, 0, 0, 0);
	            }
			}
		}
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int j) {
       return Item.getItemFromBlock(Blocks.sand);
    }
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for(int i = 0; i < count; i++) {
            if(metadata == GroundMeta.ANCIENT) {
            	if(!Extra.NERF_FOOD) {
	            	if(world.rand.nextInt(7) <= fortune) ret.add(new ItemStack(Items.bone));
	            	if(world.rand.nextInt(3) <= fortune) ret.add(new ItemStack(Items.dye, 1, Dye.BONE));
            	}
            	
            	if(world.rand.nextInt(15) <= fortune) ret.add(new ItemStack(Items.dye, 1, Dye.INK));
            	if(world.rand.nextInt(64) <= fortune) ret.add(new ItemStack(Items.gold_nugget));
            	if(world.rand.nextInt(48) <= fortune) ret.add(new ItemStack(Blocks.chest));
            	if(world.rand.nextInt(52) <= fortune) ret.add(new ItemStack(Blocks.trapped_chest));
            	if(world.rand.nextInt(32) <= fortune) ret.add(new ItemStack(Items.fish, 1, world.rand.nextInt(FishSpecies.species.size())));
            	if(ret.size() == 0) ret.add(new ItemStack(this, 1, GroundMeta.ANCIENT));
            } else {
            	Item item = getItemDropped(metadata, world.rand, fortune);
                if (item != null) {
                    ret.add(new ItemStack(item, 1, damageDropped(metadata)));
                }
            }
        }
        return ret;
    }
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta == GroundMeta.BUBBLES) return Blocks.sand.getIcon(side, meta);
		else return blockIcon;
	}

	@Override
	public int getMetaCount() {
		return GroundMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(Mariculture.modid + ":ancientSand");
	}
}
