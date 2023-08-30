@file:Suppress("SameParameterValue")

package universal.appfactory.aeroindia2023

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_homepage.*
import universal.appfactory.aeroindia2023.agendas.AgendaActivity
import universal.appfactory.aeroindia2023.agendas.AgendaViewModel
import universal.appfactory.aeroindia2023.delegate.*
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorViewModel
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorsActivity
import universal.appfactory.aeroindia2023.faqs.FaqsViewModel
import universal.appfactory.aeroindia2023.faqs.QuestionsActivity
import universal.appfactory.aeroindia2023.liaison_officer.*
import universal.appfactory.aeroindia2023.liaison_officer.trail.TrailPageActivity
import universal.appfactory.aeroindia2023.liaison_officer.trail.trailhome.TrailViewModel
import universal.appfactory.aeroindia2023.products.ProductViewModel
import universal.appfactory.aeroindia2023.products.ProductsActivity
import universal.appfactory.aeroindia2023.speakers.SpeakerViewModel
import universal.appfactory.aeroindia2023.speakers.SpeakersActivity
import universal.appfactory.aeroindia2023.weather.WeatherActivity
import universal.appfactory.aeroindia2023.weather.WeatherViewModel
import universal.appfactory.aeroindia2023.agendas.*
import java.time.LocalDateTime

class HomepageActivity : AppCompatActivity() {

    private var backpress: Int = 0
    private var navigableBundle = Bundle()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var agendaViewModel: AgendaViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var speakerViewModel: SpeakerViewModel
    private lateinit var liaisonViewModel: LiaisonViewModel
    private lateinit var delegateViewModel: DelegateViewModel
    private lateinit var trailViewModel: TrailViewModel
    private lateinit var exhibitorViewModel: ExhibitorViewModel
    private lateinit var questionsViewModel: FaqsViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var viewModel: AgendaViewModel
    private lateinit var userType: String
    private lateinit var image: ImageView
    private lateinit var gridLayout1: GridLayout
    private lateinit var gridLayout2: GridLayout
    private lateinit var gridLayout3: GridLayout
    private lateinit var foreignKeyId : String

//    0 -> "Unknown role"
//    1 -> "Attendee"
//    2 -> "Delegates"
//    3 -> "Liaison officer"
//    4 -> "Washroom zonal manager"
//    5 -> "Washroom super manager"
//    6 -> "Exhibitor"
//    7 -> "Media"

