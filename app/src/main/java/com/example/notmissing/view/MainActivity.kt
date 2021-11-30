package com.example.notmissing.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.notmissing.R
import com.example.notmissing.api.NotMissingServerClient
import com.example.notmissing.model.missingpeople.People
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem
import com.example.notmissing.model.safespot.Spot
import com.example.notmissing.view.fragment.HowToReportLostPeopleFragment
import com.example.notmissing.view.fragment.MapFragment
import com.example.notmissing.view.fragment.PeopleFragment
import com.example.notmissing.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val mainFragment1 by lazy { HowToReportLostPeopleFragment() }
    private val mainFragment3 by lazy { MapFragment() }
    private val mainFragment2 by lazy { PeopleFragment() }
    private val vm : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment(mainFragment1)

        vm.missingPeopleList.value = intent.getSerializableExtra("peopleList") as ArrayList<People>
        vm.mapList.value = intent.getSerializableExtra("mapList") as ArrayList<Spot>
        vm.postList.value = intent.getSerializableExtra("postList") as ArrayList<ReportPostDataModelItem>


        findViewById<BottomNavigationView>(R.id.bottomNavigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map -> {
                    changeFragment(mainFragment3)
                    true
                }
                R.id.lostPeople -> {
                    changeFragment(mainFragment2)
                    true
                }
                R.id.reportLostPeople ->{
                    changeFragment(mainFragment1)
                    true
                }
                else -> {
                    changeFragment(mainFragment2)
                    false
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    Log.d("ㅁㄴㅇㄹ", "onActivityResult: 클럭됨")
                    getPostData()
                }
            }
        }
    }

    private fun getPostData(){
        CoroutineScope(Dispatchers.IO).launch {
            NotMissingServerClient.getApiService().getReportPostData().enqueue(object : Callback<ReportPostDataModel>{
                override fun onResponse(
                    call: Call<ReportPostDataModel>,
                    response: Response<ReportPostDataModel>
                ) {
                    if(response.isSuccessful){
                        vm.postList.value = response.body()!!
                        vm.postList.value!!.reverse()
                    }
                }

                override fun onFailure(call: Call<ReportPostDataModel>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }


    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, fragment).commit()
    }

}