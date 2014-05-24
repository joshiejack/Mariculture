package mariculture.fishery.blocks;

import java.util.Random;

import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockCustard extends BlockFluid {
	public BlockCustard(Fluid fluid, Material material) {
		super(fluid, material);
		quantaPerBlock = 5;
		quantaPerBlockFloat = 5;
		tickRate = 60;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		return;
	}

	@Override
	public void velocityToAddToEntity(World world, int x, int y, int z, Entity entity, Vec3 vec) {
		entity.motionX /= 5;
		entity.motionZ /= 5;
		if(entity.motionX == 0 && entity.motionZ == 0) {
			entity.motionY /= 5;
		} else {
			entity.motionY /= 2;
		}
		
		if(entity.motionY < -0.05) {
			entity.motionY = -0.05;
		}
	}
}
