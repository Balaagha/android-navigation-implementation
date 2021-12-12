package com.example.androidnavigationimplementation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.androidnavigationimplementation.databinding.ActivityMainBinding
import com.example.androidnavigationimplementation.databinding.NavHeaderMainBinding
import com.example.androidnavigationimplementation.ui.viewmodel.LettersViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    // TODO: initialize navController

    // TODO: initialize appBarConfiguration

    private var lettersViewModel: LettersViewModel? = null
    private lateinit var headerBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setSupportActionBar(toolbar)
        setupNavigation()
        setupViewModel()
        setupViews()
    }

    override fun onDestroy() {
        lettersViewModel?.apply { closeDb() }
        super.onDestroy()
    }

    private fun setupDataBinding() {
        val activityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        headerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.nav_header_main, activityMainBinding.navView, false
        )
        headerBinding.ivEdit.setOnClickListener {
            // TODO: navigate to edit profile fragment
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        activityMainBinding.navView.addHeaderView(headerBinding.root)
    }

    private fun setupNavigation() {
        // TODO: setup navController with drawerLayout
        // TODO: setup navController with toolbar and appBarConfiguration
        // TODO: add destination listener to navController
    }

    private fun setupViewModel() {
        // TODO: initialize lettersViewModel
        // TODO: assign lettersViewModel to headerBinding
        // TODO: load profile with lettersViewModel
    }

    private fun setupViews() {
        navView.setNavigationItemSelectedListener(this)
        fab.setOnClickListener {
            // TODO: navigate to create letter fragment
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_inbox -> {
                // TODO: navigate to inbox fragment
            }
            R.id.nav_sent -> {
                // TODO: navigate to sent fragment
            }
            R.id.nav_privacy_policy -> {
                // TODO: navigate to privacy policy fragment
            }
            R.id.nav_terms_of_service -> {
                // TODO: navigate to privacy terms of service fragment
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}