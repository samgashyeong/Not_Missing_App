package com.example.notmissing.view.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notmissing.R
import com.example.notmissing.adapter.PostAdapter
import com.example.notmissing.api.NotMissingServerClient
import com.example.notmissing.databinding.FragmentHowToReportLostPeopleBinding
import com.example.notmissing.databinding.FragmentMapBinding
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem
import com.example.notmissing.view.PeopleDetailActivity
import com.example.notmissing.view.PostAddActivity
import com.example.notmissing.view.PostDetailActivity
import com.example.notmissing.viewmodel.MainViewModel
import com.google.gson.internal.bind.MapTypeAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HowToReportLostPeopleFragment : Fragment() {

    private val vm : MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private lateinit var binding: FragmentHowToReportLostPeopleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_how_to_report_lost_people, container, false)
        vm.postList.observe(viewLifecycleOwner, {
            binding.recycler1.adapter = PostAdapter(vm.postList.value!!){ data: ReportPostDataModelItem, position: Int ->
                startActivity(Intent(requireActivity(), PostDetailActivity::class.java)
                    .putExtra("data", data))
            }
            Log.d(TAG, "onCreateView: ${vm.postList.value}")
        })

        binding.postAddBtn.setOnClickListener {
            requireActivity().startActivityForResult(Intent(requireActivity(), PostAddActivity::class.java), 100)
        }
        return binding.root
    }
}