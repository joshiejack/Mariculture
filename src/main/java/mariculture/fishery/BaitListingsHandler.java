package mariculture.fishery;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodType;
import mariculture.core.util.MCTranslate;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

//Adds the information about the baits rods can use, if they are registered
public class BaitListingsHandler {
    @SubscribeEvent
    public void addToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        List list = event.toolTip;

        RodType quality = Fishing.fishing.getRodType(stack);
        if (quality != null) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                if (quality.canDisplay(stack)) {
                    if (quality.canUseBaitManually()) {
                        list.add(mariculture.lib.util.Text.INDIGO + MCTranslate.translate("bait"));
                    } else {
                        list.add(mariculture.lib.util.Text.INDIGO + MCTranslate.translate("bait2"));
                    }

                    ArrayList<ItemStack> baits = Fishing.fishing.getCanUseList(quality);
                    for (ItemStack bait : baits)
                        if (bait != null) {
                            list.add(bait.getDisplayName());
                        }
                }
            } else {
                if (quality.canUseBaitManually()) {
                    list.add(MCTranslate.getShiftText("rod"));
                } else {
                    list.add(MCTranslate.getShiftText("rod2"));
                }
            }
        }
    }
}