    @SuppressLint("MissingInflatedId", "SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        Log.i("Homepage msg", "In homepage")

        supportActionBar?.hide()

        navigableBundle = intent.extras!!
        lateinit var intent: Intent

        sharedPreferences = getSharedPreferences("LocalUserData", MODE_PRIVATE)

        userType = navigableBundle.getString("userType", "0")
        foreignKeyId = navigableBundle.getString("foreignKeyId","0")

        setName()

        val hashMap : HashMap<String, Class<*>> = HashMap()

        loadAllActivities()

        Log.i("Homepage activity msg", "User type: $userType")

        //Dynamic gridLayout generated
        val viewPager = findViewById<ViewPager>(R.id.pager)

        val ll1: LinearLayout? = findViewById(R.id.gridLL1)
        val ll2: LinearLayout? = findViewById(R.id.gridLL2)

        gridLayout1 = GridLayout(this)
        gridLayout1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        gridLayout1.setPadding(0)
        gridLayout1.columnCount = 3
        gridLayout1.rowCount = 3

        gridLayout2 = GridLayout(this)
        gridLayout2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        gridLayout2.setPadding(0)
        gridLayout2.columnCount = 3
        gridLayout2.rowCount = 3

        gridLayout3 = GridLayout(this)
        gridLayout3.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        gridLayout3.setPadding(0)
        gridLayout3.columnCount = 3
        gridLayout3.rowCount = 3

        ll1?.addView(gridLayout1)
        ll2?.addView(gridLayout2)

        when(userType){
            "1" -> {

                hashMap["1"] = AgendaActivity::class.java
                hashMap["2"] = SpeakersActivity::class.java
                hashMap["3"] = DummyActivity::class.java
                hashMap["4"] = MapsActivity::class.java
                hashMap["5"] = ExhibitorsActivity::class.java
                hashMap["6"] = DummyActivity::class.java
                hashMap["7"] = ProductsActivity::class.java
                hashMap["8"] = QuestionsActivity::class.java
                hashMap["9"] = Feedback::class.java
                hashMap["10"] = WeatherActivity::class.java

                for (i in hashMap.keys) {
                    image = ImageView(this)
                    image.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    image.setPadding(spToPx(20F), spToPx(20F), spToPx(20F), spToPx(20F))
                    image.setImageResource(
                        resources.getIdentifier(
                            "gen_$i",
                            "drawable",
                            packageName
                        )
                    )
                    image.setOnClickListener {
                        backpress = 0
                        intent = Intent(applicationContext, hashMap[i])
                        intent.putExtras(navigableBundle)
                        this@HomepageActivity.startActivity(intent)
                    }
                    if(i.toInt() <= 9)
                        gridLayout1.addView(image)
                    else
                        gridLayout2.addView(image)
                }

                ll1?.addView(gridLayout1)
                ll2?.addView(gridLayout2)
            }

            "2" -> {

                ll1?.addView(gridLayout1)
                ll2?.addView(gridLayout2)

                hashMap["1"] = DelegateVehicleActivity::class.java
                hashMap["2"] = DelegateHotelActivity::class.java
                hashMap["3"] = DelegateTravelActivity::class.java
                hashMap["4"] = AssignedLOActivity::class.java
                hashMap["5"] = DummyActivity::class.java
                hashMap["6"] = AgendaActivity::class.java
                hashMap["7"] = SpeakersActivity::class.java
                hashMap["8"] = DummyActivity::class.java
                hashMap["9"] = MapsActivity::class.java
                hashMap["10"] = ExhibitorsActivity::class.java
                hashMap["12"] = ProductsActivity::class.java
                hashMap["13"] = QuestionsActivity::class.java
                hashMap["14"] = Feedback::class.java
                hashMap["15"] = WeatherActivity::class.java

                for (i in hashMap.keys) {
                    image = ImageView(this)
                    image.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    image.setPadding(spToPx(20F), spToPx(20F), spToPx(20F), spToPx(20F))
                    image.setOnClickListener {
                        backpress = 0
                        intent = Intent(applicationContext, hashMap[i])
                        intent.putExtras(navigableBundle)
                        this@HomepageActivity.startActivity(intent)
                    }
                    if (i.toInt() <= 5)
                        image.setImageResource(
                            resources.getIdentifier(
                                "do_$i",
                                "drawable",
                                packageName
                            )
                        )
                    else
                        image.setImageResource(
                            resources.getIdentifier(
                                "gen_" + (i.toInt() - 5).toString(),
                                "drawable",
                                packageName
                            )
                        )

                    if(i.toInt() <= 9)
                        gridLayout1.addView(image)
                    else
                        gridLayout2.addView(image)

                }
            }

            "3" -> {

                ll1?.addView(gridLayout1)
                ll2?.addView(gridLayout2)

                hashMap["1"] = VehicleActivity::class.java
                hashMap["2"] = HotelActivity::class.java
                hashMap["3"] = TravelActivity::class.java
                hashMap["4"] = DelegationActivity::class.java
                hashMap["5"] = TrailPageActivity::class.java
                hashMap["6"] = AgendaActivity::class.java
                hashMap["7"] = SpeakersActivity::class.java
                hashMap["8"] = DummyActivity::class.java
                hashMap["9"] = MapsActivity::class.java
                hashMap["10"] = ExhibitorsActivity::class.java
                hashMap["12"] = ProductsActivity::class.java
                hashMap["13"] = QuestionsActivity::class.java
                hashMap["14"] = Feedback::class.java
                hashMap["15"] = WeatherActivity::class.java

                for (i in hashMap.keys) {
                    image = ImageView(this)
                    image.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    image.setPadding(spToPx(20F), spToPx(20F), spToPx(20F), spToPx(20F))
                    image.setOnClickListener {
                        backpress = 0
                        intent = Intent(applicationContext, hashMap[i])
                        intent.putExtras(navigableBundle)
                        this@HomepageActivity.startActivity(intent)
                    }
                    if (i.toInt() <= 5)
                        image.setImageResource(
                            resources.getIdentifier(
                                "lo_$i",
                                "drawable",
                                packageName
                            )
                        )
                    else
                        image.setImageResource(
                            resources.getIdentifier(
                                "gen_" + (i.toInt() - 5).toString(),
                                "drawable",
                                packageName
                            )
                        )

                    if(i.toInt() <= 9)
                        gridLayout1.addView(image)
                    else
                        gridLayout2.addView(image)

                }
            }

            "4" -> {

                ll1?.addView(gridLayout1)
                ll2?.addView(gridLayout2)

                hashMap["1"] = AgendaActivity::class.java
                hashMap["2"] = SpeakersActivity::class.java
                hashMap["3"] = DummyActivity::class.java
                hashMap["4"] = MapsActivity::class.java
                hashMap["5"] = ExhibitorsActivity::class.java
                hashMap["6"] = DummyActivity::class.java
                hashMap["7"] = ProductsActivity::class.java
                hashMap["8"] = QuestionsActivity::class.java
                hashMap["9"] = ZonalManagerActivity::class.java
                hashMap["10"] = WeatherActivity::class.java

                for (i in hashMap.keys) {
                    image = ImageView(this)
                    image.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    image.setPadding(spToPx(20F), spToPx(20F), spToPx(20F), spToPx(20F))
                    image.setOnClickListener {
                        backpress = 0
                        intent = Intent(applicationContext, hashMap[i])
                        intent.putExtras(navigableBundle)
                        this@HomepageActivity.startActivity(intent)
                    }
                    if (i.toInt() != 9)
                        image.setImageResource(
                            resources.getIdentifier(
                                "gen_$i",
                                "drawable",
                                packageName
                            )
                        )
                    else
                        image.setImageResource(R.drawable.check_complaints)

                    if(i.toInt() <= 9)
                        gridLayout1.addView(image)
                    else
                        gridLayout2.addView(image)
                }
            }

            "5" -> {

                ll1?.addView(gridLayout1)
                ll2?.addView(gridLayout2)

                hashMap["1"] = AgendaActivity::class.java
                hashMap["2"] = SpeakersActivity::class.java
                hashMap["3"] = DummyActivity::class.java
                hashMap["4"] = MapsActivity::class.java
                hashMap["5"] = ExhibitorsActivity::class.java
                hashMap["6"] = DummyActivity::class.java
                hashMap["7"] = ProductsActivity::class.java
                hashMap["8"] = QuestionsActivity::class.java
                hashMap["9"] = ManagerActivity::class.java
                hashMap["10"] = WeatherActivity::class.java

                for (i in hashMap.keys) {
                    image = ImageView(this)
                    image.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    image.setPadding(spToPx(20F), spToPx(20F), spToPx(20F), spToPx(20F))
                    image.setOnClickListener {
                        backpress = 0
                        intent = Intent(applicationContext, hashMap[i])
                        intent.putExtras(navigableBundle)
                        this@HomepageActivity.startActivity(intent)
                    }
                    if (i.toInt() != 9)
                        image.setImageResource(
                            resources.getIdentifier(
                                "gen_$i",
                                "drawable",
                                packageName
                            )
                        )
                    else
                        image.setImageResource(R.drawable.check_complaints)

                    if(i.toInt() <= 9)
                        gridLayout1.addView(image)
                    else
                        gridLayout2.addView(image)
                }
            }

            else -> Log.i("Homepage Activity", "User type not defined")
        }

        viewPager.adapter = PageAdapter(supportFragmentManager, 2)

        findViewById<LinearLayout>(R.id.profileView).setOnClickListener{
            intent = Intent(this@HomepageActivity, ProfileActivity::class.java)
            intent.putExtras(navigableBundle)
            backpress = 0
            this.startActivity(intent)

            setName()
        }

        findViewById<ImageView>(R.id.refreshHomepage).setOnClickListener{
            findViewById<TextView>(R.id.userNameView).text = sharedPreferences.getString("name", "DEFAULT USER")

            agendaViewModel.loadAllAgendas(true)
            productViewModel.loadAllProducts(true)
            speakerViewModel.loadAllSpeakers(true)
            exhibitorViewModel.loadAllExhibitors(true)
            questionsViewModel.loadAllFaqs(true, userType)
            weatherViewModel.loadAllWeather(true)

            if(userType=="3"){
                liaisonViewModel.loadAllLiaisonOfficers(true,foreignKeyId.toInt())
            }
            if(userType=="2") {
                delegateViewModel.loadAllDelegates(true,foreignKeyId.toInt())
            }
            trailViewModel.loadAllTrail(true)

            Log.i("Homepage activity message", "Homepage refreshed")
            Toast.makeText(this, "Page refreshed", Toast.LENGTH_SHORT).show()
        }

        // Fetching data from AgendaModel
        var data: ArrayList<AgendaModel>
        viewModel = AgendaViewModel()
        viewModel.init(application)
        viewModel.allagenda.observe(this){
            data = it as ArrayList<AgendaModel>
            if(data.size != 0) {
                Log.i("Agenda time data: ", data[0].getStart_date_time())
                Log.i("Current time data: ", LocalDateTime.now().toString())
            }
        }

    }

