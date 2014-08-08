package mariculture.core;

import mariculture.Mariculture;
import mariculture.core.config.Machines.Client;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TickingMeta;
import mariculture.core.lib.WaterMeta;
import mariculture.core.render.AnvilSpecialRenderer;
import mariculture.core.render.HammerSpecialRenderer;
import mariculture.core.render.RenderAnvil;
import mariculture.core.render.RenderBlockCaster;
import mariculture.core.render.RenderCopperTank;
import mariculture.core.render.RenderFakeItem;
import mariculture.core.render.RenderHammer;
import mariculture.core.render.RenderHandler;
import mariculture.core.render.RenderIngotCaster;
import mariculture.core.render.RenderNuggetCaster;
import mariculture.core.render.RenderOyster;
import mariculture.core.render.RenderSingleItem;
import mariculture.core.render.RenderSpecialHandler;
import mariculture.core.render.RenderVat;
import mariculture.core.render.RenderVoidBottle;
import mariculture.core.render.VatSpecialRenderer;
import mariculture.core.tile.TileAirPump;
import mariculture.core.tile.TileAnvil;
import mariculture.core.tile.TileAutohammer;
import mariculture.core.tile.TileVat;
import mariculture.core.util.EntityFakeItem;
import mariculture.diving.render.ModelAirPump;
import mariculture.diving.render.RenderCompressorBase;
import mariculture.diving.render.RenderCompressorTop;
import mariculture.factory.EntityFLUDDSquirt;
import mariculture.factory.Factory;
import mariculture.factory.render.ModelFLUDD;
import mariculture.factory.render.RenderCustomItem;
import mariculture.factory.render.RenderFLUDDSquirt;
import mariculture.factory.render.RenderFluidDictionary;
import mariculture.factory.render.RenderGeyser;
import mariculture.factory.render.RenderPressureVessel;
import mariculture.factory.tile.TileFLUDDStand;
import mariculture.fishery.EntityBass;
import mariculture.fishery.EntityHook;
import mariculture.fishery.EntityItemFireImmune;
import mariculture.fishery.Fish;
import mariculture.fishery.render.FishFeederSpecialRenderer;
import mariculture.fishery.render.FishTankSpecialRenderer;
import mariculture.fishery.render.HatcherySpecialRenderer;
import mariculture.fishery.render.RenderFeeder;
import mariculture.fishery.render.RenderFishTank;
import mariculture.fishery.render.RenderHatchery;
import mariculture.fishery.render.RenderNet;
import mariculture.fishery.render.RenderProjectileFish;
import mariculture.fishery.render.RenderSifter;
import mariculture.fishery.tile.TileFeeder;
import mariculture.fishery.tile.TileFishTank;
import mariculture.fishery.tile.TileHatchery;
import mariculture.transport.EntitySpeedBoat;
import mariculture.transport.Transport;
import mariculture.transport.render.RenderSpeedBoat;
import mariculture.transport.render.RenderSpeedBoatItem;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    private static final ResourceLocation AIR_PUMP = new ResourceLocation(Mariculture.modid, "textures/blocks/air_pump_texture.png");
    private static final ResourceLocation SIFT = new ResourceLocation(Mariculture.modid, "textures/blocks/sift_texture.png");
    private static final ResourceLocation FEEDER = new ResourceLocation(Mariculture.modid, "textures/blocks/feeder_texture.png");
    private static final ResourceLocation FLUDD = new ResourceLocation(Mariculture.modid, "textures/blocks/fludd_texture.png");
    private static final ResourceLocation PRESSURE_VESSEL = new ResourceLocation(Mariculture.modid, "textures/blocks/pressure_vessel_texture.png");

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

            ClientRegistry.bindTileEntitySpecialRenderer(TileFLUDDStand.class, new RenderSpecialHandler(new ModelFLUDD(), FLUDD));
            RenderHandler.register(Core.renderedMachines, MachineRenderedMeta.GEYSER, RenderGeyser.class);
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.PRESSURE_VESSEL, RenderPressureVessel.class);
            RenderHandler.register(Core.tanks, TankMeta.DIC, RenderFluidDictionary.class);
        }

        if (Modules.isActive(Modules.fishery)) {
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