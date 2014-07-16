package mariculture.core.handlers;

import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFluid;
import mariculture.core.items.ItemBuckets;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.Event.Result.ALLOW;

public class BucketHandler {
    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event) {
        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        if (block instanceof BlockFluid) if (event.current.getItem() == Items.bucket) {
            ItemStack ret = ((ItemBuckets) Core.buckets).getBucket(block);
            if (ret != null) {
                event.result = ret;
                event.setResult(Result.ALLOW);
            }
        }
    }
}
