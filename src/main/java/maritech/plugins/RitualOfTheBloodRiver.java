package maritech.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import mariculture.api.fishery.IMutationModifier;
import maritech.tile.TileIncubator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;

public class RitualOfTheBloodRiver extends RitualEffect {
    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> ritual = new ArrayList();
        ritual.add(new RitualComponent(1, 0, 1, RitualComponent.FIRE));
        ritual.add(new RitualComponent(-1, 0, -1, RitualComponent.FIRE));
        ritual.add(new RitualComponent(1, 0, -1, RitualComponent.FIRE));
        ritual.add(new RitualComponent(-1, 0, 1, RitualComponent.FIRE));
        ritual.add(new RitualComponent(2, 0, 2, RitualComponent.FIRE));
        ritual.add(new RitualComponent(-2, 0, -2, RitualComponent.FIRE));
        ritual.add(new RitualComponent(2, 0, -2, RitualComponent.FIRE));
        ritual.add(new RitualComponent(-2, 0, 2, RitualComponent.FIRE));
        ritual.add(new RitualComponent(-3, 1, 1, RitualComponent.BLANK));
        ritual.add(new RitualComponent(-3, 1, -1, RitualComponent.BLANK));
        ritual.add(new RitualComponent(-1, 1, -3, RitualComponent.BLANK));
        ritual.add(new RitualComponent(1, 1, -3, RitualComponent.BLANK));
        ritual.add(new RitualComponent(3, 1, -1, RitualComponent.BLANK));
        ritual.add(new RitualComponent(3, 1, 1, RitualComponent.BLANK));
        ritual.add(new RitualComponent(1, 1, 3, RitualComponent.BLANK));
        ritual.add(new RitualComponent(-1, 1, 3, RitualComponent.BLANK));
        ritual.add(new RitualComponent(-3, 2, 1, RitualComponent.WATER));
        ritual.add(new RitualComponent(-3, 2, -1, RitualComponent.WATER));
        ritual.add(new RitualComponent(-1, 2, -3, RitualComponent.WATER));
        ritual.add(new RitualComponent(1, 2, -3, RitualComponent.WATER));
        ritual.add(new RitualComponent(3, 2, -1, RitualComponent.WATER));
        ritual.add(new RitualComponent(3, 2, 1, RitualComponent.WATER));
        ritual.add(new RitualComponent(1, 2, 3, RitualComponent.WATER));
        ritual.add(new RitualComponent(-1, 2, 3, RitualComponent.WATER));
        ritual.add(new RitualComponent(-3, 3, 1, RitualComponent.WATER));
        ritual.add(new RitualComponent(-3, 3, -1, RitualComponent.WATER));
        ritual.add(new RitualComponent(-1, 3, -3, RitualComponent.WATER));
        ritual.add(new RitualComponent(1, 3, -3, RitualComponent.WATER));
        ritual.add(new RitualComponent(3, 3, -1, RitualComponent.WATER));
        ritual.add(new RitualComponent(3, 3, 1, RitualComponent.WATER));
        ritual.add(new RitualComponent(1, 3, 3, RitualComponent.WATER));
        ritual.add(new RitualComponent(-1, 3, 3, RitualComponent.WATER));
        return ritual;
    }
       
    private static final HashMap<IMasterRitualStone, MasterStoneModifier> modifierMap = new HashMap();
    
    public MasterStoneModifier getModifierForStone(IMasterRitualStone stone) {
        MasterStoneModifier modifier = modifierMap.get(stone);
        if (modifier != null) return modifier;
        else {
            MasterStoneModifier mod = new MasterStoneModifier();
            modifierMap.put(stone, mod);
            return mod;
        }
    }

    @Override
    public void performEffect(IMasterRitualStone ritual) {
        if (ritual.getWorld().getTotalWorldTime() % 20 == 0) {            
            String owner = ritual.getOwner();
            World worldSave = MinecraftServer.getServer().worldServers[0];
            LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);

            if (data == null) {
                data = new LifeEssenceNetwork(owner);
                worldSave.setItemData(owner, data);
            }

            int currentEssence = data.currentEssence;
            World world = ritual.getWorld();
            Random rand = world.rand;
            int x = ritual.getXCoord();
            int y = ritual.getYCoord();
            int z = ritual.getZCoord();
            
            boolean changed = getModifierForStone(ritual).update(owner, world, x, y, z, currentEssence);
            if (changed) {
                data.currentEssence = currentEssence - getCostPerRefresh();
                data.markDirty();
            }
        }
    }
    
    
    public class MasterStoneModifier implements IMutationModifier {
        public boolean isActive = false;
        private TileIncubator incubator;
        
        private TileIncubator getIncubator(World world, int x, int y, int z) {
            if (incubator != null) return incubator;
            
            TileEntity tile = world.getTileEntity(x, y + 1, z);
            if (tile instanceof TileIncubator) {
                incubator = (TileIncubator) tile;
                incubator.addMutationModifier(this);
                return incubator;
            }
            
            return null;
        }
        
        @Override
        public double getValue() {
            return isActive ? 3D: 0D;
        }
        
        public boolean update(String owner, World world, int x, int y, int z, int currentEssence) {
            TileIncubator incubator = getIncubator(world, x, y, z);
            if (incubator == null) return false;
            
            if (currentEssence < getCostPerRefresh()) {
                EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
                if (entityOwner == null) return false;
                isActive = false;
                entityOwner.addPotionEffect(new PotionEffect(Potion.confusion.id, 80));
                return false;
            } else {
                isActive = true;
                for (int i = 0; i < 3; i++) {
                    int x2 = x + world.rand.nextInt(4) - 2;
                    int y2 = y + 1 + world.rand.nextInt(3);
                    int z2 = z + world.rand.nextInt(4) - 2;
                    SpellHelper.sendIndexedParticleToAllAround(world, x, y, z, 20, world.provider.dimensionId, 2, x, y, z);
                }
                
                return true;
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 1;
    }

    @Override
    public int getInitialCooldown() {
        return 200;
    }
}
