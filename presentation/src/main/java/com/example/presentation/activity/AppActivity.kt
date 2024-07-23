package com.example.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.presentation.R
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity(R.layout.activity_app) {
    private lateinit var appNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setupListeners()
    }

    private fun init() {
        val customTransparent = com.example.resources.R.color.custom_transparent.toColor().toArgb()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = customTransparent,
                darkScrim = customTransparent
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = customTransparent,
                darkScrim = customTransparent
            ),
        )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        appNavController = findNavController(viewId = R.id.nav_host_fragment)
    }

    private fun setupListeners() {
        findViewById<NavigationBarView>(R.id.bottom_nav_bar)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.air_tickets -> {
                        appNavController.navigate(R.id.nav_main)
                        true
                    }
                    else -> {
                        appNavController.navigate(
                            if (appNavController.currentDestination?.id == R.id.startSearchFragment)
                                R.id.action_searchFragment_to_stubFragment
                            else
                                R.id.stubFragment
                        )
                        true
                    }
                }
            }
    }
}