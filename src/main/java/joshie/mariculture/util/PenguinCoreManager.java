package joshie.mariculture.util;

import static joshie.mariculture.lib.MaricultureInfo.JAVAPATH;
import static joshie.mariculture.lib.MaricultureInfo.MODNAME;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import joshie.mariculture.lib.MaricultureInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.versioning.VersionParser;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PenguinCoreManager {
	private static final String LATEST_VERSION = "0.0.2"; //The latest version of the dependency 
	private static final String LATEST_BUILD = "1"; // The latest build of the dependency
	
	private static final String DEPNAME = "PenguinCore"; //The name of the mod dependency
	private static final String MODID = "PenguinCore"; //The modid of the mod dependency
	private static final String JARNAME = "PenguinCore"; //the jarname of the mod dependency
	private static final String JOBNAME = "PenguinCore"; //the jobname of the mod dependency
	private static final String MC_VERSION = "1.8"; //the minecraft version of the mod dependency
	private static final String DEV = MaricultureInfo.IS_DEV? "-dev": "";
	
	/** Everything else below can be pretty much ignored **/
	
	private static final String BASE_URL = "http://joshiejack.uk:8080/job/" + JOBNAME + "/lastSuccessfulBuild/artifact/build/libs/";
	private static final String FILE_NAME = JARNAME + "-" + MC_VERSION + "-" + LATEST_VERSION + "-" + LATEST_BUILD + DEV + ".jar";
	private static final String DOWNLOAD = BASE_URL + FILE_NAME;
	private static final String FALLBACK = "http://joshiejack.uk:8080/job/" + JOBNAME + "/";
    public static final Logger logger = LogManager.getLogger(DEPNAME + " Manager");

    @SidedProxy(serverSide = JAVAPATH + "util." + MODID + "Manager$CommonProxy", clientSide = JAVAPATH + "util." + MODID + "Manager$ClientProxy")
    public static CommonProxy proxy;
    long totalSize;
    
    private static String format(String string, Object... data) {
    	return StatCollector.translateToLocalFormatted("joshie.downloader." + string, data);
    }
    
    private static String translate(String string) {
    	return StatCollector.translateToLocal("joshie.downloader." + string);
    }

    /** Checks whether the mod is the correct version **/
    public static boolean isPresent(String required) {
        if (!isLoaded()) {
            logger.error(DEPNAME + " is not installed!");
            if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                FMLCommonHandler.instance().bus().register(new PenguinCoreManager());
            } else {
                String s = format("missing", MODNAME, DEPNAME, FALLBACK);
                FMLLog.bigWarning(s);
                FMLCommonHandler.instance().getSidedDelegate().haltGame(s, null);
            }
        } else if (!isVersionCorrect(required)) {
            logger.error(DEPNAME + " is not the correct version!");
            proxy.throwError();
        } else {
            return true;
        }

        return false;
    }

    /** Check whether the mod is loaded **/
    private static boolean isLoaded() {
        return Loader.isModLoaded(MODID);
    }

    /** Validates the mods version **/
    private static boolean isVersionCorrect(String required) {
        return VersionParser.parseRange(required).containsVersion(Loader.instance().getIndexedModList().get(MODID).getProcessedVersion());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiYesNo(new GuiYesNoCallback() {
                @Override
                public void confirmClicked(boolean yesButton, int screenID) {
                    if (yesButton) {
                        download();
                    } else {
                        FMLLog.bigWarning("joshie.downloader.missing", FALLBACK);
                        Minecraft.getMinecraft().shutdown();
                    }
                }
            }, format("0", DEPNAME, MODNAME), format("1", DEPNAME), 0));
        }
    }

    @SideOnly(Side.CLIENT)
    public void download() {
        final GuiScreenWorking screenWorking = new GuiScreenWorking();
        Minecraft.getMinecraft().displayGuiScreen(screenWorking);

        final Thread downloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                screenWorking.resetProgressAndMessage(format("downloading", DEPNAME));
                screenWorking.displayLoadingString("Starting...");

                File target;
                URL download;
                OutputStream output = null;
                InputStream input = null;
                try {
                    target = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "mods" + File.separator + FILE_NAME);
                    download = new URL(DOWNLOAD);
                    output = new FileOutputStream(target);
                    input = download.openStream();
                    DownloadCountingOutputStream countingOutputStream = new DownloadCountingOutputStream(output, screenWorking);

                    totalSize = Long.valueOf(download.openConnection().getHeaderField("Content-Length"));
                    screenWorking.displayLoadingString(String.format("Downloading file (%.3f MB)...", totalSize / 1000000F));

                    IOUtils.copy(input, countingOutputStream);
                } catch (IOException e) {
                    //Delete file on close cause it could be corrupt
                    new File(Minecraft.getMinecraft().mcDataDir + File.separator + "mods" + File.separator + FILE_NAME).deleteOnExit();
                    e.printStackTrace();

                    proxy.showFailedScreen();
                } finally {
                    IOUtils.closeQuietly(output);
                    IOUtils.closeQuietly(input);
                }
            }
        }, "Enchiridion Downloader");

        downloadThread.setDaemon(true);
        downloadThread.start();
    }

    private class DownloadCountingOutputStream extends CountingOutputStream {

        private final IProgressUpdate update;

        public DownloadCountingOutputStream(OutputStream out, IProgressUpdate update) {
            super(out);
            this.update = update;
        }

        @Override
        protected void afterWrite(int n) throws IOException {
            super.afterWrite(n);

            if (getByteCount() == totalSize) {
                proxy.showRestartScreen();
            }

            update.setLoadingProgress((int) (getByteCount() / totalSize));
        }
    }

    @SideOnly(Side.CLIENT)
    private static class GuiScreenHold extends GuiScreen {
        private String topMessage;
        private String bottomMessage;

        public GuiScreenHold(String topMessage, String bottomMessage) {
            this.topMessage = topMessage;
            this.bottomMessage = bottomMessage;
        }

        public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
            drawDefaultBackground();
            drawCenteredString(fontRendererObj, topMessage, width / 2, 90, 16777215);
            drawCenteredString(fontRendererObj, bottomMessage, width / 2, 110, 16777215);
        }

        protected void keyTyped(char character, int keycode) {}
    }

    public static class CommonProxy {
        public void throwError() {
            FMLLog.bigWarning(format("outofdate", FALLBACK));
        }

        public void showRestartScreen() {}

        public void showFailedScreen() {}
    }

    @SideOnly(Side.CLIENT)
    public static class ClientProxy extends CommonProxy {
        public void throwError() {
            throw new CustomModLoadingErrorDisplayException() {
                @Override
                public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
                    try {
                        Desktop.getDesktop().browse(URI.create(FALLBACK));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
                    String s = format("outofdate", DEPNAME);
                    fontRenderer.drawString(s, (errorScreen.width / 2) - (fontRenderer.getStringWidth(s) / 2), errorScreen.height / 2, 0xFFFFFFFF);
                }
            };
        }

        public void showRestartScreen() {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenHold(format("success", DEPNAME), translate("restart")));
        }

        public void showFailedScreen() {
            try {
                Desktop.getDesktop().browse(URI.create(FALLBACK));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenHold(format("failed.0", DEPNAME), format("failed.1", DEPNAME, FALLBACK)));
        }
    }
}