package universal.appfactory.aeroindia2023.agendas

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


class AgendaRepository(application: Application) {

    private var agendaDao: AgendaDao
    private var agendas: LiveData<List<AgendaModel>>

    init {
        agendaDao = application.let { AgendaDatabase.getDatabase(it).agendaDao() }
        agendas = agendaDao.getAgendas()
        Log.d(TAG, "New instance created...")
    }


    fun getAllAgendas(): LiveData<List<AgendaModel>> {
        return agendaDao.getAgendas()
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadAllAgendas(reload: Boolean) {
        if(reload)
        {
            agendaDao.deleteAll()
            val agendaApi = ApiClient.getInstance().create(ApiInterface::class.java)

            // launching a new coroutine
            GlobalScope.launch(Dispatchers.IO) {
                val value = agendaApi.getAgendas("Bearer 61b25a411a2dad66bb7b6ff145db3c2f")
                    ?.awaitResponse()
                val data: List<AgendaModel>? = value?.body()?.data as List<AgendaModel>
                Log.d("Response: ", data.toString())
                agendaDao.insertAll(data)

            }
        }
    }

}