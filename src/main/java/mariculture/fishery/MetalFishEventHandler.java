package mariculture.fishery;

import mariculture.api.fishery.FishHatchEggEvent;
import mariculture.api.util.CachedCoords;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MetalFishEventHandler {
    @SubscribeEvent
    public void onGenerateEgg(FishHatchEggEvent event) {
        if (event.coords.size() > 0) {
            for (int coordinate = 0; coordinate < event.coords.size(); coordinate++) {
                CachedCoords pos = event.coords.get(coordinate);
                for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                    Block block = event.world.getBlock(pos.x + dir.offsetX, pos.y + dir.offsetY, pos.z + dir.offsetZ);
                    int meta = event.world.getBlockMetadata(pos.x + dir.offsetX, pos.y + dir.offsetY, pos.z + dir.offsetZ);
                    ItemStack stack = OreDicHelper.getStackFromBlockData(block, meta);
                    if (stack == null) {
                        continue;
                    } else {
                        int[] ids = OreDictionary.getOreIDs(stack);
                        for (int i : ids) {
                            String oreName = OreDictionary.getOreName(i);
                            if (oreName.startsWith("block")) {
                                event.egg.getTagCompound().setInteger(oreName, event.egg.getTagCompound().getInteger(oreName) + 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
