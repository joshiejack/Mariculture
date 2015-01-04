package mariculture.fishery.gui;

import mariculture.Mariculture;
import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.Feature;
import mariculture.core.network.PacketClick;
import mariculture.core.network.PacketFishTankAdd;
import mariculture.core.network.PacketHandler;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.tile.TileFishTank;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiFishTank extends GuiMariculture {
    private boolean freeze;
    private TileFishTank tile;

    public GuiFishTank(InventoryPlayer player, TileFishTank tile) {
        super(new ContainerFishTank(tile, player), "fishtank", 52);

        this.tile = tile;
    }

    @Override
    public int getX() {
        return 22;
    }

    @Override
    protected void onMouseClick(int x, int y) {
        if (mouseX >= -18 && mouseX <= 2 && mouseY >= 104 && mouseY <= 124) {
            tile.thePage -= 1;
            if (tile.thePage < 0) {
                tile.thePage = tile.MAX_PAGES - 1;
            }

            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, tile.previous));
            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, ClientHelper.getPlayer().getEntityId()));
            ClientHelper.getPlayer().openGui(Mariculture.instance, -1, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        }

        if (mouseX >= 172 && mouseX <= 192 && mouseY >= 104 && mouseY <= 124) {
            tile.thePage += 1;
            if (tile.thePage >= tile.MAX_PAGES) {
                tile.thePage = 0;
            }

            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, tile.next));
            PacketHandler.sendToServer(new PacketClick(tile.xCoord, tile.yCoord, tile.zCoord, ClientHelper.getPlayer().getEntityId()));
            ClientHelper.getPlayer().openGui(Mariculture.instance, -1, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
        }
        
        if(mouseX >= 8 && mouseX <= 168 && mouseY >= 16 && mouseY <= 122) {
            ItemStack held = ClientHelper.getPlayer().inventory.getItemStack();
            if (held != null && held.getItem() instanceof ItemFishy) {
                //tile.addFish(held);
                System.out.println("SEND");
                PacketHandler.sendToServer(new PacketFishTankAdd(tile.xCoord, tile.yCoord, tile.zCoord, held.stackTagCompound, held.stackSize));
                ClientHelper.getPlayer().inventory.setItemStack(null);
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(!freeze) {
            for (FishTankData data : tile.getVisible()) {
                data.move();
            }
        }
    }

    @Override
    public void drawForeground() {
        fontRendererObj.drawString("Page: " + (tile.thePage + 1) + "/" + TileFishTank.MAX_PAGES, 100, ySize - 96 + 3, 4210752);
        fontRendererObj.drawString("Page: " + (tile.thePage + 1) + "/" + TileFishTank.MAX_PAGES, 100, nameHeight, 4210752);
        for (FishTankData data : tile.getVisible()) {
            ItemStack stack = data.make();

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            if (!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), mc.getTextureManager(), stack, RenderItem.getInstance().renderWithColor, RenderItem.getInstance().zLevel, 7 + data.x, 15 + data.y)) {
                RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, 7 + data.x, 15 + data.y);
                RenderItem.getInstance().renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, 7 + data.x, 15 + data.y, "" + stack.stackSize);
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }

        fontRendererObj.drawString("Search...", 100, -10, 0xFF000000);
    }

    @Override
    public void drawBackground(int x, int y) {
        mc.renderEngine.bindTexture(Feature.texture);
        drawTexturedModalRect(x - 18, y + 103, 176, 99, 21, 22);
        drawTexturedModalRect(x + 173, y + 103, 176, 122, 21, 22);
        drawTexturedModalRect(x - 18 + 3, y + 103 + 2, 54, 220, 18, 18);
        drawTexturedModalRect(x + 90, y - 17, 0, 115, 84, 17);
        drawTexturedModalRect(x + 95, y - 12, 81, 68, 74, 11);
        if (mouseX >= -18 && mouseX <= 2 && mouseY >= 104 && mouseY <= 124) {
            drawTexturedModalRect(x - 18 + 3, y + 103 + 2, 0, 220, 18, 18);
        }

        drawTexturedModalRect(x + 173, y + 103 + 2, 36, 220, 18, 18);
        if (mouseX >= 172 && mouseX <= 192 && mouseY >= 104 && mouseY <= 124) {
            drawTexturedModalRect(x + 173, y + 103 + 2, 18, 220, 18, 18);
        }

        GL11.glColor4f(0.8980392F, 0.6F, 1.0F, 1.0F);
        drawTexturedModalRect(x + 175, y + 16, 176, 122, 21, 22);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);

        GL11.glColor4f(0.0F, 0.5019608F, 1.0F, 1.0F);
        drawTexturedModalRect(x + 175, y + 41, 176, 122, 21, 22);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);

        mc.renderEngine.bindTexture(TEXTURE);
    }
}
