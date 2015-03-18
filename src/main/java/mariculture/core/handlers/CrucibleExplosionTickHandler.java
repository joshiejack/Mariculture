package mariculture.core.handlers;

import mariculture.api.core.IFuelTickHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CrucibleExplosionTickHandler implements IFuelTickHandler {
	private final int chance;
	private final float radius;
	
	public CrucibleExplosionTickHandler(float radius, int chance) {
		this.chance = chance;
		this.radius = radius;
	}
	
	@Override
	public int onTemperatureIncrease(TileEntity crucible, int original) {
		World world = crucible.getWorldObj();
		if (world.rand.nextInt(chance) == 0) {
			EntityPlayer player = world.getClosestPlayer(crucible.xCoord, crucible.yCoord, crucible.zCoord, 128D);
			if (player != null) {
				world.createExplosion(player, crucible.xCoord + world.rand.nextDouble(), crucible.yCoord + 2 + world.rand.nextDouble(), crucible.zCoord + world.rand.nextDouble(), radius, true);
			}
			
			return original * 1000;
		}
		
		return original;
	}

}
