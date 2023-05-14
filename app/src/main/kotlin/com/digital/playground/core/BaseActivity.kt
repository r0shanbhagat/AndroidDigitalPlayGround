package com.digital.playground.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Details BaseActivity is abstract class which having the
 * the common properties of all activity.
 * @Author Roshan Bhagat *
 * @param B
 * @constructor Create Base activity
 */
abstract class BaseActivity<B : ViewDataBinding> :
    AppCompatActivity() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    @get:LayoutRes
    protected abstract val layoutResId: Int


}