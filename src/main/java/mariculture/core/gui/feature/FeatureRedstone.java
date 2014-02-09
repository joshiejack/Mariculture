package mariculture.core.gui.feature;

import java.util.List;

import org.lwjgl.opengl.GL11;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.lib.Text;
import mariculture.core.network.Packet114RedstoneControlled;
import mariculture.core.util.IRedstoneControlled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class FeatureRedstone extends Feature {
	public IRedstoneControlled tile;
	public int x, y, z;
	
	public FeatureRedstone(IRedstoneControlled tile) {
		this.tile = tile;
		x = ((TileEntity)tile).xCoord;
		y = ((TileEntity)tile).yCoord;
		z = ((TileEntity)tile).zCoord;
	}
	
	public enum RedstoneMode {
		DISABLED, LOW, HIGH;

		public static RedstoneMode readFromNBT(NBTTagCompound nbt) {
			return RedstoneMode.values()[nbt.getByte("RedstoneMode")];
		}

		public static void writeToNBT(NBTTagCompound nbt, RedstoneMode mode) {
			nbt.setByte("RedstoneMode", (byte) mode.ordinal());
		}

		public static boolean canWork(TileEntity tile, RedstoneMode mode) {
			switch(mode) {
			case DISABLED: 
				return true;
			case LOW:
				return !tile.worldObj.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord);
			case HIGH:
				return tile.worldObj.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord);
			default:
				return !tile.worldObj.isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord);
			}
		}

		public static RedstoneMode toggle(RedstoneMode mode) {
			switch(mode) {
				case DISABLED:
					return LOW;
				case LOW:
					return HIGH;
				case HIGH:
					return DISABLED;
			}
			
			return mode;
		}
	}
	
	@Override
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		if(mouseX >= 177 && mouseX <= 192 && mouseY >= 76 && mouseY <= 92) {
			tooltip.add(Text.RED + StatCollector.translateToLocal("rsmode." + tile.getRSMode().toString().toLowerCase()));
			for(int i = 0; i < 3; i++) {
				addLine("rsmode", i, tile.getRSMode().toString(), tooltip);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		if(mouseX >= 177 && mouseX <= 192 && mouseY >= 76 && mouseY <= 92) {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new Packet114RedstoneControlled(x, y, z).build());
		}
	}
	
	@Override
	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		super.draw(gui, x, y, mouseX, mouseY);
		
		int offsetY = (mouseX >= 177 && mouseX <= 192 && mouseY >= 76 && mouseY <= 92)? -18: 0;
		
		int color = 0xA62A2A;
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		
		GL11.glColor4f(red, green, blue, 1.0F);
		gui.drawTexturedModalRect(x + 175, y + 73, 176, 122, 21, 22);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		
		switch (tile.getRSMode()) {
			case DISABLED:
				gui.drawTexturedModalRect(x + 177, y + 76, 199, 125 + offsetY, 16, 16);
				break;
			case LOW:
				gui.drawTexturedModalRect(x + 177, y + 76, 219, 125 + offsetY, 16, 16);
				break;
			case HIGH:
				gui.drawTexturedModalRect(x + 177, y + 76, 239, 125 + offsetY, 16, 16);
				break;
			default:
				gui.drawTexturedModalRect(x + 177, y + 76, 219, 125 + offsetY, 16, 16);
		}
	}
}
