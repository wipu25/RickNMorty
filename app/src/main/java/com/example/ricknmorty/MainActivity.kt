package com.example.ricknmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.characterFragment,
            R.id.locationFragment,
            R.id.episodeFragment
        ))

        setupActionBarWithNavController(navController,appBarConfiguration)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        setupWithNavController(bottomNavigation, navController)

        navController.addOnDestinationChangedListener{ controller, destination, argument ->
            if (appBarConfiguration.topLevelDestinations.contains(destination.id)){
                bottomNavigation.isVisible = true
            } else {
                bottomNavigation.isGone = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}