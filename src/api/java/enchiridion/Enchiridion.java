package enchiridion;

import java.io.File;
import java.util.Map.Entry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import enchiridion.CustomBooks.BookInfo;
import enchiridion.api.GuideHandler;

@Mod(modid = "Enchiridion", name = "Enchiridion", dependencies="required-after:Forge@[10.12.1.1082,)")
public class Enchiridion {
	public static final String modid = "books";
	public static Item items;

	@SidedProxy(clientSide = "enchiridion.ClientProxy", serverSide = "enchiridion.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance("Enchiridion")
	public static Enchiridion instance = new Enchiridion();
	
	//Root folder
	public static File root;
	
	public EventsHandler handler;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		root = new File(event.getModConfigurationDirectory() + File.separator + "books");
		Config.init(new Configuration(new File(root + File.separator + "enchiridion.cfg")));
		items = new ItemEnchiridion().setUnlocalizedName("items");
		GameRegistry.registerItem(items, "items");
		proxy.preInit();
		handler = new EventsHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			if(GuideHandler.DEBUG_ENABLED) {
				MinecraftForge.EVENT_BUS.register(new TooltipHandler());
			}
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new TextureHandler());
		}
		
		if(Config.binder_recipe) {
			CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(items, 1, ItemEnchiridion.BINDER), new Object[] {
				"SP", "SP", 'S', Items.string, 'P', Items.paper
			}));
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		
		if(CustomBooks.bookInfo.size() > 0) {
			for (Entry<String, BookInfo> books : CustomBooks.bookInfo.entrySet()) {
				BookInfo info = books.getValue();
				if(info.crafting != null) {
					CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(CustomBooks.create(books.getKey()), info.crafting));
				}
			}
		}
	}
	
	@EventHandler
    public void onReceivedBookAddition(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (message.key.equalsIgnoreCase("addBook")) {
                if (message.isStringMessage()) {
                	Config.additions.add(message.getStringValue());
                }
            }
        }
    }
}