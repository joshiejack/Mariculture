package mariculture.core.items;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.tile.TileAnvil;
import mariculture.core.util.IHasGUI;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHammer extends ItemPickaxe implements IItemRegistry {	
	public ItemHammer(ToolMaterial brick) {
		super(brick);
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);
		if(block == Blocks.bed) return false;
		if(BlockHelper.canRotate(block)) {
			int meta = world.getBlockMetadata(x, y, z);
			if(player.isSneaking()) {
				world.setBlockMetadataWithNotify(x, y, z, BlockHelper.rotateVanillaBlockAlt(world, Block.getIdFromBlock(block), meta, x, y, z), 3);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, BlockHelper.rotateVanillaBlock(world, Block.getIdFromBlock(block), meta, x, y, z), 3);
			}
		}
		
		if(block != null) {
			if(block == Core.renderedMachines && world.getBlockMetadata(x, y, z) == MachineRenderedMeta.ANVIL && !player.isSneaking()) return false;
			if(world.getTileEntity(x, y, z) instanceof IHasGUI && !player.isSneaking()) return false;
			if(block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side)))
				return !world.isRemote;
		}
		
		return false;
	}
	
	public int getItemEnchantability() {
		return 10;
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return !(world.getTileEntity(x, y, z) instanceof TileAnvil);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == Core.crafting && stack2.getItemDamage() == CraftingMeta.BURNT_BRICK;
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)5.0F, 0));
        return multimap;
    }
	
	@Override
	public void register(Item item) {
		MaricultureRegistry.register(getName(new ItemStack(item, 1, 0)), new ItemStack(item, 1, 0));
	}
	
	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String theName, name = getName(new ItemStack(this));
		String[] aName = name.split("\\.");
		if(aName.length == 2) theName = aName[0] + aName[1].substring(0, 1).toUpperCase() + aName[1].substring(1);
		else theName = name;
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + theName);
	}
}
