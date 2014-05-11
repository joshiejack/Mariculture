package mariculture.sealife;

import mariculture.Mariculture;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Modules.RegistrationModule;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Sealife extends RegistrationModule {
	@Override
	public void registerHandlers() {
		return;
	}

	@Override
	public void registerBlocks() {
		return;
	}

	@Override
	public void registerItems() {
		return;
	}
	
	@Override
	public void registerOther() {
		EntityRegistry.registerModEntity(EntityHammerhead.class, "hammerHead", EntityIds.HAMMERHEAD,  Mariculture.instance, 80, 3, true);
		EntityRegistry.addSpawn(EntityHammerhead.class, 100, 10, 30, EnumCreatureType.waterCreature, BiomeGenBase.ocean);
	}
	
	@Override
	public void registerRecipes() {
		return;
	}
}
