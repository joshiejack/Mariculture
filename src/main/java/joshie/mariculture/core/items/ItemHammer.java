package joshie.mariculture.core.items;

import joshie.lib.base.ItemBasePickaxe;
import joshie.lib.helpers.DirectionHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.cofh.BlockHelper;
import joshie.mariculture.core.lib.CraftingMeta;
import joshie.mariculture.core.lib.MCModInfo;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.core.tile.TileAnvil;
import joshie.mariculture.core.util.IHasGUI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHammer extends ItemBasePickaxe {
    public ItemHammer(ToolMaterial material) {
        super(MCModInfo.MODPATH, MaricultureTab.tabFactory, material);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
        if ((double) block.getBlockHardness(world, x, y, z) != 0.0D) {
            stack.damageItem(1, entity);
        }

        int meta = world.getBlockMetadata(x, y, z);
        if (ForgeHooks.isToolEffective(stack, block, meta) && !isAnvil(block, meta)) {
            ForgeDirection dir = DirectionHelper.getFacingFromEntity(entity).getOpposite();
            joshie.mariculture.core.helpers.BlockHelper.destroyBlock(world, x, y - 1, z, block, stack);
            for (int i = 1; i <= 2; i++) {
                joshie.mariculture.core.helpers.BlockHelper.destroyBlock(world, x + (i * dir.offsetX), y, z + (i * dir.offsetZ), block, stack);
                joshie.mariculture.core.helpers.BlockHelper.destroyBlock(world, x + (i * dir.offsetX), y - 1, z + (i * dir.offsetZ), block, stack);

                joshie.mariculture.core.helpers.BlockHelper.destroyBlock(world, x - 1 + (i * dir.offsetX), y, z + (i * dir.offsetZ), block, stack);
                joshie.mariculture.core.helpers.BlockHelper.destroyBlock(world, x + 1 + (i * dir.offsetX), y - 1, z + (i * dir.offsetZ), block, stack);

                joshie.mariculture.core.helpers.BlockHelper.destroyBlock(world, x + (i * dir.offsetX), y, z - 1 + (i * dir.offsetZ), block, stack);
                joshie.mariculture.core.helpers.BlockHelper.destroyBlock(world, x + (i * dir.offsetX), y - 1, z + 1 + (i * dir.offsetZ), block, stack);
            }
        }

        return true;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (ForgeHooks.isToolEffective(stack, block, meta) && !isAnvil(block, meta)) {
            if (block instanceof BlockStone) {
                return efficiencyOnProperMaterial * 3;
            } else return efficiencyOnProperMaterial;
        }

        return super.getDigSpeed(stack, block, meta);
    }

    private boolean isAnvil(Block block, int meta) {
        return block == Core.renderedMachines && meta == MachineRenderedMeta.ANVIL;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.bed) return false;
        if (BlockHelper.canRotate(block)) {
            int meta = world.getBlockMetadata(x, y, z);
            if (player.isSneaking()) {
                world.setBlockMetadataWithNotify(x, y, z, BlockHelper.rotateVanillaBlockAlt(world, Block.getIdFromBlock(block), meta, x, y, z), 3);
            } else {
                world.setBlockMetadataWithNotify(x, y, z, BlockHelper.rotateVanillaBlock(world, Block.getIdFromBlock(block), meta, x, y, z), 3);
            }
        }

        if (block != null) {
            if (block == Core.renderedMachines && world.getBlockMetadata(x, y, z) == MachineRenderedMeta.ANVIL && !player.isSneaking()) return false;
            if (world.getTileEntity(x, y, z) instanceof IHasGUI && !player.isSneaking()) return false;
            if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) return !world.isRemote;
        }

        return false;
    }

    @Override
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
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", 5.0F, 0));
        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":hammer");
    }
}
