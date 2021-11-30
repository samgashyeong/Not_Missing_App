package com.example.notmissing.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.notmissing.R
import com.example.notmissing.api.NotMissingServerClient
import com.example.notmissing.databinding.ActivityPostAddBinding
import com.example.notmissing.model.reportpostdata.PostModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostAddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPostAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_add)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_add)


        binding.saveBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                NotMissingServerClient.getApiService().PostReportPostData(
                    PostModel(
                        binding.titleText.text.toString(),
                        binding.contentEditText.text.toString(),
                        binding.writerText.text.toString())
                ).enqueue(object : Callback<ReportPostDataModelItem> {
                    override fun onResponse(
                        call: Call<ReportPostDataModelItem>,
                        response: Response<ReportPostDataModelItem>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(this@PostAddActivity, "게시물 게시에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                            finish()
                        }
                        else{
                            Toast.makeText(this@PostAddActivity, "게시물 게시에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ReportPostDataModelItem>, t: Throwable) {
                         t.printStackTrace()
                    }
                })
            }
        }


        setSupportActionBar(binding.toolbar2)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}