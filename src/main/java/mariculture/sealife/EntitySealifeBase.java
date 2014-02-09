package mariculture.sealife;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
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
