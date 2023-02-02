package universal.appfactory.aeroindia2023.agendas

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AgendaViewModel : ViewModel() {

    lateinit var allagenda: LiveData<List<AgendaModel>>
    private lateinit var agendaDao: AgendaDao
    private lateinit var repository: AgendaRepository

    fun init(app: Application) {
        if (::allagenda.isInitialized) return
        agendaDao = AgendaDatabase.getDatabase(app).agendaDao()
        repository = AgendaRepository(app)
        allagenda = repository.getAllAgendas()
    }

    fun loadAllAgendas(reload: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadAllAgendas(reload)
        }
    }

}