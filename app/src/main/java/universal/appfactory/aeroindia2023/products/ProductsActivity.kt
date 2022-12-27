package universal.appfactory.aeroindia2023.products

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import universal.appfactory.aeroindia2023.ApiClient
import universal.appfactory.aeroindia2023.ApiInterface
import universal.appfactory.aeroindia2023.R
import java.util.*
import kotlin.collections.ArrayList

class ProductsActivity : AppCompatActivity() {

    // variable for our adapter
    // class and array list
    private lateinit var adapter: ProductAdapter
    private lateinit var data: ArrayList<ProductModel>
    private lateinit var recyclerview: RecyclerView
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setCustomView(R.layout.action_bar_layout)

        // getting the recyclerview by its id
        recyclerview = findViewById(R.id.recycler_view)
        // getting searchview by its id
        val searchView = findViewById<SearchView>(R.id.search_bar)
        val refreshView = findViewById<SwipeRefreshLayout>(R.id.refreshLayout)
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        viewModel.init((this as AppCompatActivity).applicationContext as Application)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        data = ArrayList()

        viewModel.allproduct.observe(this) {
            data = it as ArrayList<ProductModel>
            // This will pass the ArrayList to our Adapter
            adapter = ProductAdapter(data,this@ProductsActivity)
            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter
        }


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
            viewModel.loadAllProducts(true)
            refreshView.isRefreshing = false
        }
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList = ArrayList<ProductModel>()

        // running a for loop to compare elements.
        for (item in data) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getProduct_title().lowercase(Locale.ROOT).contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }

        adapter.filterList(filteredList)
    }
}