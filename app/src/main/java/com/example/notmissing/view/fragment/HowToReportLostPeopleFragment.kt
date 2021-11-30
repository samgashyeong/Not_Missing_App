package com.example.notmissing.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notmissing.R
import com.example.notmissing.adapter.PostAdapter
import com.example.notmissing.databinding.FragmentHowToReportLostPeopleBinding
import com.example.notmissing.databinding.FragmentMapBinding
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem
import com.example.notmissing.viewmodel.MainViewModel

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
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_map, container, false)

        vm.postList.observe(viewLifecycleOwner, Observer {
            binding.recycler.adapter = PostAdapter(vm.postList.value!!){ data: ReportPostDataModelItem, position: Int ->

            }
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_how_to_report_lost_people, container, false)
    }
}