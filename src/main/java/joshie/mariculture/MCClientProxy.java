package joshie.mariculture;

import joshie.mariculture.core.Core;
import joshie.mariculture.core.config.Machines.Client;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.core.lib.MachineRenderedMultiMeta;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.lib.RenderIds;
import joshie.mariculture.core.lib.TankMeta;
import joshie.mariculture.core.lib.TickingMeta;
import joshie.mariculture.core.lib.WaterMeta;
import joshie.mariculture.core.render.AnvilSpecialRenderer;
import joshie.mariculture.core.render.HammerSpecialRenderer;
import joshie.mariculture.core.render.RenderAnvil;
import joshie.mariculture.core.render.RenderBlockCaster;
import joshie.mariculture.core.render.RenderCopperTank;
import joshie.mariculture.core.render.RenderFakeItem;
import joshie.mariculture.core.render.RenderHammer;
import joshie.mariculture.core.render.RenderHandler;
import joshie.mariculture.core.render.RenderIngotCaster;
import joshie.mariculture.core.render.RenderNuggetCaster;
import joshie.mariculture.core.render.RenderOyster;
import joshie.mariculture.core.render.RenderSingleItem;
import joshie.mariculture.core.render.RenderSpecialHandler;
import joshie.mariculture.core.render.RenderVat;
import joshie.mariculture.core.render.RenderVoidBottle;
import joshie.mariculture.core.render.VatSpecialRenderer;
import joshie.mariculture.core.tile.TileAirPump;
import joshie.mariculture.core.tile.TileAnvil;
import joshie.mariculture.core.tile.TileAutohammer;
import joshie.mariculture.core.tile.TileVat;
import joshie.mariculture.core.util.EntityFakeItem;
import joshie.mariculture.diving.render.ModelAirPump;
import joshie.mariculture.diving.render.RenderCompressorBase;
import joshie.mariculture.diving.render.RenderCompressorTop;
import joshie.mariculture.factory.EntityFLUDDSquirt;
import joshie.mariculture.factory.Factory;
import joshie.mariculture.factory.render.ModelFLUDD;
import joshie.mariculture.factory.render.ModelRotor;
import joshie.mariculture.factory.render.RenderCustomItem;
import joshie.mariculture.factory.render.RenderFLUDDSquirt;
import joshie.mariculture.factory.render.RenderFluidDictionary;
import joshie.mariculture.factory.render.RenderGeyser;
import joshie.mariculture.factory.render.RenderPressureVessel;
import joshie.mariculture.factory.tile.TileFLUDDStand;
import joshie.mariculture.factory.tile.TileRotorAluminum;
import joshie.mariculture.factory.tile.TileRotorCopper;
import joshie.mariculture.factory.tile.TileRotorTitanium;
import joshie.mariculture.fishery.EntityBass;
import joshie.mariculture.fishery.EntityHook;
import joshie.mariculture.fishery.EntityItemFireImmune;
import joshie.mariculture.fishery.Fish;
import joshie.mariculture.fishery.render.FishFeederSpecialRenderer;
import joshie.mariculture.fishery.render.FishTankSpecialRenderer;
import joshie.mariculture.fishery.render.HatcherySpecialRenderer;
import joshie.mariculture.fishery.render.RenderFeeder;
import joshie.mariculture.fishery.render.RenderFishTank;
import joshie.mariculture.fishery.render.RenderHatchery;
import joshie.mariculture.fishery.render.RenderNet;
import joshie.mariculture.fishery.render.RenderProjectileFish;
import joshie.mariculture.fishery.render.RenderSifter;
import joshie.mariculture.fishery.tile.TileFeeder;
import joshie.mariculture.fishery.tile.TileFishTank;
import joshie.mariculture.fishery.tile.TileHatchery;
import joshie.mariculture.transport.EntitySpeedBoat;
import joshie.mariculture.transport.Transport;
import joshie.mariculture.transport.render.RenderSpeedBoat;
import joshie.mariculture.transport.render.RenderSpeedBoatItem;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MCClientProxy extends MCCommonProxy {
    private static final ResourceLocation AIR_PUMP = new ResourceLocation(Mariculture.modid, "textures/blocks/air_pump_texture.png");
    private static final ResourceLocation SIFT = new ResourceLocation(Mariculture.modid, "textures/blocks/sift_texture.png");
    private static final ResourceLocation FEEDER = new ResourceLocation(Mariculture.modid, "textures/blocks/feeder_texture.png");
    private static final ResourceLocation FLUDD = new ResourceLocation(Mariculture.modid, "textures/blocks/fludd_texture.png");
    private static final ResourceLocation PRESSURE_VESSEL = new ResourceLocation(Mariculture.modid, "textures/blocks/pressure_vessel_texture.png");
    private static final ResourceLocation COPPER = new ResourceLocation(Mariculture.modid, "textures/blocks/copperRotor.png");
    private static final ResourceLocation ALUMINUM = new ResourceLocation(Mariculture.modid, "textures/blocks/aluminumRotor.png");
    private static final ResourceLocation TITANIUM = new ResourceLocation(Mariculture.modid, "textures/blocks/titaniumRotor.png");

    public static KeyBinding key_activate;
    public static KeyBinding key_toggle;

    @Override
    public void setupClient() {
        key_activate = new KeyBinding("key.activate", Keyboard.KEY_V, "key.categories.gameplay");
        key_toggle = new KeyBinding("key.toggle", Keyboard.KEY_Y, "key.categories.gameplay");

        ClientRegistry.registerKeyBinding(key_activate);
        ClientRegistry.registerKeyBinding(key_toggle);

        RenderIds.RENDER_ALL = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Core.renderedMachines), new RenderSingleItem());
        RenderingRegistry.registerBlockHandler(new RenderHandler());
        RenderingRegistry.registerEntityRenderingHandler(EntityFakeItem.class, new RenderFakeItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TileVat.class, new VatSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAnvil.class, new AnvilSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAutohammer.class, new HammerSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAirPump.class, new RenderSpecialHandler(new ModelAirPump(), AIR_PUMP));
        RenderHandler.register(Core.water, WaterMeta.OYSTER, RenderOyster.class);
        RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.ANVIL, RenderAnvil.class);
        RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.NUGGET_CASTER, RenderNuggetCaster.class);
        RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.INGOT_CASTER, RenderIngotCaster.class);
        RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.BLOCK_CASTER, RenderBlockCaster.class);
        RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.AUTO_HAMMER, RenderHammer.class);
        RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.VAT, RenderVat.class);
        RenderHandler.register(Core.tanks, TankMeta.TANK, RenderCopperTank.class);
        RenderHandler.register(Core.tanks, TankMeta.BOTTLE, RenderVoidBottle.class);

        if (Modules.isActive(Modules.diving)) {
            RenderIds.DIVING = RenderingRegistry.addNewArmourRendererPrefix("diving");
            RenderIds.SCUBA = RenderingRegistry.addNewArmourRendererPrefix("scuba");
            RenderIds.SNORKEL = RenderingRegistry.addNewArmourRendererPrefix("snorkel");
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_BASE, RenderCompressorBase.class);
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_TOP, RenderCompressorTop.class);
        }

        if (Modules.isActive(Modules.factory)) {
            RenderingRegistry.registerEntityRenderingHandler(EntityFLUDDSquirt.class, new RenderFLUDDSquirt());
            RenderIds.FLUDD = RenderingRegistry.addNewArmourRendererPrefix("fludd");
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customBlock), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customFlooring), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customSlabs), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customSlabsDouble), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customStairs), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customFence), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customGate), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customLight), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customWall), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customRFBlock), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(Factory.fludd, new RenderSingleItem());

            ClientRegistry.bindTileEntitySpecialRenderer(TileRotorCopper.class, new RenderSpecialHandler(new ModelRotor(), COPPER));
            ClientRegistry.bindTileEntitySpecialRenderer(TileRotorAluminum.class, new RenderSpecialHandler(new ModelRotor(), ALUMINUM));
            ClientRegistry.bindTileEntitySpecialRenderer(TileRotorTitanium.class, new RenderSpecialHandler(new ModelRotor(), TITANIUM));
            ClientRegistry.bindTileEntitySpecialRenderer(TileFLUDDStand.class, new RenderSpecialHandler(new ModelFLUDD(), FLUDD));
            RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.GEYSER, RenderGeyser.class);
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.PRESSURE_VESSEL, RenderPressureVessel.class);
            RenderHandler.register(Core.tanks, TankMeta.DIC, RenderFluidDictionary.class);
        }

        if (Modules.isActive(Modules.fishery)) {
            RenderIds.FISHING = RenderingRegistry.addNewArmourRendererPrefix("fishing");
            RenderingRegistry.registerEntityRenderingHandler(EntityItemFireImmune.class, new RenderItem());
            RenderingRegistry.registerEntityRenderingHandler(EntityHook.class, new RenderFish());
            RenderingRegistry.registerEntityRenderingHandler(EntityBass.class, new RenderProjectileFish(Fish.bass.getID()));
            ClientRegistry.bindTileEntitySpecialRenderer(TileFeeder.class, new FishFeederSpecialRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileFishTank.class, new FishTankSpecialRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileHatchery.class, new HatcherySpecialRenderer());
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.SIFTER, RenderSifter.class);
            RenderHandler.register(Core.tanks, TankMeta.HATCHERY, RenderHatchery.class);
            RenderHandler.register(Core.ticking, TickingMeta.NET, RenderNet.class);
            RenderHandler.register(Core.tanks, TankMeta.FISH, RenderFishTank.class);
            if (Client.SHOW_FISH) {
                RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.FISH_FEEDER, RenderFeeder.class);
            }
        }

        if (Modules.isActive(Modules.transport)) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySpeedBoat.class, new RenderSpeedBoat());
            MinecraftForgeClient.registerItemRenderer(Transport.speedBoat, new RenderSpeedBoatItem());
        }
    }
}