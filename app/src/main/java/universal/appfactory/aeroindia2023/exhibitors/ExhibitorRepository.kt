package universal.appfactory.aeroindia2023.exhibitors

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import universal.appfactory.aeroindia2023.ApiClient
import universal.appfactory.aeroindia2023.ApiInterface


class ExhibitorRepository(application: Application) {

    private var exhibitorDao: ExhibitorDao
    private var exhibitors: LiveData<List<ExhibitorModel>>

    init {
        exhibitorDao = application.let { ExhibitorDatabase.getDatabase(it).exhibitorDao() }
        exhibitors = exhibitorDao.getExhibitors()
        Log.d(TAG, "New instance created...")
    }


    fun getAllExhibitors(): LiveData<List<ExhibitorModel>> {
        return exhibitorDao.getExhibitors()
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadAllExhibitors(reload: Boolean) {
        if (reload) {
            exhibitorDao.deleteAll()
            val exhibitorApi = ApiClient.getInstance().create(ApiInterface::class.java)

            // launching a new coroutine
            GlobalScope.launch(Dispatchers.IO) {
                val value = exhibitorApi.getExhibitors("Bearer 61b25a411a2dad66bb7b6ff145db3c2f")
                    ?.awaitResponse()
                val data: List<ExhibitorModel>? = value?.body()?.data
                Log.d("Response: ", data.toString())
                exhibitorDao.insertAll(data)

            }
        }
    }

}