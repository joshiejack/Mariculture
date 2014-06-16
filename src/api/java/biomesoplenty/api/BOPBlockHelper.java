package biomesoplenty.api;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class BOPBlockHelper
{
    public static Block get(String name)
    {
        return GameRegistry.findBlock("BiomesOPlenty", name);
    }
    
    public static String getUniqueName(Block block)
    {
        return GameData.blockRegistry.getNameForObject(block);
    }
}
