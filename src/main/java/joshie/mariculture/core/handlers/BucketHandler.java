package joshie.mariculture.core.handlers;

import joshie.mariculture.core.Core;
import joshie.mariculture.core.blocks.base.BlockFluid;
import joshie.mariculture.core.items.ItemBuckets;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler {
    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event) {
        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        if (block instanceof BlockFluid) if (event.current.getItem() == Items.bucket) {
            ItemStack ret = ((ItemBuckets) Core.buckets).getBucket(block);
            if (ret != null) {
                event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
                event.result = ret;
                event.setResult(Result.ALLOW);
            }
        }
    }
}
