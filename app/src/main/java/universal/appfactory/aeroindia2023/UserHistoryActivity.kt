package universal.appfactory.aeroindia2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserHistoryActivity : AppCompatActivity() {
    private lateinit var adapter: UserHistoryAdapter
    private lateinit var data: ArrayList<UserHistoryModel>
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_history)
        recyclerview = findViewById<RecyclerView>(R.id.recycler_view)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        data = ArrayList()
        fetchPData()   }


    @OptIn(DelicateCoroutinesApi::class)
    fun fetchPData () {
        val UApi = ApiClient.getInstance().create(ApiInterface::class.java)

        // launching a new coroutine
        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            UApi.gethistory("Bearer 61b25a411a2dad66bb7b6ff145db3c2f")?.enqueue(object :
                Callback<UserHistoryResponse?> {
                override fun onResponse(
                    call: Call<UserHistoryResponse?>,
                    response: Response<UserHistoryResponse?>,
                ) {

                    Log.d("Response: ", response.body().toString())
                    data = response.body()?.data as ArrayList<UserHistoryModel>
                    // This will pass the ArrayList to our Adapter
                    adapter = UserHistoryAdapter(data)
                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter

                }

                override fun onFailure(call: Call<UserHistoryResponse?>, t: Throwable) {
                    Toast.makeText(
                        applicationContext, t.message,
                        Toast.LENGTH_SHORT,
                    ).show()
                    Log.d("Failure Response: ", t.message.toString())
                }
            },
            )


        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }
}