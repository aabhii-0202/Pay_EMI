package com.mediustechnologies.payemi.adapters

import androidx.fragment.app.FragmentStatePagerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mediustechnologies.payemi.activities.BankListFragment

class CatagoryListAdapter internal constructor(fm: FragmentManager?, private val mNumOfTabs: Int, private var ids: ArrayList<String> ) :
    FragmentStatePagerAdapter(
        fm!!
    ) {
    // get the current item with position number
    override fun getItem(position: Int): Fragment {
        val b = Bundle()
        b.putString("position", ids!![position])
        val frag: Fragment = BankListFragment.newInstance()
        frag.arguments = b
        return frag
    }

    // get total number of tabs
    override fun getCount(): Int {
        return mNumOfTabs
    }
}