package joshie.maritech;

import joshie.mariculture.core.lib.Modules;
import joshie.maritech.entity.EntitySpeedBoat;
import joshie.maritech.extensions.modules.ExtensionTransport;
import joshie.maritech.render.RenderSpeedBoat;
import joshie.maritech.render.RenderSpeedBoatItem;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MTClientProxy extends MTCommonProxy {
    @Override
    public void setupClient() {
        if (Modules.isActive(Modules.transport)) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySpeedBoat.class, new RenderSpeedBoat());
            MinecraftForgeClient.registerItemRenderer(ExtensionTransport.speedBoat, new RenderSpeedBoatItem());
        }
    }
}
