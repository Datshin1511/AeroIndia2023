package universal.appfactory.aeroindia2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SpeakersActivity : AppCompatActivity() {

    // variable for our adapter
    // class and array list
    private lateinit var adapter: SpeakersAdapter
    private lateinit var data: ArrayList<SpeakerModel>
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speakers)

        supportActionBar?.hide()

        // getting the recyclerview by its id
        recyclerview = findViewById(R.id.recycler_view)
        // getting searchview by its id
        val searchView = findViewById<SearchView>(R.id.search_bar)
        val refreshView = findViewById<SwipeRefreshLayout>(R.id.refreshLayout)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        data = ArrayList()
        fetchSpeakerData()

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })

        refreshView.setOnRefreshListener{
            fetchSpeakerData()
            refreshView.isRefreshing = false
        }
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList = ArrayList<SpeakerModel>()

        // running a for loop to compare elements.
        for (item in data) {
            // checking if the entered string matched with any item of our recycler view.
            val name = item.getFirstName() + " " + item.getLastName()
            if (name.lowercase(Locale.ROOT).contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }

        adapter.filterList(filteredList)
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun fetchSpeakerData () {
        val speakerApi = ApiClient.getInstance().create(ApiInterface::class.java)

        // launching a new coroutine
        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            speakerApi.getSpeakers("Bearer 61b25a411a2dad66bb7b6ff145db3c2f")?.enqueue(object :
                Callback<SpeakerResponse?> {
                override fun onResponse(
                    call: Call<SpeakerResponse?>,
                    response: Response<SpeakerResponse?>
                ) {

                    Log.d("Response: ", response.body().toString())
                    data = response.body()?.data as ArrayList<SpeakerModel>
                    // This will pass the ArrayList to our Adapter
                    adapter = SpeakersAdapter(data, this@SpeakersActivity)
                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter

                }

                override fun onFailure(call: Call<SpeakerResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message,
                        Toast.LENGTH_SHORT).show()
                    Log.d("Failure Response: ", t.message.toString())
                }
            })


        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }
}