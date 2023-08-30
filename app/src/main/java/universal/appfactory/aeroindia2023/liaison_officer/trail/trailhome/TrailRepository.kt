package universal.appfactory.aeroindia2023.liaison_officer.trail.trailhome

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import universal.appfactory.aeroindia2023.ApiClient
import universal.appfactory.aeroindia2023.ApiInterface

class TrailRepository(application: Application) {
    private var trailDao : TrailDao
    private var trail : LiveData<List<TrailModel>>

    init {
        trailDao = application.let { TrailDatabase.getDatabase(it).trailDao() }
        trail = trailDao.getTrail()
        Log.d(ContentValues.TAG, "New instance created")
    }

    fun getAllTrail(): LiveData<List<TrailModel>> {
        return trailDao.getTrail()
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadAllTrail(reload: Boolean,id : Int = 1) {
        if (reload) {
            trailDao.deleteAll()
            val liaisonApi = ApiClient.getInstance().create(ApiInterface::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val value = liaisonApi.getTrail("Bearer 61b25a411a2dad66bb7b6ff145db3c2f")
                    ?.awaitResponse()
                val data: List<TrailModel>? = value?.body()?.data
                Log.d("Response :", data.toString())
                trailDao.insertAll(data)
            }
        }
    }
}