package joshie.mariculture.util;

import joshie.mariculture.helpers.StringHelper;
import joshie.mariculture.lib.CreativeOrder;
import joshie.mariculture.modules.abyssal.block.BlockLimestoneSlab.Type;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static joshie.mariculture.lib.MaricultureInfo.MODPREFIX;

public abstract class BlockSlabMC<E extends Enum<E> & IStringSerializable, B extends Block> extends BlockSlab implements MCBlock<B> {
    protected static PropertyEnum<?> temporary;
    protected final PropertyEnum<E> property;
    protected final E[] values;
    protected Block single;

    public BlockSlabMC(Material material, Class<E> clazz) {
        super(preInit(material, clazz));
        property = (PropertyEnum<E>) temporary;
        values = clazz.getEnumConstants();
    }

    private static Material preInit(Material material, Class clazz) {
        temporary = PropertyEnum.create(clazz.getSimpleName().toLowerCase(), clazz);
        return material;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(property == null) return this.isDouble()? new BlockStateContainer(this, temporary) : new BlockStateContainer(this, HALF, temporary);
        return new BlockStateContainer(this, property);
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 0;
    }

    public E getEnumFromState(IBlockState state) {
        return state.getValue(property);
    }

    public E getEnumFromMeta(int meta) {
        if (meta < 0 || meta >= values.length) {
            meta = 0;
        }

        return values[meta];
    }

    public ItemStack getStackFromEnum(E type) {
        return new ItemStack(this, 1, type.ordinal());
    }

    public ItemStack getStackFromEnum(E type, int amount) {
        return new ItemStack(this, amount, type.ordinal());
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return property;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return getEnumFromMeta(stack.getMetadata() & 7);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return (state.getValue(property)).ordinal();
    }

    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(single);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(single, 1, getEnumFromState(state).ordinal());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | (state.getValue(property)).ordinal();

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = getDefaultState().withProperty(property, getEnumFromMeta(meta & 7));
        if (!isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = MODPREFIX + getUnlocalizedName().replace(".slab", "");
        String name = getUnlocalizedName(stack.getItemDamage());
        return StringHelper.localize(unlocalized + "." + name + ".slab");
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return getEnumFromMeta(meta).getName().replace("_", "");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (!isDouble()) {
            for (Type type : Type.values()) {
                list.add(new ItemStack(itemIn, 1, type.ordinal()));
            }
        }
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return CreativeOrder.SLABS + stack.getItemDamage();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(Item item, String name) {
        String prefix = isDouble() ? "" : "half=bottom,";
        for (E type: values) {
            ModelLoader.setCustomModelResourceLocation(item, type.ordinal(), new ModelResourceLocation(getRegistryName(), prefix + type.getClass().getSimpleName().toLowerCase() + "=" + type.getName()));
        }
    }
}
