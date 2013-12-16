package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantmentClock extends EnchantmentJewelry {
	public EnchantmentClock(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("clock");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		if(stack.getItem() instanceof ItemJewelry) {
			return true;
		}
		
		return false;
	}

	@Override
	public int getMinEnchantability(final int level) {
		return 10;
	}

	@Override
	public int getMaxEnchantability(final int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean canApplyTogether(final Enchantment enchantment) {
		return false;
	}

	public static void activate(final World world, final int what, final int time) {
		int numberOfPlayersOnline = 0;
		int numberThatWillAllowChange = 0;

		for (int i = 0; i < world.playerEntities.size(); i++) {
			if (world.playerEntities.get(i) instanceof EntityPlayer) {
				numberOfPlayersOnline++;

				final EntityPlayer player = (EntityPlayer) world.playerEntities.get(i);
				if (EnchantHelper.hasEnchantment(Magic.clock, player)) {
					final ItemStack[] check = MaricultureHandlers.mirror.getMirrorContents(player);

					boolean found = false;

					for (int j = 0; j < check.length; j++) {
						if (check[j] != null) {
							if (check[j].hasTagCompound()) {
								if (check[j].stackTagCompound.hasKey("Extra")) {
									if (check[j].stackTagCompound.getInteger("Extra") == what) {
										found = true;
									}
								}
							}
						}
					}

					if (found) {
						numberThatWillAllowChange++;
						EnchantHelper.damageItems(Magic.clock, player, 1);
					}
				}
			}
		}

		if (numberThatWillAllowChange >= numberOfPlayersOnline && numberOfPlayersOnline != 0 && numberThatWillAllowChange != 0) {
			world.setWorldTime(time);
		}
	}
}
