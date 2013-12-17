package mariculture.fishery.blocks;

import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.SingleMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockItemNet extends ItemMariculture {
	public BlockItemNet(int i) {
		super(i);
		setHasSubtypes(false);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		final MovingObjectPosition object = this.getMovingObjectPositionFromPlayer(world, player, true);

		if (object == null) {
			return stack;
		} else {
			if (object.typeOfHit == EnumMovingObjectType.TILE) {
				int x = object.blockX;
				int y = object.blockY;
				int z = object.blockZ;

				if (!world.canMineBlock(player, x, y, z)) {
					return stack;
				}

				if (!player.canPlayerEdit(x, y, z, object.sideHit, stack)) {
					return stack;
				}

				if (world.getBlockMaterial(x, y, z) == Material.water && world.getBlockMetadata(x, y, z) == 0
						&& world.isAirBlock(x, y + 1, z)) {
					world.setBlock(x, y + 1, z, Core.singleBlocks.blockID, SingleMeta.NET, 2);

					if (!player.capabilities.isCreativeMode) {
						--stack.stackSize;
					}
				}
			}

			return stack;
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName();
	}
	
	@Override 
	public String getName(ItemStack stack) {
		return "net";
	}
}
