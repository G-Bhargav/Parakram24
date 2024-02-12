package com.explore.parakram24

import android.graphics.Color
import android.media.PlaybackParams
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
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
        navController =findNavController(R.id.nav_host_fragment_content_main)

        videoView= binding.appBar.videoView
        val videoPath = "android.resource://" + packageName + "/" + R.raw.backgroundVideo
        videoView.setVideoPath(videoPath)
        var speed = 1f;
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            mp.setVolume(0f, 0f)
        }

        videoView.setOnCompletionListener {mp->
            speed *= -1
        }

        videoView.start()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.aboutUsFragment,
//                R.id.eventsFragment,
//                R.id.singleEventFragment,
//                R.id.announcementsFragment,
//                R.id.merchandiseFragment,
//                R.id.sponsorsFragment,
//                R.id.addAnnouncementFragment,
//                R.id.profileFragment,
//                R.id.coreTeamFragment,
//                R.id.contactFragment,
//                R.id.plansFragmentDrawer
            ), binding.drawerLayout
        )
        binding.appBar.btnMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.homeFragment -> binding.navView.setCheckedItem(R.id.homeFragment)
                R.id.aboutUsFragment -> binding.navView.setCheckedItem(R.id.aboutUsFragment)
//                R.id.eventsFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
//                R.id.singleEventFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
//                R.id.announcementsFragment -> binding.navView.setCheckedItem(R.id.announcementsFragment)
//                R.id.merchandiseFragment -> binding.navView.setCheckedItem(R.id.merchandiseFragment)
//                R.id.sponsorsFragment -> binding.navView.setCheckedItem(R.id.sponsorsFragment)
//                R.id.addAnnouncementFragment -> binding.navView.setCheckedItem(R.id.addAnnouncementFragment)
//                R.id.profileFragment -> binding.navView.setCheckedItem(R.id.profileFragment)
//                R.id.coreTeamFragment -> binding.navView.setCheckedItem(R.id.coreTeamFragment)
//                R.id.contactFragment -> binding.navView.setCheckedItem(R.id.contactFragment)
//                R.id.plansFragmentDrawer -> binding.navView.setCheckedItem(R.id.plansFragmentDrawer)
                else -> binding.navView.setCheckedItem(R.id.homeFragment)
            }
            binding.appBar.tvTitle.text = when (destination.id) {
                R.id.homeFragment -> "Home"
//                R.id.eventsFragment -> "Events"
//                R.id.announcementsFragment -> "Announcements"
//                R.id.merchandiseFragment -> "Merchandise"
//                R.id.sponsorsFragment -> "Past Sponsors"
//                R.id.addAnnouncementFragment -> "New Announcement"
//                R.id.profileFragment -> "Profile"
                R.id.aboutUsFragment -> "About Us"
//                R.id.coreTeamFragment -> "Core Team"
//                R.id.contactFragment -> "Contact Us"
//                R.id.singleEventFragment -> "Events"
//                R.id.plansFragmentDrawer -> "Plans"
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

}

