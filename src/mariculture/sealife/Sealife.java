package mariculture.sealife;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;
import mariculture.Mariculture;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules.Module;

public class Sealife extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive && Extra.DEBUG_ON;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityHammerhead.class, "hammerHead", EntityIds.HAMMERHEAD, 
				Mariculture.instance, 80, 3, true);
		
		EntityRegistry.addSpawn(EntityHammerhead.class, 100, 10, 30, EnumCreatureType.waterCreature, BiomeGenBase.ocean);
	}
	
	@Override
	public void registerHandlers() {
	}

	@Override
	public void registerBlocks() {
	}

	@Override
	public void registerItems() {
	}
	
	@Override
	public void registerOther() {
	}
	
	@Override
	public void addRecipes() {
	}
}
