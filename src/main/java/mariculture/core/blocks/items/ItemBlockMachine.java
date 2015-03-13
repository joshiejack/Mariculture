package mariculture.core.blocks.items;

import mariculture.core.Core;
import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.events.MaricultureEvents;
import mariculture.core.lib.MachineMeta;
import mariculture.lib.util.Text;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemBlockMachine extends ItemBlockMariculture {
    public ItemBlockMachine(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        int meta = stack.getItemDamage();
        switch (meta) {
            case MachineMeta.BOOKSHELF:
                return "bookshelf";
            case MachineMeta.DICTIONARY_ITEM:
                return "dictionary";
            case MachineMeta.SAWMILL:
                return "sawmill";
            case MachineMeta.FISH_SORTER:
                return "fishSorter";
            case MachineMeta.UNPACKER:
                return "unpacker";
        }

        return MaricultureEvents.getItemName(field_150939_a, meta, "machines");
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = field_150939_a.getUnlocalizedName().replace("tile.", "").replace("_", ".");
        String name = getName(stack).replaceAll("(.)([A-Z])", "$1$2").toLowerCase();
        return Text.localize(unlocalized.replace("mariculture.", MaricultureEvents.getMod(stack.getItem(), stack.getItemDamage(), "mariculture") + ".") + "." + name);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() != MachineMeta.AUTOFISHER) return stack;
        MovingObjectPosition object = getMovingObjectPositionFromPlayer(world, player, true);
        if (object == null) return stack;
        else {
            if (object.typeOfHit == MovingObjectType.BLOCK) {
                int x = object.blockX;
                int y = object.blockY;
                int z = object.blockZ;

                if (!world.canMineBlock(player, x, y, z)) return stack;
                if (!player.canPlayerEdit(x, y, z, object.sideHit, stack)) return stack;
                if (world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0 && world.isAirBlock(x, y + 1, z)) {
                    world.setBlock(x, y + 1, z, field_150939_a, MachineMeta.AUTOFISHER, 2);

                    if (!player.capabilities.isCreativeMode) {
                        --stack.stackSize;
                    }
                }
            }

            return stack;
        }
    }
}
