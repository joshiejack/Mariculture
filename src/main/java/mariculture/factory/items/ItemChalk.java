package mariculture.factory.items;

import mariculture.core.Core;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.items.ItemDamageable;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.PlansMeta;
import mariculture.factory.Factory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChalk extends ItemDamageable {
	public ItemChalk(int id, int dmg) {
		super(id, dmg);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		int slot = PlayerHelper.hasItem(player, new ItemStack(Core.craftingItem, 1, CraftingMeta.BLANK_PLAN), false);
		if(slot != -1) {
			int id = world.getBlockId(x, y, z);
			if(Block.blocksList[id] != null) {
				Block block = Block.blocksList[id];
				int plan = PlansMeta.getPlanType(block, id, world, x, y, z);
				if(plan != -1) {
					SpawnItemHelper.spawnItem(world, x, y + 1, z, PlansMeta.setType(new ItemStack(Factory.plans), plan));
					if(!player.capabilities.isCreativeMode) {
						player.inventory.decrStackSize(slot, 1);
					}
					
					world.spawnParticle("largeexplode", x, y + 1, z, 0.0D, 0.0D, 0.0D);
					stack.stackSize--;
				}
			}
		}
		
		return true;
	}
}
