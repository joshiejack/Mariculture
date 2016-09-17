package joshie.mariculture.modules.aquaculture;

import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.modules.aquaculture.block.BlockOyster;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The Aquaculture is all about the breeding, breeding is planned to be a much simpler system,
 *  more vanilla like, it's intended purpose is mostly for fun/food, Only basic fish can be made,
 *  More advanced/crazy things will be in other mods*/
@MCLoader
public class Aquaculture {
    public static final BlockOyster OYSTER = new BlockOyster().register("oyster");

    public static void preInit() {}

    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
        //ClientRegistry.bindTileEntitySpecialRenderer(TileOyster.class, new SpecialRendererOyster());
    }
}
