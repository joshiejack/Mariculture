package joshie.maritech.handlers;

import java.util.HashMap;

import joshie.mariculture.core.events.BlockEvent;
import joshie.mariculture.core.events.BlockEvent.BlockBroken;
import joshie.mariculture.core.events.BlockEvent.GetInventoryIIcon;
import joshie.mariculture.core.events.BlockEvent.GetWorldIIcon;
import joshie.mariculture.core.events.BlockEvent.TilePlaced;
import joshie.maritech.util.IBlockExtension;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEvents {
    public static HashMap<Block, IBlockExtension> blocks = new HashMap();
    
    public static void register(Block block, IBlockExtension extension) {
        blocks.put(block, extension);
        ItemEvents.register(Item.getItemFromBlock(block), extension);
    }
    
    @SubscribeEvent
    public void getIsActive(BlockEvent.GetIsActive event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.isActive = extension.isActive(event.meta, event.isActive);
        }
    }

    @SubscribeEvent
    public void isValidTab(BlockEvent.GetIsValidTab event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.isValid = extension.isValidTab(event.tab, event.meta, event.isValid);
        }
    }

    @SubscribeEvent
    public void getToolType(BlockEvent.GetToolType event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.tooltype = extension.getToolType(event.meta, event.tooltype);
        }
    }

    @SubscribeEvent
    public void getToolLevel(BlockEvent.GetToolLevel event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.level = extension.getToolLevel(event.meta, event.level);
        }
    }

    @SubscribeEvent
    public void getHardness(BlockEvent.GetHardness event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.hardness = extension.getHardness(event.meta, event.hardness);
        }
    }

    @SubscribeEvent
    public void getTileEntity(BlockEvent.GetTileEntity event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.tile = extension.getTileEntity(event.meta, event.tile);
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            IBlockExtension extension = blocks.get(event.world.getBlock(event.x, event.y, event.z));
            if (extension != null) {
                if (extension.onRightClickBlock(event.world, event.x, event.y, event.z, event.entityPlayer)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockPlaced(TilePlaced event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            extension.onTilePlaced(event.stack, event.tile, event.entity, event.direction);
        }
    }

    @SubscribeEvent
    public void onBlockBroken(BlockBroken event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.setCanceled(extension.onBlockBroken(event.meta, event.world, event.x, event.y, event.z));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void getInventoryIcon(GetInventoryIIcon event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.icon = extension.getInventoryIcon(event.meta, event.side, event.icon);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void getWorldIcon(GetWorldIIcon event) {
        IBlockExtension extension = blocks.get(event.block);
        if (extension != null) {
            event.icon = extension.getWorldIcon(event.world, event.x, event.y, event.z, event.side, event.icon);
        }
    }
}
