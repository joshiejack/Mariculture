package mariculture.fishery.blocks;

import java.util.Random;

import mariculture.core.blocks.base.BlockFluid;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.util.Rand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

public class BlockFishOil extends BlockFluid {
	public BlockFishOil(Fluid fluid, Material material) {
		super(fluid, material);
		quantaPerBlock = 7;
		quantaPerBlockFloat = 7;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLivingBase && !world.isRemote) {
			EntityLivingBase living = (EntityLivingBase) entity;
			if(!living.isPotionActive(Potion.regeneration.id)) {
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 0, true));
			}
		}
	}
}
