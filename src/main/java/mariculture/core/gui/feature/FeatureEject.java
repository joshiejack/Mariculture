package mariculture.core.gui.feature;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.network.PacketClick;
import mariculture.core.network.PacketHandler;
import mariculture.core.util.IEjectable;
import mariculture.core.util.Text;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class FeatureEject extends Feature {
	public IEjectable tile;
	public int x, y, z;
	
	public FeatureEject(IEjectable tile) {
		this.tile = tile;
		x = ((TileEntity)tile).xCoord;
		y = ((TileEntity)tile).yCoord;
		z = ((TileEntity)tile).zCoord;
	}
	
	public enum EjectSetting {
		NONE, ITEM, FLUID, ITEMNFLUID;

		public static EjectSetting readFromNBT(NBTTagCompound nbt) {
			return EjectSetting.values()[nbt.getByte("EjectSetting")];
		}

		public static void writeToNBT(NBTTagCompound nbt, EjectSetting setting) {
			nbt.setByte("EjectSetting", (byte) setting.ordinal());
		}

		//Mode = The mode that the machine is currently in, the TYPE is always ITEM or FLUID(the type of transfer ocurring)
		public static boolean canEject(EjectSetting mode, EjectSetting type) {
			switch(mode) {
				case NONE:  		return false;
				case ITEM: 			return type.equals(ITEM);
				case FLUID: 		return type.equals(FLUID);
				case ITEMNFLUID: 	return true;
				default: 			return false;
			}
		}
		
		public boolean canEject(EjectSetting type) {
			if(this == NONE)  		return false;
			if(this == ITEM)  		return type == ITEM;
			if(this == FLUID) 		return type == FLUID;
			if(this == ITEMNFLUID)  return true;
			return false;
		}
		
		public static EjectSetting toggle(EjectSetting block, EjectSetting setting) {
			switch(block) {
				case ITEM:  return setting == ITEM? NONE: ITEM;
				case FLUID: return setting == FLUID? NONE: FLUID;
				case ITEMNFLUID: {
					int i = setting.ordinal() + 1;
					if(i < EjectSetting.values().length) {
						return EjectSetting.values()[i];
					}
					
							return NONE;
				}
				default: 	return NONE;
			}
		}
	}
	
	@Override
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		if(mouseX >= 177 && mouseX <= 192 && mouseY >= 99 && mouseY <= 114) {
			tooltip.add(Text.AQUA + StatCollector.translateToLocal("ejectmode." + tile.getEjectSetting().toString().toLowerCase()));
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		if(mouseX >= 177 && mouseX <= 192 && mouseY >= 99 && mouseY <= 114) {
			PacketHandler.sendToServer(new PacketClick(x, y, z, Feature.EJECT));
		}
	}
	
	@Override
	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		super.draw(gui, x, y, mouseX, mouseY);
		
		int offsetY = (mouseX >= 177 && mouseX <= 192 && mouseY >= 99 && mouseY <= 114)? -18: 0;
		
		int color = 0x78AB46;
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		
		GL11.glColor4f(red, green, blue, 1.0F);
		gui.drawTexturedModalRect(x + 175, y + 96, 176, 122, 21, 22);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		
		switch (tile.getEjectSetting()) {
			case NONE:
				gui.drawTexturedModalRect(x + 177, y + 99, 179, 166 + offsetY, 16, 16);
				break;
			case ITEM:
				gui.drawTexturedModalRect(x + 177, y + 99, 239, 166 + offsetY, 16, 16);
				break;
			case FLUID:
				gui.drawTexturedModalRect(x + 177, y + 99, 219, 166 + offsetY, 16, 16);
				break;
			case ITEMNFLUID:
				gui.drawTexturedModalRect(x + 177, y + 99, 199, 166 + offsetY, 16, 16);
				break;
			default:
				gui.drawTexturedModalRect(x + 177, y + 99, 219, 166 + offsetY, 16, 16);
		}
	}
}
