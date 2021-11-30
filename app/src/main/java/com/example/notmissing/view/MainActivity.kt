package com.example.notmissing.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.notmissing.R
import com.example.notmissing.model.missingpeople.People
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem
import com.example.notmissing.model.safespot.Spot
import com.example.notmissing.view.fragment.HowToReportLostPeopleFragment
import com.example.notmissing.view.fragment.MapFragment
import com.example.notmissing.view.fragment.PeopleFragment
import com.example.notmissing.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        vm.postList.value = intent.getSerializableExtra("postList") as ReportPostDataModel


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
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, fragment).commit()
    }
}