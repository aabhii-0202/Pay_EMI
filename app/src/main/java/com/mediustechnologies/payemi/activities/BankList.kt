package com.mediustechnologies.payemi.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.mediustechnologies.payemi.ApiResponse.banklistResponse
import com.mediustechnologies.payemi.ApiResponse.getAllBanks
import com.mediustechnologies.payemi.adapters.CatagoryListAdapter
import com.mediustechnologies.payemi.adapters.bankListAdapter
import com.mediustechnologies.payemi.databinding.ActivityBankListBinding
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity


class BankList : BaseAppCompatActivity() {
    private var binding: ActivityBankListBinding? = null
    private var catagories: ArrayList<String>? = null
    private var ids: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankListBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        val p = intent.getIntExtra("position", 0)
        catagories = intent.getStringArrayListExtra("catagories");
        ids = intent.getStringArrayListExtra("ids");

        initcatagories(p)

    }

    private fun initcatagories(p: Int) {

        binding!!.viewpager.setOffscreenPageLimit(5)
        binding!!.viewpager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding!!.tabs))
        binding!!.tabs.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // setCurrentItem as the tab position
                binding!!.viewpager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        setDynamicFragmentToTabLayout(p)
    }
    private fun setDynamicFragmentToTabLayout(p: Int) {
        for (i in catagories!!) {
            binding!!.tabs.addTab(binding!!.tabs.newTab().setText(i))
        }
        val CatagoryListAdapter = CatagoryListAdapter(
            supportFragmentManager, binding!!.tabs.getTabCount(), ids!!
        )
        binding!!.viewpager.setAdapter(CatagoryListAdapter)

        binding!!.viewpager.setCurrentItem(p)
    }

}