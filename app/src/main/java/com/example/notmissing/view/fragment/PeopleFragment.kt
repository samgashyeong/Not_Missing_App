package com.example.notmissing.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.notmissing.R
import com.example.notmissing.adapter.MissingPeopleAdapter
import com.example.notmissing.api.NotMissingServerClient
import com.example.notmissing.databinding.FragmentPeopleBinding
import com.example.notmissing.model.missingpeople.MissingPeopleModel
import com.example.notmissing.model.missingpeople.People
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleFragment : Fragment() {

    private lateinit var binding : FragmentPeopleBinding
    private var list : ArrayList<People>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false)
        val v : View = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(list == null){
            getData()
        }
        else{
            setAdapter(list!!)
        }
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            NotMissingServerClient.getApiService().getMissingPeople("1").enqueue(object : Callback<MissingPeopleModel>{
                override fun onResponse(
                    call: Call<MissingPeopleModel>,
                    response: Response<MissingPeopleModel>
                ) {
                    val result = response.body()
                    if(response.code() == 200){
                        requireActivity().runOnUiThread {
                            list = result!!.list as ArrayList<People>
                            setAdapter(list!!)
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
        binding.recycler.adapter = MissingPeopleAdapter(list)
        binding.progress.visibility = View.INVISIBLE
    }
}