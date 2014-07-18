package hacker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import minetweaker.MineTweakerAPI;
import minetweaker.mc1710.brackets.ItemBracketHandler;
import minetweaker.mc1710.brackets.LiquidBracketHandler;
import minetweaker.mc1710.brackets.OreBracketHandler;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "aMTDevHack", name = "MTDevHack", dependencies = "required-after:MineTweaker3")
public class MTDevHack {
    public static final boolean REMOVE_FML_ANNOYANCE = true;

    private static void setLogger(Class clazz) {
        try {
            Field result = clazz.getDeclaredField("logger");
            result.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(result, result.getModifiers() & ~Modifier.FINAL);
            result.set(null, HackedLogger.getLogger());
        } catch (Exception e) {}
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        setLogger(TextureMap.class);

        if (REMOVE_FML_ANNOYANCE) {
            setLogger(TileEntity.class);
        }
    }

    @EventHandler
    public void init(FMLPostInitializationEvent event) {
        MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
        MineTweakerAPI.registerBracketHandler(new LiquidBracketHandler());
        MineTweakerAPI.registerBracketHandler(new OreBracketHandler());
    }
}
