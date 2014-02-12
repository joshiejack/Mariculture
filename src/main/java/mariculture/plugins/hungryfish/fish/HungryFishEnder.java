package mariculture.plugins.hungryfish.fish;

import mariculture.core.helpers.BlockHelper;
import mariculture.core.util.Rand;
import mariculture.fishery.fish.FishEnder;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class HungryFishEnder extends FishEnder {
	public HungryFishEnder(int id) {
		super(id);
	}

	@Override
	public String getSpecies() {
		return (this.getClass().getSimpleName().toLowerCase()).substring(10);
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.getFoodStats().addStats(1, -0.05F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		
		if(!world.isRemote) {
			world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
			int x = (int) ((player.posX) + Rand.rand.nextInt(64) - 32);
			int z = (int) ((player.posZ) + Rand.rand.nextInt(64) - 32);
			if(BlockHelper.chunkExists(world, x, z)) {
				int y = world.getTopSolidOrLiquidBlock(x, z);
				
				if(world.getBlock(x, y, z).getMaterial() != Material.lava) {
					world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
					player.setPositionAndUpdate(x, y, z);
					world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
				}
			}
		}
	}
}
