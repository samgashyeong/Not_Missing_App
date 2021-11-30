package com.example.notmissing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.notmissing.R
import com.example.notmissing.databinding.ActivityPostDetailBinding
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem

class PostDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)

        val data = intent.getSerializableExtra("data") as ReportPostDataModelItem
        binding.writerText.text = data.writer
        binding.contentEditText.text = data.content
        binding.titleText.text = data.title
        binding.dayText.text = data.created_at
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