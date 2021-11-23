package com.example.notmissing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val mainFragment2 by lazy { MapFragment() }
    private val mainFragment3 by lazy { PeopleFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment(mainFragment2)


        findViewById<BottomNavigationView>(R.id.bottomNavigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map -> {
                    changeFragment(mainFragment2)
                    true
                }
                R.id.lostPeople -> {
                    changeFragment(mainFragment3)
                    true
                }
                else -> {
                    changeFragment(mainFragment2)
                    false
                }
            }
        }
        findViewById<FloatingActionButton>(R.id.faBtn).setOnClickListener {
//            startActivity(Intent(this, PickMoodActivity::class.java))
        }
    }
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, fragment).commit()
    }
}