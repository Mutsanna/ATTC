package com.mutsanna.attc.camera.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.mutsanna.attc.R

class OnBoardingViewPagerAdapter (
    private var context: Context,
    private var onBoardingDataList: List<OnBoardingData>
    ) : PagerAdapter() {
    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.slide_content,null)

        val imageView: ImageView = view.findViewById(R.id.ct_image)
        val title: TextView = view.findViewById(R.id.ct_desc1)
        val desc:TextView = view.findViewById(R.id.ct_desc2)

        imageView.setImageResource(onBoardingDataList[position].imageView)
        title.text = onBoardingDataList[position].judul
        desc.text = onBoardingDataList[position].desc

        container.addView(view)
        return view
    }



}