package mariculture.magic.gui;

import java.util.Random;

import mariculture.core.Mariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.magic.EnchantPacket;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

public class GuiMirror extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/mirror.png");
	private int inventoryRows = 0;
	private final ContainerMirror containerMirror;
	private final boolean namesGenerated = false;
	private final boolean namesGenerated2 = false;
	private final boolean namesGenerated3 = false;

	private float field_74213_p;
	private float field_74212_q;

	private float field_74209_t;
	private float field_74208_u;
	private int[] enchantLevels = new int[3];

	private final InventoryMirror mirrorInventory;
	private final Random rand = new Random();

	public GuiMirror(IInventory playerInv, IInventory mirrorInv, World world) {
		super(new ContainerMirror(playerInv, mirrorInv, world));
		this.allowUserInput = false;
		final short var3 = 222;
		final int var4 = var3 - 110;
		this.inventoryRows = mirrorInv.getSizeInventory() / 9;
		this.ySize = var4 + this.inventoryRows * 18;
		mirrorInventory = (InventoryMirror) mirrorInv;
		this.containerMirror = (ContainerMirror) this.inventorySlots;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		if(mirrorInventory != null) {
			String name = mirrorInventory.getName();
	        this.fontRenderer.drawString(name, 66, 4, 4210752);
		}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		
		if(mirrorInventory.getStackInSlot(0) == null) {
			this.drawTexturedModalRect(x + 7, y + 12, 187, 12, 18, 18);
		}
		
		if(mirrorInventory.getStackInSlot(1) == null) {
			this.drawTexturedModalRect(x + 7, y + 33, 187, 33, 18, 18);
		}
		
		if(mirrorInventory.getStackInSlot(2) == null) {
			this.drawTexturedModalRect(x + 7, y + 55, 187, 55, 18, 18);
		}

		if (mirrorInventory.hasItemForEnchanting()) {
			GL11.glPushMatrix();
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glPushMatrix();
			GL11.glLoadIdentity();
			final ScaledResolution var7 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			GL11.glViewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(),
					(var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(),
					240 * var7.getScaleFactor());
			GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
			GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
			float var8 = 1.0F;
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			RenderHelper.enableStandardItemLighting();
			GL11.glTranslatef(0.0F, 3.3F, -16.0F);
			GL11.glScalef(var8, var8, var8);
			float var9 = 5.0F;
			GL11.glScalef(var9, var9, var9);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			float var10 = this.field_74208_u + (this.field_74209_t - this.field_74208_u) * f;
			GL11.glTranslatef((1.0F - var10) * 0.2F, (1.0F - var10) * 0.1F, (1.0F - var10) * 0.25F);
			GL11.glRotatef(-(1.0F - var10) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			float var11 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * f + 0.25F;
			float var12 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * f + 0.75F;
			var11 = (var11 - MathHelper.truncateDoubleToInt(var11)) * 1.6F - 0.3F;
			var12 = (var12 - MathHelper.truncateDoubleToInt(var12)) * 1.6F - 0.3F;

			if (var11 < 0.0F) {
				var11 = 0.0F;
			}

			if (var12 < 0.0F) {
				var12 = 0.0F;
			}

			if (var11 > 1.0F) {
				var11 = 1.0F;
			}

			if (var12 > 1.0F) {
				var12 = 1.0F;
			}

			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPopMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.renderEngine.bindTexture(TEXTURE);
			EnchantmentNameParts.instance.setRandSeed(mirrorInventory.nameSeed);

			if (mirrorInventory.isMagical()) {
				enchantLevels[0] = mirrorInventory.lvl1;
				enchantLevels[1] = mirrorInventory.lvl2;
				enchantLevels[2] = mirrorInventory.lvl3;
			} else {
				enchantLevels[0] = 1;
				enchantLevels[1] = 3;
				enchantLevels[2] = 5;
			}

			for (int k = 0; k < 3; ++k) {
				final String randString = EnchantmentNameParts.instance.generateRandomEnchantName();
				this.zLevel = 0.0F;
				this.mc.renderEngine.bindTexture(TEXTURE);
				final int level = enchantLevels[k];
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				if (level == 0) {
					this.drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 185, 108, 19);
				} else {
					final String lvlString = "" + level;
					FontRenderer fontRenderer = this.mc.standardGalacticFontRenderer;
					int var18 = 6839882;

					if (this.mc.thePlayer.experienceLevel < level && !this.mc.thePlayer.capabilities.isCreativeMode) {
						this.drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 185, 108, 19);
						fontRenderer.drawSplitString(randString, x + 62, y + 16 + 19 * k, 104, (var18 & 16711422) >> 1);
						fontRenderer = this.mc.fontRenderer;
						var18 = 4226832;
						fontRenderer.drawStringWithShadow(lvlString, x + 62 + 104 - fontRenderer.getStringWidth(lvlString), y
								+ 16 + 19 * k + 7, var18);
					} else {
						final int var19 = i - (x + 60);
						final int var20 = j - (y + 14 + 19 * k);

						if (var19 >= 0 && var20 >= 0 && var19 < 108 && var20 < 19) {
							this.drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 204, 108, 19);
							var18 = 16777088;
						} else {
							this.drawTexturedModalRect(x + 60, y + 14 + 19 * k, 0, 166, 108, 19);
						}

						fontRenderer.drawSplitString(randString, x + 62, y + 16 + 19 * k, 104, var18);
						fontRenderer = this.mc.fontRenderer;
						var18 = 8453920;
						fontRenderer.drawStringWithShadow(lvlString, x + 62 + 104 - fontRenderer.getStringWidth(lvlString), y
								+ 16 + 19 * k + 7, var18);
					}
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		if (mirrorInventory.hasItemForEnchanting()) {
			final int var4 = (this.width - this.xSize) / 2;
			final int var5 = (this.height - this.ySize) / 2;

			for (int var6 = 0; var6 < 3; ++var6) {
				final int var7 = par1 - (var4 + 60);
				final int var8 = par2 - (var5 + 14 + 19 * var6);

				if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19
						&& containerMirror.enchantItem(this.mc.thePlayer, (var6) + 1)) {
					final int level = enchantLevels[var6];
					EnchantPacket.dispatchPacket(mc.thePlayer, mc.thePlayer.openContainer.windowId, level);
				}
			}
		}
	}
}
