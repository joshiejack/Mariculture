package joshie.mariculture.api.fishery;

import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.inventory.IInventory;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class FishTickEvent extends Event {
    /** This event is called everytime, a fish ticks; Output slots are 7-12
     * This is called before a fish ticks, you can cancel the event to stop,
     * them from producing eggs/products etc. **/
    //The Male Slot is 5, The Female Slot Number is 6, if you wish to affect them
    public final boolean isMale;
    public final FishSpecies species;
    public final IInventory inventory;
    
    public FishTickEvent(IInventory inventory, FishSpecies species, boolean isMale) {
        this.inventory = inventory;
        this.species = species;
        this.isMale = false;
    }
}
