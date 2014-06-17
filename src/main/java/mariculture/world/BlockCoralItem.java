package mariculture.world;

import mariculture.Mariculture;
import mariculture.api.util.Text;
import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.CoralMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoralItem extends ItemBlockMariculture {
    private IIcon[] icons;
    private Block spawnBlock;

    public BlockCoralItem(Block block) {
        super(block);
        spawnBlock = block;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (spawnBlock == WorldPlus.plantGrowable) return super.getItemStackDisplayName(stack);
        else return Text.localize("mariculture.string.dried") + " " + super.getItemStackDisplayName(stack);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.coral" + "." + getName(stack);
    }

    @Override
    public String getName(ItemStack stack) {
        String name = "";
        switch (stack.getItemDamage()) {
            case CoralMeta.KELP:
                return "kelp";
            case CoralMeta.KELP_MIDDLE:
                return "kelp_middle";
            case CoralMeta.LIGHT_BLUE:
                return "blue";
            case CoralMeta.YELLOW:
                return "yellow";
            case CoralMeta.MAGENTA:
                return "magenta";
            case CoralMeta.BROWN:
                return "brown";
            case CoralMeta.ORANGE:
                return "orange";
            case CoralMeta.PINK:
                return "pink";
            case CoralMeta.PURPLE:
                return "purple";
            case CoralMeta.RED:
                return "red";
            case CoralMeta.GREY:
                return "grey";
            case CoralMeta.LIGHT_GREY:
                return "lightgrey";
            case CoralMeta.WHITE:
                return "white";
            default:
                return "coral";
        }
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        if (dmg < getMetaCount()) return icons[dmg];

        return icons[0];
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.snow && (world.getBlockMetadata(x, y, z) & 7) < 1) {
            side = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
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
        }

        if (!player.canPlayerEdit(x, y, z, side, stack)) return false;
        else if (stack.stackSize == 0) return false;
        else {
            if (canPlaceBlockOnSide(world, x, y, z, side, stack)) {
                int j1 = stack.getItemDamage();
                if (world.setBlock(x, y, z, spawnBlock, j1, 2)) {
                    if (world.getBlock(x, y, z) == spawnBlock) {
                        spawnBlock.onBlockPlacedBy(world, x, y, z, player, stack);
                        spawnBlock.onPostBlockPlaced(world, x, y, z, j1);
                    }

                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;

                    return true;
                }
            }

            return false;
        }
    }

    private boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side, ItemStack stack) {
        if (side != 1) return false;

        if (world.getBlock(x, y + 1, z).getMaterial() != Material.water) return false;

        Block block = world.getBlock(x, y - 1, z);
        int meta = world.getBlockMetadata(x, y - 1, z);
        if (stack.getItemDamage() == CoralMeta.KELP) return BlockCoral.canSustainKelp(block, meta);
        if (stack.getItemDamage() > CoralMeta.KELP_MIDDLE) return BlockCoral.canSustainCoral(block, meta);

        return false;
    }

    @Override
    public int getMetaCount() {
        return CoralMeta.COUNT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[getMetaCount()];

        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "coral_" + getName(new ItemStack(this, 1, i)));
        }
    }
}