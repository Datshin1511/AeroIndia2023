package universal.appfactory.aeroindia2023.Notifications

import android.content.Context
import android.util.Log
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal.OSRemoteNotificationReceivedHandler

class NotificationServiceExtension : OSRemoteNotificationReceivedHandler {
    override fun remoteNotificationReceived(
        context: Context,
        notificationReceivedEvent: OSNotificationReceivedEvent
    ) {
        val notification = notificationReceivedEvent.notification

        val data = notification.additionalData
        Log.e("OneSignalExample", "Received Notification Data: $data")

        notificationReceivedEvent.complete(notification)
    }
}