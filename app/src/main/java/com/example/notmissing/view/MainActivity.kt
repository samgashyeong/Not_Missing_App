package com.example.notmissing.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.notmissing.R
import com.example.notmissing.view.fragment.HowToReportLostPeopleFragment
import com.example.notmissing.view.fragment.MapFragment
import com.example.notmissing.view.fragment.PeopleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val mainFragment1 by lazy { HowToReportLostPeopleFragment() }
    private val mainFragment3 by lazy { MapFragment() }
    private val mainFragment2 by lazy { PeopleFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment(mainFragment1)


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