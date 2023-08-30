package universal.appfactory.aeroindia2023

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

// Main Activity
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if(isOnline(this@MainActivity)){
                    Log.i("Internet connections", "Valid and fine to start")

                    // Starts Login Activity
                    val intent = Intent(this@MainActivity, UserLoginActivity::class.java)
                    startActivity(intent)
                    this@MainActivity.finish()
                }
                else{
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("ERROR !")
                    builder.setMessage("You need an internet connection to get started. Kindly connect to an internet connection.")
                    builder.setNeutralButton("OK") { dialog, which ->
                        this@MainActivity.finishAffinity()
                    }
                    builder.create().show()

                    Log.i("Internet connections", "Connections invalid. Need an internet connection to proceed")
                }
            }
        }
        timer.start()
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

}


