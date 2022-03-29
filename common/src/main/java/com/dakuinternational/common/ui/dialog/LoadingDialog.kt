package com.dakuinternational.common.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.dakuinternational.common.R
import com.dakuinternational.common.databinding.DialogLoadingBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce


class LoadingDialog(context: Context): Dialog(context) {

    lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar = binding.spinKit as ProgressBar
        val doubleBounce: Sprite = DoubleBounce()
        progressBar.indeterminateDrawable = doubleBounce

        setCancelable(false)
    }

    override fun onStart() {
        super.onStart()
        this.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        this.window?.setBackgroundDrawableResource(R.color.transparent)
    }
}   