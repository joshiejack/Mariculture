/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Zoe Lee (Kihira) and iLexiconn
 *
 * Some code used from CodeChickenCore under the MIT License
 *
 * See LICENSE for full License
 */

package mariculture;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import mariculture.core.lib.MCModInfo;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.client.CustomModLoadingErrorDisplayException;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EnchiridionManager {
    public static final String requiredVersion = "1.1";
    public static final String latestVersion = "1.2b";
    public static final String latestFileName = "Enchiridion-1.7.X-" + latestVersion + ".jar";
    public static final String downloadLink = "http://addons.cursecdn.com/files/2211/379/" + latestFileName;
    public static final String downloadFallback = "http://minecraft.curseforge.com/mc-mods/76612-enchiridion/files";
    public static final Logger logger = LogManager.getLogger("Enchiridion Manager");

    @SidedProxy(serverSide = MCModInfo.JAVAPATH + "EnchiridionManager$CommonProxy", clientSide = MCModInfo.JAVAPATH + "EnchiridionManager$ClientProxy")
    public static CommonProxy proxy;
    long totalSize;

    public static boolean isPresent() {
        if (!isLoaded()) {
            logger.error("Enchiridion is not installed!");
            if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
                FMLCommonHandler.instance().bus().register(new EnchiridionManager());
            } else {
                String s = StatCollector.translateToLocalFormatted("enchiridion.downloader.missing", MCModInfo.MODID, downloadFallback);
                FMLLog.bigWarning(s);
                FMLCommonHandler.instance().getSidedDelegate().haltGame(s, null);
            }
        } else if (!isVersionCorrect()) {
            logger.error("Enchiridion is not the correct version!");
            proxy.throwError();
        } else {
            return true;
        }

        return false;
    }

    private static boolean isLoaded() {
        return Loader.isModLoaded("Enchiridion");
    }

    private static boolean isVersionCorrect() {
        return VersionParser.parseRange(requiredVersion).containsVersion(Loader.instance().getIndexedModList().get("Enchiridion").getProcessedVersion());
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
                        FMLLog.bigWarning("enchiridion.downloader.missing", downloadFallback);
                        Minecraft.getMinecraft().shutdown();
                    }
                }
            }, I18n.format("enchiridion.downloader.0", MCModInfo.MODID), I18n.format("enchiridion.downloader.1"), 0));
        }
    }

    @SideOnly(Side.CLIENT)
    public void download() {
        final GuiScreenWorking screenWorking = new GuiScreenWorking();
        Minecraft.getMinecraft().displayGuiScreen(screenWorking);

        final Thread downloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                screenWorking.resetProgressAndMessage(I18n.format("enchiridion.downloader.downloading"));
                screenWorking.resetProgresAndWorkingMessage("Starting...");

                File target;
                URL download;
                OutputStream output = null;
                InputStream input = null;
                try {
                    target = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "mods" + File.separator + latestFileName);
                    download = new URL(downloadLink);
                    output = new FileOutputStream(target);
                    input = download.openStream();
                    DownloadCountingOutputStream countingOutputStream = new DownloadCountingOutputStream(output, screenWorking);

                    totalSize = Long.valueOf(download.openConnection().getHeaderField("Content-Length"));
                    screenWorking.displayProgressMessage(String.format("Downloading file (%.3f MB)...", totalSize / 1000000F));

                    IOUtils.copy(input, countingOutputStream);
                } catch (IOException e) {
                    //Delete file on close cause it could be corrupt
                    new File(Minecraft.getMinecraft().mcDataDir + File.separator + "mods" + File.separator + latestFileName).deleteOnExit();
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
            FMLLog.bigWarning(StatCollector.translateToLocalFormatted("enchiridion.downloader.outofdate", downloadFallback));
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
                        Desktop.getDesktop().browse(URI.create(downloadFallback));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
                    String s = I18n.format("enchiridion.downloader.outofdate");
                    fontRenderer.drawString(s, (errorScreen.width / 2) - (fontRenderer.getStringWidth(s) / 2), errorScreen.height / 2, 0xFFFFFFFF);
                }
            };
        }

        public void showRestartScreen() {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenHold(I18n.format("enchiridion.downloader.success"), I18n.format("enchiridion.downloader.restart")));
        }

        public void showFailedScreen() {
            try {
                Desktop.getDesktop().browse(URI.create(downloadFallback));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenHold(I18n.format("enchiridion.downloader.failed.0", downloadFallback), I18n.format("enchiridion.downloader.failed.1")));
        }
    }
}