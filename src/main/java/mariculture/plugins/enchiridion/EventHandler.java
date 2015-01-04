package mariculture.plugins.enchiridion;

import java.util.ArrayList;

import mariculture.core.Core;
import mariculture.core.config.GeneralStuff;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.diving.ItemArmorSnorkel;
import mariculture.fishery.Fishery;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import enchiridion.api.DisplayRegistry;

public class EventHandler {
    public static boolean isLoaded = false;

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        if (GeneralStuff.SPAWN_BOOKS) {
            BookSpawnHelper.spawn(event.player, GuideMeta.PROCESSING);
        }
    }

    @SubscribeEvent
    public void onCrafting(ItemCraftedEvent event) {
        if (GeneralStuff.SPAWN_BOOKS) {
            ItemStack stack = event.crafting;
            if (Modules.isActive(Modules.diving) && stack.getItem() instanceof ItemArmorSnorkel) {
                BookSpawnHelper.spawn(event.player, GuideMeta.DIVING);
            }
            
            if (Modules.isActive(Modules.fishery) && stack.getItem() == Fishery.rodReed) {
                BookSpawnHelper.spawn(event.player, GuideMeta.FISHING);
            }
            
            if (Modules.isActive(Modules.factory) && stack.getItem() == Core.crafting && stack.getItemDamage() == CraftingMeta.WHEEL) {
                BookSpawnHelper.spawn(event.player, GuideMeta.MACHINES);
            }
        }
    }

    @SubscribeEvent
    public void onOreDictionaryRegistration(OreRegisterEvent event) {
        //Initialize all existing Entries
        if (!isLoaded) {
            String[] ores = OreDictionary.getOreNames();
            for (String ore : ores) {
                ArrayList<ItemStack> stacks = OreDictionary.getOres(ore);
                for (ItemStack stack : stacks)
                    if (stack != null && ore != null && !ore.equals("")) {
                        DisplayRegistry.registerOreDictionaryCycling(ore);
                    }
            }

            isLoaded = true;
        }

        DisplayRegistry.registerOreDictionaryCycling(event.Name);
    }
}
