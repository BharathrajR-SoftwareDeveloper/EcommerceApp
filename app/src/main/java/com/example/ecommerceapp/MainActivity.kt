package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ecommerceapp.ui.CardFragement
import com.example.ecommerceapp.ui.HomeFragementt
import com.example.ecommerceapp.ui.OrderFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        setCurrentFragment(HomeFragementt())

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeBtn -> setCurrentFragment(HomeFragementt())
                R.id.cardBtn -> setCurrentFragment(CardFragement())
                R.id.orderBtn -> setCurrentFragment(OrderFragment())
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main, fragment)
            commit()
        }
    }
}
