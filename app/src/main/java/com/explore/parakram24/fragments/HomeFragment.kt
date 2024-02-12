package com.explore.parakram24.fragments

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.renderscript.RenderScript
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.explore.parakram24.R
import com.explore.parakram24.adapters.ViewPagerAdapter
import com.explore.parakram24.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentPage = 0

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // binding.background.setRenderEffect(RenderEffect.createBlurEffect(10f,10f,Shader.TileMode.MIRROR))
        binding.viewPagerCarousel.adapter = ViewPagerAdapter()

        binding.viewPagerCarousel.setPageTransformer(Transformer())

        addDotsIndicator(7)
        viewLifecycleOwner.lifecycleScope.launch {
            while(true) {
                delay(2000)
                binding.viewPagerCarousel.currentItem = (currentPage++) % 7
            }
        }


        binding.viewPagerCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })
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



//    private fun countDownStart() {
//        @Suppress("DEPRECATION") val handler = Handler()
//        val runnable = object : Runnable {
//            @SuppressLint("SetTextI18n", "SimpleDateFormat")
//            override fun run() {
//                handler.postDelayed(this, 1000)
//                try {
//                    val currentDate = Date()
//                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                    val futureDate: Date = dateFormat.parse("2024-03-08 00:00:00")!!
//                    if (!currentDate.after(futureDate)) {
//                        var diff: Long = (futureDate.time
//                                - currentDate.time)
//                        val days = diff / (24 * 60 * 60 * 1000)
//                        diff -= days * (24 * 60 * 60 * 1000)
//                        val hours = diff / (60 * 60 * 1000)
//                        diff -= hours * (60 * 60 * 1000)
//                        val minutes = diff / (60 * 1000)
//                        diff -= minutes * (60 * 1000)
//                        val seconds = diff / 1000
//                        binding.txtDay.text = "" + String.format("%02d", days)
//                        binding.txtHour.text = "" + String.format("%02d", hours)
//                        binding.txtMinute.text = "" + String.format("%02d", minutes)
//                        binding.txtSecond.text = "" + String.format("%02d",seconds)
//                    }
//                    else {
//                        countDownSrijanEnd()
//                        binding.textCounterDown.text = "Parakram'24 is Live"
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        handler.postDelayed(runnable, 1 * 1000)
//
//    }
//
//
//
//    fun countDownSrijanEnd(){
//        @Suppress("DEPRECATION") val handler = Handler()
//        val runnable = object : Runnable {
//            @SuppressLint("SetTextI18n", "SimpleDateFormat")
//            override fun run() {
//                handler.postDelayed(this, 1000)
//                try {
//                    val currentDate = Date()
//                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                    val futureDate: Date = dateFormat.parse("2024-03-10 00:00:00")!!
//                    if (!currentDate.after(futureDate)) {
//
//                        var diff: Long = (futureDate.time
//                                - currentDate.time)
//                        val days = diff / (24 * 60 * 60 * 1000)
//                        diff -= days * (24 * 60 * 60 * 1000)
//                        val hours = diff / (60 * 60 * 1000)
//                        diff -= hours * (60 * 60 * 1000)
//                        val minutes = diff / (60 * 1000)
//                        diff -= minutes * (60 * 1000)
//                        val seconds = diff / 1000
//                        binding.txtDay.text = "" + String.format("%02d", days)
//                        binding.txtHour.text = "" + String.format("%02d", hours)
//                        binding.txtMinute.text = "" + String.format("%02d", minutes)
//                        binding.txtSecond.text = "" + String.format("%02d",seconds)
//                    }
//                    else {
//                        binding.textCounterDown.text = "Parakram'24 is  Over"
//                        binding.txtDay.visibility = View.INVISIBLE
//                        binding.txtHour.visibility = View.INVISIBLE
//                        binding.txtMinute.visibility = View.INVISIBLE
//                        binding.txtSecond.visibility = View.INVISIBLE
//
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        handler.postDelayed(runnable,  1 * 1000)
//    }

    private fun addDotsIndicator(size: Int) {
        val dots = arrayOfNulls<ImageView>(size)
        for (i in 0 until size) {
            dots[i] = ImageView(requireContext())
            dots[i]?.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                R.drawable.indicator_inactive
            ))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.dotLayout.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(ContextCompat.getDrawable(requireContext(),
            R.drawable.indicator_active
        ))
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
            //this.animate().setDuration(5000L)
            view.pivotX = (if (position < 0) 0 else view.width).toFloat()
            view.scaleX = if (position < 0) 1f + position else 1f - position
        }
    }
}

