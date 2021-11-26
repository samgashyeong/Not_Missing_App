package com.example.notmissing.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.notmissing.R
import com.example.notmissing.databinding.ActivityPeopleDetailBinding
import com.example.notmissing.model.missingpeople.People
import java.lang.Exception

class PeopleDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPeopleDetailBinding
    private lateinit var peopleData : People
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_people_detail)

        peopleData = intent.getSerializableExtra("peopleData") as People

        setUi()
        setSupportActionBar(binding.toolbar)

        binding.toolbar.title = "실종자 세부사항"

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUi() {
        binding.peopleName.text = peopleData.nm
        binding.peopleAge.text = "당시 나이 : ${peopleData.age}\n현재 나이 : ${peopleData.ageNow}"
        binding.occurText.text = "발생 장소 : ${peopleData.occrAdres}\n발생 날짜 : ${peopleData.occrde}"
        binding.peopleImage.setImageBitmap(StringToBitMap(peopleData.tknphotoFile))
        binding.wearDetailText.text = "키 : ${peopleData.height}\n뭄무게 : ${peopleData.bdwgh}\n체격 : ${peopleData.frmDscd}\n얼굴형 : ${peopleData.faceshpeDscd}\n두발색상 : ${peopleData.hairshpeDscd}\n착의사항 : ${peopleData.alldressingDscd}"
        binding.etcDetailText.text = "대상구분 : ${partPeople()}"
    }

    private fun partPeople(): String {
        return when(peopleData.writngTrgetDscd){
            "010"->{
                "정상아동"
            }
            "020" ->{
                "가출인"
            }
            "040" ->{
                "시설보호무연고자"
            }
            "060"->{
                "지적장애인"
            }
            "061"->{
                "지적장애인"
            }
            "062"->{
                "지적장애인"
            }
            "070" ->{
                "치매질환자"
            }
            else->{
                "기타"
            }
        }
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
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