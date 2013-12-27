package mariculture.plugins.hungryfish;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mariculture.core.lib.CoralMeta;
import mariculture.world.ItemCoral;

public class ItemHungryCoral extends ItemCoral {
	public ItemHungryCoral(int i, Block block) {
		super(i, block);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (stack.getItemDamage() <= CoralMeta.KELP_MIDDLE) {
			--stack.stackSize;

			player.getFoodStats().addStats(1, 0.015F);
			world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}

		return stack;
	}
}
