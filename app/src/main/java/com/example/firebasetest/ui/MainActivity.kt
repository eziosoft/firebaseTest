package com.example.firebasetest.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.firebasetest.BuildConfig
import com.example.firebasetest.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "aaa"
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        AppCenter.start(application, BuildConfig.APP_CENTER_SECRET, Analytics::class.java, Crashes::class.java)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
//        navController = navHostFragment.findNavController()
//
//        val appBarConfiguration = AppBarConfiguration(setOf())
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}

//
