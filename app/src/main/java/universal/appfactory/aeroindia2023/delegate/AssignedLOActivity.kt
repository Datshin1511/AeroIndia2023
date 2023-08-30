package universal.appfactory.aeroindia2023.delegate

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_assigned_loactivity.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import universal.appfactory.aeroindia2023.ApiClient
import universal.appfactory.aeroindia2023.ApiInterface
import universal.appfactory.aeroindia2023.R
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorAdapter
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorModel
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorResponse

class AssignedLOActivity : AppCompatActivity() {
    private lateinit var viewModel: DelegateViewModel
    private lateinit var data: ArrayList<DelegateModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assigned_loactivity)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar_layout)

        viewModel = ViewModelProvider(this)[DelegateViewModel::class.java]
        viewModel.init((this as AppCompatActivity).applicationContext as Application)

        fetchAssignedLoData()
//        viewModel.allDelegates.observe(this){
//            data = it as ArrayList<DelegateModel>
//            var item = data[0]
//            lo_name.text = item.getLiason_ooficer()
//            lo_gmail.text = item.getLO_email()
//            lo_contact_number.text  = item.getLO_mobile()
//            lo_organization.text = item.getLO_organisation()
//
//        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchAssignedLoData() {
        val delegateApi = ApiClient.getInstance().create(ApiInterface::class.java)

        GlobalScope.launch(Dispatchers.IO) {

            delegateApi.getDelegates("Bearer 61b25a411a2dad66bb7b6ff145db3c2f",1)?.enqueue(
                object : Callback<DelegateResponse?>{
                    override fun onResponse(
                        call: Call<DelegateResponse?>,
                        response: Response<DelegateResponse?>
                    ) {
                        data = response.body()?.data as ArrayList<DelegateModel>
                        var item = data[0]
                        lo_name.text = item.getLiason_ooficer()
                        lo_gmail.text = item.getLO_email()
                        lo_contact_number.text = item.getLO_mobile()
                        lo_organization.text = item.getLO_organisation()
                    }

                    override fun onFailure(call: Call<DelegateResponse?>, t: Throwable) {
                        Log.d("Delegate Failure Response: ", t.message.toString())
                    }

                }
            )

        }
    }
}