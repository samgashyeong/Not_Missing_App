package com.example.notmissing.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.notmissing.R
import com.example.notmissing.api.NotMissingServerClient
import com.example.notmissing.model.missingpeople.MissingPeopleModel
import com.example.notmissing.model.missingpeople.People
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.safespot.SafeSpotModel
import com.example.notmissing.model.safespot.Spot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InitActivity : AppCompatActivity() {
    private lateinit var peopleList : ArrayList<People>
    private lateinit var mapList : ArrayList<Spot>
    private lateinit var postList : ReportPostDataModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        getPeopleData()
    }

    private fun getPeopleData() {
        CoroutineScope(Dispatchers.IO).launch {
            NotMissingServerClient.getApiService().getMissingPeople("1").enqueue(object :
                Callback<MissingPeopleModel> {
                override fun onResponse(
                    call: Call<MissingPeopleModel>,
                    response: Response<MissingPeopleModel>
                ) {
                    val result = response.body()
                    if(response.code() == 200){
                        this@InitActivity.runOnUiThread {
                            peopleList = result!!.list as ArrayList<People>
                            peopleList.add(People(1, " ", " ",1," "," "," "," ",1,1," "," "," "," ",1," "," ",1, " "
                            ))
                            findViewById<TextView>(R.id.loadingText).text = "앱 로딩 중...(1/3)"
                            getPostData()
                        }
                    }
                }

                override fun onFailure(call: Call<MissingPeopleModel>, t: Throwable) {
                    t.printStackTrace()
                }
            })
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
                        findViewById<TextView>(R.id.loadingText).text = "앱 로딩 중...(2/3)"
                        postList = response.body()!!
                        getMapData()
                    }
                }

                override fun onFailure(call: Call<ReportPostDataModel>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

    private fun getMapData(){
        CoroutineScope(Dispatchers.IO).launch {
            NotMissingServerClient.getApiService().getSafeSpot("1").enqueue(object : Callback<SafeSpotModel> {
                override fun onResponse(
                    call: Call<SafeSpotModel>,
                    response: Response<SafeSpotModel>
                ) {
                    if(response.isSuccessful){
                        findViewById<TextView>(R.id.loadingText).text = "앱 로딩 중...(3/3) 성공!"
                        mapList = response.body()!!.list as ArrayList<Spot>
                        mapList.add(Spot(" "," "," "," "," "," ",1,1.0,1.0,1," "," "," "," "))
                        startActivity(Intent(
                            this@InitActivity, MainActivity::class.java
                        )
                            .putExtra("postList", postList)
                            .putExtra("peopleList", peopleList)
                            .putExtra("mapList", mapList)
                        )
                        finish()
                    }
                }

                override fun onFailure(call: Call<SafeSpotModel>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }
}