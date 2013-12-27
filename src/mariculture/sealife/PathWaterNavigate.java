package mariculture.sealife;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class PathWaterNavigate extends PathNavigate {
	public PathWaterNavigate(EntityLiving entity, World world) {
		super(entity, world);
	}
}
