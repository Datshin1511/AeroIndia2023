package universal.appfactory.aeroindia2023.faqs

import android.app.Application
import android.content.ContentValues.TAG
import android.text.BoringLayout
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import retrofit2.create
import universal.appfactory.aeroindia2023.ApiClient
import universal.appfactory.aeroindia2023.ApiInterface

class FaqsRepository(application: Application) {

    private var faqDao: FaqDao
    private var faqs: LiveData<List<FAQsModel>>

    init {
        faqDao = application.let { FaqsDatabase.getDatabase(it).faqsDao() }
        faqs = faqDao.getfaqs()
        Log.d(TAG, "New instance created")
    }

    fun getAllFaqs(): LiveData<List<FAQsModel>> {
        return faqDao.getfaqs()
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadAllFaqs(reload: Boolean,userType : String) {
        if (reload) {
            faqDao.deleteAll()
            val faqApi = ApiClient.getInstance().create(ApiInterface::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val value = faqApi.getFaqs("Bearer 61b25a411a2dad66bb7b6ff145db3c2f",userType.toInt())
                    ?.awaitResponse()
                val data: List<FAQsModel>? = value?.body()?.data
                Log.d("Response :", data.toString())
                faqDao.insertAll(data)
            }
        }
    }

}
