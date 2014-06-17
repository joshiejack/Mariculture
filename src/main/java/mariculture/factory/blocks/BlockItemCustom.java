package mariculture.factory.blocks;

import java.util.List;

import mariculture.api.util.Text;
import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.PlansMeta;
import mariculture.factory.Factory;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockItemCustom extends ItemBlockMariculture {
    private IIcon[] icons;

    public BlockItemCustom(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /** SLAB CODE **/

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        boolean isFullBlock = Block.getBlockFromItem(stack.getItem()) != Factory.customSlabs;
        if (isFullBlock) return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        else if (stack.stackSize == 0) return false;
        else if (!player.canPlayerEdit(x, y, z, side, stack)) return false;
        else {
            Block block = world.getBlock(x, y, z);
            int i1 = world.getBlockMetadata(x, y, z);
            int j1 = i1 & 7;
            boolean flag = (i1 & 8) != 0;

            if (PlansMeta.isTheSame(world, x, y, z, stack) && (side == 1 && !flag || side == 0 && flag) && block == Factory.customSlabs && j1 == stack.getItemDamage()) {
                if (world.checkNoEntityCollision(Factory.customSlabsDouble.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, Factory.customSlabsDouble, j1, 3)) {
                    Factory.customSlabsDouble.onBlockPlacedBy(world, x, y, z, player, stack);
                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, Factory.customSlabsDouble.stepSound.func_150496_b(), (Factory.customSlabsDouble.stepSound.getVolume() + 1.0F) / 2.0F, Factory.customSlabsDouble.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                }

                return true;
            } else return func_150946_a(stack, player, world, x, y, z, side) ? true : super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
        int i1 = x;
        int j1 = y;
        int k1 = z;
        Block block = world.getBlock(x, y, z);
        int l1 = world.getBlockMetadata(x, y, z);
        int i2 = l1 & 7;
        boolean flag = (l1 & 8) != 0;

        if ((side == 1 && !flag || side == 0 && flag) && block == Factory.customSlabs && i2 == stack.getItemDamage()) return true;
        else {
            if (side == 0) {
                --y;
            }

            if (side == 1) {
                ++y;
            }

            if (side == 2) {
                --z;
            }

            if (side == 3) {
                ++z;
            }

            if (side == 4) {
                --x;
            }

            if (side == 5) {
                ++x;
            }

            Block block1 = world.getBlock(x, y, z);
            int j2 = world.getBlockMetadata(x, y, z);
            i2 = j2 & 7;
            return block1 == Factory.customSlabs && i2 == stack.getItemDamage() ? true : super.func_150936_a(world, i1, j1, k1, side, player, stack);
        }
    }

    private boolean func_150946_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        if (side == 0) {
            --y;
        }

        if (side == 1) {
            ++y;
        }

        if (side == 2) {
            --z;
        }

        if (side == 3) {
            ++z;
        }

        if (side == 4) {
            --x;
        }

        if (side == 5) {
            ++x;
        }

        Block block = world.getBlock(x, y, z);
        int i1 = world.getBlockMetadata(x, y, z);
        int j1 = i1 & 7;

        if (PlansMeta.isTheSame(world, x, y, z, stack) && block == Factory.customSlabs && j1 == stack.getItemDamage()) {
            if (world.checkNoEntityCollision(Factory.customSlabsDouble.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, Factory.customSlabsDouble, j1, 3)) {
                Factory.customSlabsDouble.onBlockPlacedBy(world, x, y, z, player, stack);
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, Factory.customSlabsDouble.stepSound.func_150496_b(), (Factory.customSlabsDouble.stepSound.getVolume() + 1.0F) / 2.0F, Factory.customSlabsDouble.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        } else return false;
    }

    /** END SLAB CODE **/
    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound()) if (stack.stackTagCompound.hasKey("Name")) return stack.stackTagCompound.getString("Name");

        return StatCollector.translateToLocal(stack.getUnlocalizedName());
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            addTextureInfo(stack, player, list, bool);
        } else {
            list.add(Text.getShiftText("custom"));
        }
    }

    public static void addTextureInfo(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (stack.hasTagCompound()) {
            addToList(list, 1, stack);
            addToList(list, 2, stack);
            addToList(list, 5, stack);
            addToList(list, 3, stack);
            addToList(list, 4, stack);
            addToList(list, 0, stack);
        }
    }

    public static void addToList(List list, int num, ItemStack stack) {
        String name = stack.stackTagCompound.getString("BlockIdentifier" + num);
        Item block = (Item) Item.itemRegistry.getObject(name);
        list.add(getName(num) + ": " + block.getItemStackDisplayName(new ItemStack(block, 1, stack.stackTagCompound.getIntArray("BlockMetas")[num])));
    }

    public static String getName(int i) {
        switch (i) {
            case 0:
                return StatCollector.translateToLocal("mariculture.string.bottom");
            case 1:
                return StatCollector.translateToLocal("mariculture.string.top");
            case 2:
                return StatCollector.translateToLocal("mariculture.string.north");
            case 3:
                return StatCollector.translateToLocal("mariculture.string.south");
            case 4:
                return StatCollector.translateToLocal("mariculture.string.west");
            case 5:
                return StatCollector.translateToLocal("mariculture.string.east");
            default:
                return "";
        }
    }

    @Override
    public String getName(ItemStack stack) {
        return this.getUnlocalizedName().substring(5);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] { null };
    }
}
