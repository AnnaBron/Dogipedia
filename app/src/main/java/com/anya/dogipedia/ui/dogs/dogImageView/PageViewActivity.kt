package com.anya.dogipedia.ui.dogs.dogImageView

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.anya.dogipedia.R


class PageViewActivity : FragmentActivity() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_view_activity)

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager)
        viewPager.setPageTransformer(ZoomOutPageTransformer())


        // The pager adapter, which provides the pages to the view pager widget.
        val position = intent.getIntExtra("currentImage", 0)
        val data = intent.getSerializableExtra("data") as? ArrayList<String>

        val pagerAdapter = data?.let { ScreenSlidePagerAdapter(this, it) }
        viewPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents num of ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(
        fa: FragmentActivity,
        private val data: ArrayList<String>
    ) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = data.size

        override fun createFragment(position: Int): Fragment = ImageViewFragment(data[position])
    }
}
