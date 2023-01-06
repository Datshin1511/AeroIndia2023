package universal.appfactory.aeroindia2023.liaison_officer.trail

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_delegate.view.*
import kotlinx.android.synthetic.main.fragment_trail.view.*
import universal.appfactory.aeroindia2023.R
import universal.appfactory.aeroindia2023.liaison_officer.DelegationAdapter
import universal.appfactory.aeroindia2023.liaison_officer.LiaisonModel
import universal.appfactory.aeroindia2023.liaison_officer.LiaisonViewModel

class TrailFragment : Fragment() {


    private lateinit var adapter: TrailAdapter
    private lateinit var data : ArrayList<TrailModel>
    private lateinit var viewModel: TrailViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[TrailViewModel::class.java]
        viewModel.init((this as AppCompatActivity).applicationContext as Application)

        val binding =  inflater.inflate(R.layout.fragment_trail, container, false)
        recyclerView = binding.trail_recycler
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        data = ArrayList()

        viewModel.allTrail.observe(viewLifecycleOwner){
            data = it as ArrayList<TrailModel>
            adapter = TrailAdapter(data)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        return binding.rootView
    }


}