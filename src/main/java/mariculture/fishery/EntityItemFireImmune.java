package mariculture.fishery;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemFireImmune extends EntityItem {
    public EntityItemFireImmune(World world) {
        super(world);
    }

    public EntityItemFireImmune(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);

        isImmuneToFire = true;
    }
}
