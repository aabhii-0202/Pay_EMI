package com.mediustechnologies.payemi.adapters

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import com.mediustechnologies.payemi.R
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class SliderAdapter(private val context: Context) : PagerAdapter() {
    var images = intArrayOf(
        R.drawable.onboard1,
        R.drawable.onboard2,
        R.drawable.onboard3
    )
    var headings = arrayOf(
        "Grow Easily.",
        "Pay EMI with One Touch",
        "Grow Easily."
    )
    var subheadings = arrayOf(
        "See at a glance all EMIs and get instant cashback.",
        "Easy and smart way to Save your wallet.",
        "See at a glance all EMIs and get instant cashback."
    )
    var btnimgs = intArrayOf(
        R.drawable.ic_load1,
        R.drawable.ic_load2,
        R.drawable.ic_load3
    )
    private var inflater: LayoutInflater? = null
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater!!.inflate(R.layout.slides_layout_onbordingscreen, container, false)
        val imageView = view.findViewById<ImageView>(R.id.sliderImage)
        val t1 = view.findViewById<TextView>(R.id.titleText1)
        val t2 = view.findViewById<TextView>(R.id.subtitleText2)
        //        ImageView btn = view.findViewById(R.id.nextbtn);
        imageView.setImageResource(images[position])
        t1.text = headings[position]
        t2.text = subheadings[position]
        //        btn.setImageResource(btnimgs[position]);
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}