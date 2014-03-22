package mariculture.core.items;

import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.sealife.EntityHammerhead;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCrafting extends ItemMariculture {
	public ItemCrafting(int i) {
		super(i);
	}

	@Override
	public int getMetaCount() {
		return CraftingMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
			case CraftingMeta.GOLDEN_SILK:
				return "goldenSilk";
			case CraftingMeta.GOLDEN_THREAD:
				return "goldenThread";
			case CraftingMeta.POLISHED_STICK:
				return "polishedStick";
			case CraftingMeta.NEOPRENE:
				return "neoprene";
			case CraftingMeta.PLASTIC:
				return "plastic";
			case CraftingMeta.LENS:
				return "scubaLens";
			case CraftingMeta.ALUMINUM_SHEET:
				return "aluminumSheet";
			case CraftingMeta.HEATER:
				return "heater";
			case CraftingMeta.COOLER:
				return "cooler";
			case CraftingMeta.CARBIDE:
				return "carbide";
			case CraftingMeta.WHEEL:
				return "wheel";
			case CraftingMeta.WICKER:
				return "wicker";
			case CraftingMeta.PLASTIC_YELLOW:
				return "plasticYellow";
			case CraftingMeta.DRAGON_EGG:
				return "dragonEgg";
			case CraftingMeta.POLISHED_TITANIUM: 
				return "titaniumRod";
			case CraftingMeta.BLANK_PLAN: 
				return "plan_blank"; 
			case CraftingMeta.DEPRECATED_CHALK: 
				return "chalk";
			case CraftingMeta.TITANIUM_SHEET:
				return "titaniumSheet";
			case CraftingMeta.LENS_GLASS:
				return "snorkelLens";
			case CraftingMeta.BURNT_BRICK:
				return "burntBrick";
			case CraftingMeta.TITANIUM_ROD:
				return "titaniumRodBasic";
			case CraftingMeta.LIFE_CORE:
				return "lifeCore";
			default:
				return "unnamed";
		}
	}
	
	public boolean spawnEnderDragon(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
		if (world.isRemote)  {
            return true;
        }  else {
        	EntityDragon dragon = new EntityDragon(world);
        	dragon.setPosition(player.posX, player.posY + 10, player.posZ);
            world.spawnEntityInWorld(dragon);
            stack.stackSize--;
        }
		
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		int dmg = stack.getItemDamage();
		if(dmg == CraftingMeta.DRAGON_EGG && Extra.ENABLE_ENDER_SPAWN) {
			return spawnEnderDragon(stack, player, world, x, y, z);
		}
		
		if(Extra.DEBUG_ON && stack.getItemDamage() == CraftingMeta.ALUMINUM_SHEET) {
			if(Modules.sealife.isActive()) {
				if(!world.isRemote) {
					EntityHammerhead shark = new EntityHammerhead(world);
					shark.setPosition(x, y + 1, z);
					world.spawnEntityInWorld(shark);
				}
			}
		}

		return true;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case CraftingMeta.DRAGON_EGG:
			return Modules.fishery.isActive();
		case CraftingMeta.DEPRECATED_CHALK: return false;
		case CraftingMeta.BLANK_PLAN:
			return Modules.factory.isActive();
		case CraftingMeta.POLISHED_STICK:
			return Modules.fishery.isActive();
		case CraftingMeta.POLISHED_TITANIUM:
			return Modules.fishery.isActive();
		case CraftingMeta.LIFE_CORE:
			return Modules.factory.isActive();
		default:
			return true;
		}
	}
}
