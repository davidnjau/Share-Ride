package com.dave.shareride.shared

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dave.shareride.R
import com.dave.shareride.shared.bottom_navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<View>(R.id.bottom_navigation)
        val bottomNavigationView : BottomNavigationView = bottomNavigation.findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }

    override fun onStart() {
        super.onStart()

        replaceFragmenty(
            fragment = Fragment_Home(),
            allowStateLoss = true,
            containerViewId = R.id.mainContent
        )
    }

    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_pin -> {

                    replaceFragmenty(
                        fragment = Fragment_Home(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                    )

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {

                    replaceFragmenty(
                        fragment = Fragment_Profile(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                    )

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_history -> {
                    replaceFragmenty(
                        fragment = Fragment_History(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_wallet -> {
                     replaceFragmenty(
                        fragment = Fragment_Wallet(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    replaceFragmenty(
                        fragment = Fragment_Settings(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                    )
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}