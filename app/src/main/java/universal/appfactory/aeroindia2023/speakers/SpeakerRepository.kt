package universal.appfactory.aeroindia2023.speakers

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


class SpeakerRepository(application: Application) {

    private var speakerDao: SpeakerDao
    private var speakers: LiveData<List<SpeakerModel>>

    init {
        speakerDao = application.let { SpeakerDatabase.getDatabase(it).speakerDao() }
        speakers = speakerDao.getSpeakers()
        Log.d(TAG, "New instance created...")
    }


    fun getAllSpeakers(): LiveData<List<SpeakerModel>> {
        return speakerDao.getSpeakers()
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadAllSpeakers(reload: Boolean) {
        if(reload)
        {
            speakerDao.deleteAll()
            val speakerApi = ApiClient.getInstance().create(ApiInterface::class.java)

            // launching a new coroutine
            GlobalScope.launch(Dispatchers.IO) {
                val value = speakerApi.getSpeakers("Bearer 61b25a411a2dad66bb7b6ff145db3c2f")
                    ?.awaitResponse()
                val data: List<SpeakerModel>? = value?.body()?.data
                Log.d("Response: ", data.toString())
                speakerDao.insertAll(data)
            }
        }
    }

}