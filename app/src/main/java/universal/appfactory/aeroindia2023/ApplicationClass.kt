package universal.appfactory.aeroindia2023

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.util.Log
import com.onesignal.OneSignal


const val ONESIGNAL_APP_ID = "1c53bac3-8c5c-4c3a-9cd2-38558dced0cf"

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        OneSignal.setNotificationWillShowInForegroundHandler {
            Log.e("OneSignal onHold msg", "Title: " + it.notification.title.toString())
            Log.e("OneSignal onHold msg", "Body: " + it.notification.body.toString())
        }

        OneSignal.OSRemoteNotificationReceivedHandler { _, it ->
            Log.e("OneSignal onSleep msg", "Title: " + it.notification.title.toString())
            Log.e("OneSignal onSleep msg", "Body: " + it.notification.body.toString())
        }

        // OneSignal Initialization
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        OneSignal.initWithContext(this@ApplicationClass)
    }
}


