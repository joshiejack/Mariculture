package mariculture.magic.enchantments;

import java.util.ArrayList;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.MirrorHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Jewelry;
import mariculture.magic.Magic;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantmentClock extends EnchantmentJewelry {
	public EnchantmentClock(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("clock");
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
	public boolean canApplyTogether(Enchantment enchantment) {
		return false;
	}
	
	private static ArrayList<EntityPlayer> list;
	public static boolean hasEnchant(EntityPlayer player, int what) {
		if(EnchantHelper.hasEnchantment(Magic.clock, player)) {
			ItemStack[] check = MaricultureHandlers.mirror.getMirrorContents(player);
			for (int j = 0; j < check.length; j++) {
				if (check[j] != null) {
					if (check[j].hasTagCompound()) {
						if (check[j].stackTagCompound.hasKey("Extra")) {
							if (check[j].stackTagCompound.getInteger("Extra") == what) {
								list.add(player);
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}

	public static void activate(World world, int what, int time) {
		list = new ArrayList<EntityPlayer>();
		double numberOfPlayers = world.playerEntities.size();
		double hasEnchant = 0;
		double percentage = Extra.PERCENT_NEEDED;
		if(numberOfPlayers > 0) {
			for(int i = 0; i < world.playerEntities.size(); i++) {
				if(world.playerEntities.get(i) instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) world.playerEntities.get(i);
					hasEnchant = hasEnchant(player, what)? hasEnchant + 1: hasEnchant;
				}
			}
		}
		
		double percent = (hasEnchant / numberOfPlayers) * 100;
		if(percent >= percentage) {
			if(list != null) {
				System.out.println(list.size());
				for(EntityPlayer player: list) {
					EnchantHelper.damageItems(Magic.clock, player, 1);
				}
			}
			
			world.setWorldTime(time);
		}
	}
}
