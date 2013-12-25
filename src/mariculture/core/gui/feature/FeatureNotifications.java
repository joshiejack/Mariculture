package mariculture.core.gui.feature;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.util.IHasNotification;

public class FeatureNotifications extends Feature {
	public IHasNotification tile;
	public NotificationType[] notifications;
	
	public FeatureNotifications(IHasNotification tile, NotificationType[] noteTypes) {
		this.tile = tile;
		this.notifications = noteTypes;
	}
	
	@Override
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		int pos = 0;
		for(NotificationType note: notifications) {
			if(tile.isNotificationVisible(note)) {
				if (mouseX >= -21 && mouseX <= 0 && mouseY >= 8 + (23 * pos) && mouseY <= 8 + (23 * pos) + 21) {
					tooltip.add(StatCollector.translateToLocal("mariculture.string.notification." + note.toString().toLowerCase().replaceAll("_", "\\.")));
				}
				
				pos++;
			}
		}
	}

	@Override
	public void draw(GuiMariculture gui, int x, int y) {
		int pos = 0;
		for(NotificationType note: notifications) {
			if(tile.isNotificationVisible(note)) {
				float red = (note.color >> 16 & 255) / 255.0F;
				float green = (note.color >> 8 & 255) / 255.0F;
				float blue = (note.color & 255) / 255.0F;
				
				GL11.glColor4f(red, green, blue, 1.0F);
				gui.drawTexturedModalRect(x - 21, y + 8 + (23 * pos), 176, 99, 21, 22);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
				
				gui.drawTexturedModalRect(x - 21 + 3, y + 8 + 2 + (23 * pos), note.x, note.y, 18, 18);
				
				
				pos++;
			}
		}
	}
	
	public static enum NotificationType {
		NO_ROD(0, 238, 0xA62A2A), 
		NO_BAIT(18, 238, 0x8B6969),
		NO_WATER(36, 238, 0x00CED1), 
		NOT_FISHABLE(54, 238, 0x00B2EE),
		NO_RF(72, 238, 0xC1CDCD);
		
		private int x;
		private int y;
		private int color;
		
		private NotificationType(int x, int y, int color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}
}
