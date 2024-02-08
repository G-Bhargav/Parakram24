package com.explore.parakram24

import android.graphics.Color
import android.os.Bundle
import android.view.View
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
import com.explore.parakram24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController =findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
//                R.id.eventsFragment,
//                R.id.singleEventFragment,
//                R.id.announcementsFragment,
//                R.id.merchandiseFragment,
//                R.id.sponsorsFragment,
//                R.id.addAnnouncementFragment,
//                R.id.profileFragment,
//                R.id.aboutUsFragment,
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
//                R.id.eventsFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
//                R.id.singleEventFragment -> binding.navView.setCheckedItem(R.id.eventsFragment)
//                R.id.announcementsFragment -> binding.navView.setCheckedItem(R.id.announcementsFragment)
//                R.id.merchandiseFragment -> binding.navView.setCheckedItem(R.id.merchandiseFragment)
//                R.id.sponsorsFragment -> binding.navView.setCheckedItem(R.id.sponsorsFragment)
//                R.id.addAnnouncementFragment -> binding.navView.setCheckedItem(R.id.addAnnouncementFragment)
//                R.id.profileFragment -> binding.navView.setCheckedItem(R.id.profileFragment)
//                R.id.aboutUsFragment -> binding.navView.setCheckedItem(R.id.aboutUsFragment)
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
//                R.id.aboutUsFragment -> "About Us"
//                R.id.coreTeamFragment -> "Core Team"
//                R.id.contactFragment -> "Contact Us"
//                R.id.singleEventFragment -> "Events"
//                R.id.plansFragmentDrawer -> "Plans"
                else -> "Srijan 24"
            }

        }

    }
}