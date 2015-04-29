package mariculture.api.fishery;

import java.util.ArrayList;

import mariculture.api.util.CachedCoords;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class FishHatchEggEvent extends Event {
    /** Called when attempting to hatch a fish **/
    public final World world;
    public final int x;
    public final int y;
    public final int z;
    public final ArrayList<CachedCoords> coords;
    
    public ItemStack male;
    public ItemStack female;
    public ItemStack egg;
    
    public FishHatchEggEvent(ItemStack fish1, ItemStack fish2, ItemStack egg, World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        this.male = fish1;
        this.female = fish2;
        this.egg = egg;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.coords = coords;
    }
}
