package com.mediustechnologies.payemi.activities.scratchCard.fragment

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mediustechnologies.payemi.activities.scratchCard.listener.ScratchListener
import com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout
import com.mediustechnologies.payemi.databinding.FragmentDemoBinding

class DemoFragment : DialogFragment(), ScratchListener {

    companion object {
        private val TAG: String? = DemoFragment::class.java.simpleName

        fun getInstance(): DemoFragment {
            val bundle = Bundle()
            val demoFragment = DemoFragment()
            demoFragment.arguments = bundle
            return demoFragment
        }
    }

    private lateinit var binding: FragmentDemoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDemoBinding.inflate(inflater)
        return binding.root
    }

    //    private fun resetLibraryView() {
//        binding.scratchCard.setScratchListener(this)
//
//        binding.reveal.setOnClickListener { binding.scratchCard.revealScratch() }
//        binding.reset.setOnClickListener {
//            binding.scratchCard.resetScratch()
//            resetControlPanelView()
//        }
//    }

//    private fun resetControlPanelView() {
//        binding.brushSizeSeekBar.progress = 40
//        binding.revealFullAtSeekBar.progress = 40
//        binding.scratchEffectToggle.isChecked = true
//        binding.scratchEffectToggle.text = "enabled"
//    }

//    private fun setControlPanelListeners() {
//        //Scratch Brush size config
//        binding.brushSizeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                context?.let {
//                    binding.scratchCard.setScratchWidthDip(ScratchCardUtils.dipToPx(it, seekBar!!.progress.toFloat()))
//                }
//            }
//        })
//
//        //Scratch effect config
//        binding.scratchEffectToggle.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
//            binding.scratchCard.setScratchEnabled(isChecked)
//            binding.scratchEffectToggle.text = if (isChecked) "enable" else "disable"
//        }
//
//        //Scratch reveal at percent
//        binding.revealFullAtSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                AppUtils.showShortToast(activity, "Restting view since reveal percent setting was change")
//                binding.scratchCard.setRevealFullAtPercent(seekBar!!.progress)
//                binding.scratchCard.resetScratch()
//            }
//        })
//    }

    override fun onScratchStarted() {
        Log.d(TAG, "Scratch started")
    }

    override fun onScratchProgress(scratchCardLayout: ScratchCardLayout, atLeastScratchedPercent: Int) {
        Log.d(TAG, "Progress = $atLeastScratchedPercent")
    }

    override fun onScratchComplete() {
        Log.d(TAG, "Scratch ended")
    }
}