package com.dave.shareride.shared

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dave.shareride.R
import com.dave.shareride.shared.bottom_navigation.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<View>(R.id.bottom_navigation)
        val bottomNavigationView : BottomNavigationView = bottomNavigation.findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }

    override fun onStart() {
        super.onStart()
        
        checkPermission1()

        replaceFragmenty(
            fragment = Fragment_Home(),
            allowStateLoss = true,
            containerViewId = R.id.mainContent
        )
    }

    private fun checkPermission1() {

        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_SMS)
            + ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.READ_SMS
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage(
                    "Location and Storage permissions are required for the app."
                )
                builder.setTitle("Please grant these permissions")
                builder.setPositiveButton(
                    "OK"
                ) { diaMainActivityterface, i ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity, arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        MY_PERMISSIONS_REQUEST_CODE
                    )
                }
                builder.setNeutralButton(
                    "Cancel"
                ) { dialog, which ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    MY_PERMISSIONS_REQUEST_CODE
                )
            }
        }
        
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> {

                // When request is cancelled, the results array are empty
                if (grantResults.isNotEmpty() &&
                    (grantResults[0]
                            + grantResults[1]
                            == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permissions are granted

                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show()
                } else {
                    // Permissions are denied
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show()

                }
                return
            }
        }
        
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



//    @Override
//    override fun onBackPressed() {
//        AlertDialog.Builder(this)
//            .setIcon(android.R.drawable.ic_dialog_alert)
//            .setTitle("Closing Activity")
//            .setMessage("Are you sure you want to close this activity?")
//            .setPositiveButton("Yes"
//            ) { _, _ -> finish() }
//            .setNegativeButton("No", null)
//            .show()
//    }
}