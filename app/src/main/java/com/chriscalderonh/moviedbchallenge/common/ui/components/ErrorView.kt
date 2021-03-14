package com.chriscalderonh.moviedbchallenge.common.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.chriscalderonh.moviedbchallenge.databinding.ViewErrorBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    var binding: ViewErrorBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ViewErrorBinding.inflate(inflater, this, true)
    }

    fun setError(text: String) {
        binding.tvErrorText.text = text
    }

    fun setClickListener(clickListener: () -> Unit) {
        binding.btnContinue.setOnClickListener { clickListener() }
    }
}