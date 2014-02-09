package mariculture.core.util;

import mariculture.core.gui.feature.FeatureNotifications.NotificationType;

public interface IHasNotification {
	public boolean isNotificationVisible(NotificationType type);
}
