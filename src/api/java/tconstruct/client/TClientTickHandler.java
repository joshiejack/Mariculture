package tconstruct.client;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import tconstruct.TConstruct;
import tconstruct.common.TRepo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TClientTickHandler
{
    Minecraft mc = Minecraft.getMinecraft();

    TControls controlInstance = ((TProxyClient) TConstruct.proxy).controlInstance;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tickEnd (ClientTickEvent event)
    {
        
    }

    /*
     * @Override public EnumSet<TickType> ticks () { return
     * EnumSet.of(TickType.RENDER); }
     */
}
