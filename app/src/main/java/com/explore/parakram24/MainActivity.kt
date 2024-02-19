package com.explore.parakram24

import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.explore.parakram24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var videoView : VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoView= binding.appBar.videoView
        val videoPath = """android.resource://$packageName/${R.raw.backgroundvideo}"""
        videoView.setVideoPath(videoPath)
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            mp.setVolume(0f, 0f)
        }
        videoView.start()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.aboutUsFragment,
                R.id.sponsorsFragment,
                R.id.eventsFragment,
                R.id.indiEventFragment,
                R.id.coreTeamFragment
            ), binding.drawerLayout
        )


        binding.appBar.btnMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        navController =findNavController(R.id.nav_host_fragment_content_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id==R.id.homeFragment){

                binding.appBar.videoView.visibility= View.VISIBLE
                videoView.start()
            }
            else{
                binding.appBar.videoView.visibility= View.GONE
                videoView.stopPlayback()
            }
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.navView.setCheckedItem(R.id.homeFragment)
                }
                R.id.aboutUsFragment -> binding.navView.setCheckedItem(R.id.aboutUsFragment)
                R.id.sponsorsFragment -> binding.navView.setCheckedItem(R.id.sponsorsFragment)
                R.id.indiEventFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
                R.id.eventsFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
                R.id.merchandiseFragment -> binding.navView.setCheckedItem(R.id.merchandiseFragment)
                R.id.coreTeamFragment -> binding.navView.setCheckedItem(R.id.coreTeamFragment)
                else -> binding.navView.setCheckedItem(R.id.homeFragment)
            }
            binding.appBar.tvTitle.text = when (destination.id) {
                R.id.homeFragment -> "Home"
                R.id.sponsorsFragment -> "Sponsors"
                R.id.aboutUsFragment -> "About Us"
                R.id.eventsFragment -> "Events"
                R.id.coreTeamFragment -> "Core Team"
                else -> "Parakram 24"
            }
        }
        binding.navView.setupWithNavController(navController)
        binding.navView.setCheckedItem(R.id.homeFragment)
    }

    override fun onResume() {
        super.onResume()
        videoView.start()

    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        if(binding.navView.id==R.id.ev)
//    }

}

