//package com.mediustechnologies.payemi.activities.scratchCard
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import com.mediustechnologies.payemi.R
//import com.mediustechnologies.payemi.activities.scratchCard.fragment.DemoFragment
//import com.mediustechnologies.payemi.databinding.ActivityScratchCardBinding
//
//class ScratchCardActivity : AppCompatActivity() {
//
//    private val demoFragment by lazy { DemoFragment.getInstance() }
//    private var binding: ActivityScratchCardBinding? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityScratchCardBinding.inflate(layoutInflater)
//        setContentView(binding!!.root)
//
//        replaceFragment(demoFragment)
//    }
//
//    private fun replaceFragment(instance: Fragment) {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.container, instance)
//        fragmentTransaction.commit()
//    }
//}