package com.explore.parakram24

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.explore.parakram24.adapters.ViewPagerAdapter
import com.explore.parakram24.databinding.ActivityMainBinding
import com.explore.parakram24.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.abs

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentPage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countDownStart()

        binding.viewPagerCarousel.adapter = ViewPagerAdapter()

        binding.viewPagerCarousel.setPageTransformer(Transformer())

        addDotsIndicator(6)
        binding.viewPagerCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })

//        startAutoScroll()

        binding.viewPagerCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }



    private fun countDownStart() {
        @Suppress("DEPRECATION") val handler = Handler()
        val runnable = object : Runnable {
            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun run() {
                handler.postDelayed(this, 1000)
                try {
                    val currentDate = Date()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val futureDate: Date = dateFormat.parse("2024-03-08 00:00:00")!!
                    if (!currentDate.after(futureDate)) {
                        var diff: Long = (futureDate.time
                                - currentDate.time)
                        val days = diff / (24 * 60 * 60 * 1000)
                        diff -= days * (24 * 60 * 60 * 1000)
                        val hours = diff / (60 * 60 * 1000)
                        diff -= hours * (60 * 60 * 1000)
                        val minutes = diff / (60 * 1000)
                        diff -= minutes * (60 * 1000)
                        val seconds = diff / 1000
                        binding.txtDay.text = "" + String.format("%02d", days)
                        binding.txtHour.text = "" + String.format("%02d", hours)
                        binding.txtMinute.text = "" + String.format("%02d", minutes)
                        binding.txtSecond.text = "" + String.format("%02d",seconds)
                    }
                    else {
                        countDownSrijanEnd()
                        binding.textCounterDown.text = "Parakram'24 is Live"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, 1 * 1000)

    }



    fun countDownSrijanEnd(){
        @Suppress("DEPRECATION") val handler = Handler()
        val runnable = object : Runnable {
            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun run() {
                handler.postDelayed(this, 1000)
                try {
                    val currentDate = Date()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val futureDate: Date = dateFormat.parse("2024-03-10 00:00:00")!!
                    if (!currentDate.after(futureDate)) {

                        var diff: Long = (futureDate.time
                                - currentDate.time)
                        val days = diff / (24 * 60 * 60 * 1000)
                        diff -= days * (24 * 60 * 60 * 1000)
                        val hours = diff / (60 * 60 * 1000)
                        diff -= hours * (60 * 60 * 1000)
                        val minutes = diff / (60 * 1000)
                        diff -= minutes * (60 * 1000)
                        val seconds = diff / 1000
                        binding.txtDay.text = "" + String.format("%02d", days)
                        binding.txtHour.text = "" + String.format("%02d", hours)
                        binding.txtMinute.text = "" + String.format("%02d", minutes)
                        binding.txtSecond.text = "" + String.format("%02d",seconds)
                    }
                    else {
                        binding.textCounterDown.text = "Parakram'24 is  Over"
                        binding.txtDay.visibility = View.INVISIBLE
                        binding.txtHour.visibility = View.INVISIBLE
                        binding.txtMinute.visibility = View.INVISIBLE
                        binding.txtSecond.visibility = View.INVISIBLE

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable,  1 * 1000)
    }

    private fun addDotsIndicator(size: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        for (i in 0 until size) {
            dots[i] = ImageView(requireContext())
            dots[i]?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.indicator_inactive))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.dotLayout.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.indicator_active))
    }

    private fun updateDots(position: Int) {
        val childCount = binding.dotLayout.childCount
        for (i in 0 until childCount) {
            val dot = binding.dotLayout.getChildAt(i) as ImageView
            val drawableId =
                if (i == position) R.drawable.indicator_active else R.drawable.indicator_inactive
            dot.setImageDrawable(ContextCompat.getDrawable(requireContext(), drawableId))
        }
    }

}

class Transformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> {
                    view.scrollX = (pageWidth * 0.75 * -1).toInt()
                }
                position <= 1 -> {
                    if (position < 0) {
                        view.scrollX = (pageWidth * 0.75 * position).toInt()
                    } else {
                        view.scrollX = (pageWidth * 0.75 * position).toInt()
                    }

                }
                else -> {
                    view.scrollX = (pageWidth * 0.75 ).toInt()
                }
            }

            view.pivotX = (if (position < 0) 0 else view.width).toFloat()
            view.scaleX = if (position < 0) 1f + position else 1f - position
        }
    }
}