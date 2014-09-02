package joshie.mariculture.core.util;

import joshie.mariculture.core.gui.feature.FeatureNotifications.NotificationType;

public interface IHasNotification {
    public boolean isNotificationVisible(NotificationType type);
}
