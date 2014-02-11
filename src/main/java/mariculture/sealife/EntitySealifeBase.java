package mariculture.sealife;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntitySealifeBase extends EntityMob {
	public EntitySealifeBase(World world) {
		super(world);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
}