    private fun loadAllActivities() {
        agendaViewModel = ViewModelProvider(this)[AgendaViewModel::class.java]
        agendaViewModel.init((this as AppCompatActivity).applicationContext as Application)
        agendaViewModel.loadAllAgendas(true)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productViewModel.init((this as AppCompatActivity).applicationContext as Application)
        productViewModel.loadAllProducts(true)

        speakerViewModel = ViewModelProvider(this)[SpeakerViewModel::class.java]
        speakerViewModel.init((this as AppCompatActivity).applicationContext as Application)
        speakerViewModel.loadAllSpeakers(true)

        exhibitorViewModel = ViewModelProvider(this)[ExhibitorViewModel::class.java]
        exhibitorViewModel.init((this as AppCompatActivity).applicationContext as Application)
        exhibitorViewModel.loadAllExhibitors(true)

        questionsViewModel = ViewModelProvider(this)[FaqsViewModel::class.java]
        questionsViewModel.init((this as AppCompatActivity).applicationContext as Application)
        questionsViewModel.loadAllFaqs(true,userType)

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        weatherViewModel.init((this as AppCompatActivity).applicationContext as Application)
        weatherViewModel.loadAllWeather(true)

        if(userType == "3" || userType =="2") {
            questionsViewModel = ViewModelProvider(this)[FaqsViewModel::class.java]
            questionsViewModel.init((this as AppCompatActivity).applicationContext as Application)
            questionsViewModel.loadAllFaqs(true,userType)
        }

        if(userType=="3"){
            liaisonViewModel = ViewModelProvider(this)[LiaisonViewModel::class.java]
            liaisonViewModel.init((this as AppCompatActivity).applicationContext as Application)
            liaisonViewModel.loadAllLiaisonOfficers(true,foreignKeyId.toInt())
        }

        if(userType=="2") {
            delegateViewModel = ViewModelProvider(this)[DelegateViewModel::class.java]
            delegateViewModel.init((this as AppCompatActivity).applicationContext as Application)
            delegateViewModel.loadAllDelegates(true,foreignKeyId.toInt())
        }

        trailViewModel = ViewModelProvider(this)[TrailViewModel::class.java]
        trailViewModel.init((this as AppCompatActivity).applicationContext as Application)
        trailViewModel.loadAllTrail(true)

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        backpress += 1
        if(backpress > 1)
            finishAffinity()
        else
            Toast.makeText(this, "Press back once again to exit", Toast.LENGTH_SHORT).show()
    }

    private fun setName(){
        findViewById<TextView>(R.id.userNameView).text = sharedPreferences.getString("name", "DEFAULT USER")
    }

    private fun spToPx(sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, applicationContext.resources.displayMetrics)
            .toInt()
    }
}

