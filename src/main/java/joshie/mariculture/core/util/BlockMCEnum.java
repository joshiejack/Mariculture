package joshie.mariculture.core.util;

import joshie.mariculture.core.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static joshie.mariculture.core.lib.MaricultureInfo.MODID;
import static joshie.mariculture.core.lib.MaricultureInfo.MODPREFIX;

public abstract class BlockMCEnum<E extends Enum<E> & IStringSerializable, B extends BlockMCEnum> extends Block implements MCBlock<B> {
    protected static PropertyEnum<?> temporary;
    protected final PropertyEnum<E> property;
    protected final E[] values;

    //Main Constructor
    public BlockMCEnum(Material material, Class<E> clazz, CreativeTabs tab) {
        super(preInit(material, clazz));
        property = (PropertyEnum<E>) temporary;
        values = clazz.getEnumConstants();
        setDefaultState(blockState.getBaseState());
        setCreativeTab(tab);

        for (E e : values) {
            setHarvestLevel(getToolType(e), getToolLevel(e), getStateFromEnum(e));
        }
    }

    //Constructor default to core tab
    public BlockMCEnum(Material material, Class<E> clazz) {
        this(material, clazz, MCTab.getCore());
    }

    private static Material preInit(Material material, Class clazz) {
        temporary = PropertyEnum.create(clazz.getSimpleName().toLowerCase(), clazz);
        return material;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(property == null) return new BlockStateContainer(this, temporary);
        return new BlockStateContainer(this, property);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(property, getEnumFromMeta(meta));
    }

    public IBlockState getStateFromEnum(E e) {
        return getDefaultState().withProperty(property, e);
    }

    public E getEnumFromBlockPos(IBlockAccess world, BlockPos pos) {
        return getEnumFromState(world.getBlockState(pos));
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

    public ItemStack getStackFromEnum(E e) {
        return new ItemStack(this, 1, e.ordinal());
    }

    public ItemStack getStackFromEnum(E e, int amount) {
        return new ItemStack(this, amount, e.ordinal());
    }

    public E getEnumFromStack(ItemStack stack) {
        return values[Math.max(0, Math.min(values.length - 1, stack.getItemDamage()))];
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(property)).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    //Default to pickaxe
    public String getToolType(E type) {
        return "pickaxe";
    }

    //Default to level 0
    protected int getToolLevel(E level) {
        return 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int side) {
        return !doesDrop(state) ? null : super.getItemDropped(state, rand, side);
    }

    protected boolean doesDrop(IBlockState state) {
        return true;
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return 500;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = MODPREFIX + getUnlocalizedName();
        String name = getNameFromStack(stack);
        return StringHelper.localize(unlocalized + "." + name);
    }

    protected String getNameFromStack(ItemStack stack) {
        return getEnumFromStack(stack).getName().replace("_", "");
    }

    protected boolean isInCreative(E e) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (E e : values) {
            if (isInCreative(e)) {
                list.add(new ItemStack(item, 1, e.ordinal()));
            }
        }
    }

    @Override
    public B register(String name) {
        Block block = this;
        block.setUnlocalizedName(name.replace("_", "."));
        block.setRegistryName(new ResourceLocation(MODID, name));
        GameRegistry.register(block);
        getItemBlock().register(name); //Register the item block
        Set<Class<? extends TileEntity>> registered = new HashSet<>();
        for (E e: values) {
            IBlockState state = getStateFromEnum(e);
            if (hasTileEntity(state)) {
                Class<? extends TileEntity> tile = createTileEntity(null, state).getClass();
                if (registered.add(tile)) GameRegistry.registerTileEntity(tile, MODID + ":" + tile.getSimpleName().toLowerCase().replace("tile", ""));
            }
        }

        return (B) this;
    }

    @SideOnly(Side.CLIENT)
    protected ResourceLocation getResourceForEnum(E e) {
        return new ResourceLocation(MODID, getUnlocalizedName().replace(MODID + ".",  "") + "_" + e.getName());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(Item item, String name) {
        for (E e: values) {
            ModelLoader.setCustomModelResourceLocation(item, e.ordinal(), new ModelResourceLocation(getRegistryName(), e.getClass().getSimpleName() + "=" + e.getName()));
        }
    }
}