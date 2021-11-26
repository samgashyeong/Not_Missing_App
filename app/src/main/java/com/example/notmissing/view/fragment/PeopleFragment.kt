package com.example.notmissing.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notmissing.R
import com.example.notmissing.adapter.MissingPeopleAdapter
import com.example.notmissing.api.NotMissingServerClient
import com.example.notmissing.databinding.FragmentPeopleBinding
import com.example.notmissing.model.missingpeople.MissingPeopleModel
import com.example.notmissing.model.missingpeople.People
import com.example.notmissing.view.PeopleDetailActivity
import com.example.notmissing.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleFragment : Fragment() {

    private lateinit var binding : FragmentPeopleBinding
    private val vm : MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    private var indexInt : Int = 2
    private var isLoding : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false)

        binding.recycler.addOnScrollListener(object :  RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if(!isLoding){
                    if((recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition() == vm.missingPeopleList.value!!.size-1){
                        getData()
                        this@PeopleFragment.isLoding = true
                    }
                }
            }
        })
        val v : View = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.missingPeopleList.observe(requireActivity(), Observer {
            vm.missingPeopleList.value?.let { it1 -> setAdapter(it1) }
        })
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            NotMissingServerClient.getApiService().getMissingPeople(indexInt.toString()).enqueue(object : Callback<MissingPeopleModel>{
                override fun onResponse(
                    call: Call<MissingPeopleModel>,
                    response: Response<MissingPeopleModel>
                ) {
                    val result = response.body()
                    if(response.code() == 200){
                        requireActivity().runOnUiThread {
                            val lastIndex = vm.missingPeopleList.value!!.size-1
                            vm.missingPeopleList.value!!.removeAt(lastIndex)
                            binding.recycler.adapter!!.notifyItemRemoved(lastIndex)
                            if(result!!.list.isNotEmpty()){
                                val list =  result!!.list as ArrayList<People>
                                list.add(People(1, " ", " ",1," "," "," "," ",1,1," "," "," "," ",1," "," ",1, " "
                                ))
                                vm.missingPeopleList.value!!.addAll(list)
                                binding.recycler.adapter!!.notifyItemInserted(vm.missingPeopleList.value!!.size-1)
                                indexInt++
                                isLoding = false
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<MissingPeopleModel>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

    fun setAdapter(list : ArrayList<People>){
        binding.recycler.adapter = MissingPeopleAdapter(list){position: Int, data: People ->
            startActivity(Intent(activity, PeopleDetailActivity::class.java)
                .putExtra("peopleData", data))
        }
        binding.progress.visibility = View.INVISIBLE
    }
}