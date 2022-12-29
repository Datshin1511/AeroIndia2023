package universal.appfactory.aeroindia2023

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_profile_info.*
import universal.appfactory.aeroindia2023.agendas.AgendaActivity
import universal.appfactory.aeroindia2023.agendas.AgendaSortActivity
import universal.appfactory.aeroindia2023.agendas.AgendaViewModel
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorsActivity
import universal.appfactory.aeroindia2023.products.ProductViewModel
import universal.appfactory.aeroindia2023.products.ProductsActivity
import universal.appfactory.aeroindia2023.speakers.SpeakerViewModel
import universal.appfactory.aeroindia2023.speakers.SpeakersActivity


class HomepageActivity : AppCompatActivity() {

    private var backpress: Int = 0
    var navigableBundle = Bundle()
    private lateinit var agendaViewModel: AgendaViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var speakerViewModel: SpeakerViewModel
    private lateinit var userId: String


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        supportActionBar?.hide()

        navigableBundle = intent.extras!!
        userId = navigableBundle.getString("userId", "0")
        findViewById<TextView>(R.id.userNameView).text = navigableBundle.getString("name", "DEFAULT USER")

        Log.i("Homepage activity msg", "User ID: $userId")

        if(userId == "4")
            findViewById<ImageView>(R.id.complaint).setImageResource(R.drawable.icon_manager)
        else if(userId =="5")
            findViewById<ImageView>(R.id.complaint).setImageResource(R.drawable.icon_manager)
        else
            Log.i("Homepage activity msg", "No icon changed")

        agendaViewModel = ViewModelProvider(this)[AgendaViewModel::class.java]
        agendaViewModel.init((this as AppCompatActivity).applicationContext as Application)
        agendaViewModel.loadAllAgendas(true)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productViewModel.init((this as AppCompatActivity).applicationContext as Application)
        productViewModel.loadAllProducts(true)

        speakerViewModel = ViewModelProvider(this)[SpeakerViewModel::class.java]
        speakerViewModel.init((this as AppCompatActivity).applicationContext as Application)
        speakerViewModel.loadAllSpeakers(true)
    }

    fun iconClicked(view: View) {
        val tag = view.tag.toString()
        Log.i("Clicked Button tag", tag)

        var navigateIntent = Intent(this@HomepageActivity,  DummyActivity::class.java)

        when(tag.toInt()){
            0 -> {navigateIntent = Intent(this@HomepageActivity, AgendaActivity::class.java)
                    backpress=0}       // Agenda
            1 -> {navigateIntent = Intent(this@HomepageActivity, SpeakersActivity::class.java)
                    backpress=0}     // Speakers
            2 -> {navigateIntent = Intent(this@HomepageActivity, DummyActivity::class.java)
                    backpress=0}        // Venue maps
            3 -> {navigateIntent = Intent(this@HomepageActivity, MapsActivity::class.java)
                    backpress=0}         // Driving directions
            4 -> {navigateIntent = Intent(this@HomepageActivity, ExhibitorsActivity::class.java)
                    backpress=0}   // Resources
            5 -> {navigateIntent = Intent(this@HomepageActivity, DummyActivity::class.java)
                    backpress=0}      // Videos
            6 -> {navigateIntent = Intent(this@HomepageActivity, ProductsActivity::class.java)
                    backpress=0}     // Twitter or products
            7 -> {navigateIntent = Intent(this@HomepageActivity, AgendaSortActivity::class.java)
                    backpress=0}       // FAQ
            8 -> {
                if(userId == "4"){
                    navigateIntent = Intent(this@HomepageActivity, ManagerActivity::class.java) // Viewing complaints 1
                }
                else if(userId =="5"){
                    navigateIntent = Intent(this@HomepageActivity, ManagerActivity::class.java) // Viewing complaints 2
                }
                else
                    navigateIntent = Intent(this@HomepageActivity, Feedback::class.java) // Lodging complaints

                backpress=0
                }

            9 -> {navigateIntent = Intent(this@HomepageActivity, ProfileActivity::class.java)
                    backpress=0}      // Profile view
            else -> {
                Log.i("Homepage msg", "Nothing was clicked")
            }
        }

        navigateIntent.putExtras(navigableBundle)
        startActivity(navigateIntent)

    }

    fun refreshPage(view: View) {
        agendaViewModel.loadAllAgendas(true)
        productViewModel.loadAllProducts(true)
        speakerViewModel.loadAllSpeakers(true)

        Log.i("Homepage activity message", "Home page refreshed")
        Toast.makeText(this, "Page refreshed", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed(){
        backpress += 1
        if(backpress > 1){
            finishAffinity()
        }
        else{
            Toast.makeText(this, "Press back once again to exit", Toast.LENGTH_SHORT).show()
        }
    }
}