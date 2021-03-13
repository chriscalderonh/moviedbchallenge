package com.chriscalderonh.moviedbchallenge.upcomingmovies.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.chriscalderonh.moviedbchallenge.R
import com.chriscalderonh.moviedbchallenge.databinding.ActivityMoviesBinding
import com.chriscalderonh.moviedbchallenge.upcomingmovies.di.DaggerUpcomingMoviesComponent

class MoviesActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMoviesBinding

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        when (destination.id) {
            R.id.upcomingMoviesFragment -> supportActionBar?.hide()
            else -> supportActionBar?.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        setUpInjection()
        setUpNavigation()
    }

    private fun setUpInjection() {
        DaggerUpcomingMoviesComponent.builder()
            .build()
            .inject(this)
    }

    private fun setUpNavigation() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(findViewById(R.id.tbMovies))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}