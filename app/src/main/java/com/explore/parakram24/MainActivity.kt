package com.explore.parakram24

import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.explore.parakram24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoView = binding.appBar.videoView
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
            if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START, true)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START, true)
            }

        }

        binding.drawerLayout.setScrimColor(getResources().getColor(R.color.transparent))

//        binding.drawerLayout.addDrawerListener(object : SimpleDrawerListener() {
//            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
//
//                // Scale the View based on current slide offset
//                val diffScaledOffset: Float = slideOffset * (1 - 0.64f)
//                val offsetScale = 1 - diffScaledOffset
//                binding.appBar.appBarConstraintLayout.scaleX = offsetScale
//                binding.appBar.appBarConstraintLayout.scaleY = offsetScale
//
//                // Translate the View, accounting for the scaled width
//                val xOffset = drawerView.width * slideOffset
//                val xOffsetDiff: Float =
//                    binding.appBar.appBarConstraintLayout.width * diffScaledOffset / 2
//                val xTranslation = xOffset - xOffsetDiff
//                binding.appBar.appBarConstraintLayout.translationX = xTranslation
//            }
//
//        }
//        )


        navController = findNavController(R.id.nav_host_fragment_content_main)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {

                binding.appBar.videoView.visibility = View.VISIBLE
                videoView.start()
            } else {
                binding.appBar.videoView.visibility = View.GONE
                videoView.stopPlayback()
            }
            when (destination.id) {
                R.id.homeFragment -> binding.navView.setCheckedItem(R.id.homeFragment)
                R.id.aboutUsFragment -> binding.navView.setCheckedItem(R.id.aboutUsFragment)
                R.id.sponsorsFragment -> binding.navView.setCheckedItem(R.id.sponsorsFragment)
                R.id.eventsFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
                R.id.merchandiseFragment -> binding.navView.setCheckedItem(R.id.merchandiseFragment)
                R.id.coreTeamFragment -> binding.navView.setCheckedItem(R.id.coreTeamFragment)
                R.id.indiEventFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
                R.id.EditableIndividualEventFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)

                else -> binding.navView.setCheckedItem(R.id.homeFragment)
            }
            val fragmentId = destination.id
            var fragmentTitle = ""

            if (fragmentId == R.id.homeFragment) {
                fragmentTitle = "Home"
            } else if (fragmentId == R.id.sponsorsFragment) {
                fragmentTitle = "Sponsors"
            } else if (fragmentId == R.id.aboutUsFragment) {
                fragmentTitle = "About Us"
            } else if (fragmentId == R.id.eventsFragment) {
                fragmentTitle = "Events"
            } else if (fragmentId == R.id.coreTeamFragment) {
                fragmentTitle = "Core Team"
            } else if (fragmentId == R.id.merchandiseFragment) {
                fragmentTitle = "Merchandise"
            }


            binding.appBar.tvTitle.text = fragmentTitle


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

