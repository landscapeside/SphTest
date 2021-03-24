package com.landside.sphtest.ui

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.landside.sphtest.R
import com.landside.support.mvp.BasePresenterImpl
import com.landside.support.mvp.BaseView
import com.landside.support.mvp.MVPBaseActivity
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity<V : BaseView, P : BasePresenterImpl<V>> : MVPBaseActivity<V, P>() {

    override fun showProgress() {

    }

    override fun dismissProgress() {

    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_base)
        LayoutInflater.from(context)
            .inflate(layoutId, page_content)
    }

    fun setupToolbar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.visibility = View.VISIBLE
    }

    override fun setTitle(resId: Int) {
        super.setTitle(resId)
        tool_bar.findViewById<TextView>(R.id.tv_back)
            ?.setText(resId)
        tool_bar.findViewById<TextView>(R.id.tv_back)
            ?.setOnClickListener {
                back()
            }
    }

    override fun setTitle(title: CharSequence) {
        super.setTitle(title)
        tool_bar.findViewById<TextView>(R.id.tv_title)
            ?.text = title
        tool_bar.findViewById<TextView>(R.id.tv_title)
            ?.visibility = View.VISIBLE
    }

    override fun setTitleColor(@ColorRes resId: Int) {
        tool_bar.findViewById<TextView>(R.id.tv_title)
            ?.setTextColor(ResourcesCompat.getColor(resources, resId, null))
    }

    fun setTitleBackgroundColor(@ColorRes resId: Int) {
        tool_bar.setBackgroundResource(resId)
    }

    fun setNavigationIcon(@DrawableRes resId: Int, onClick: () -> Unit) {
        tool_bar.setNavigationIcon(resId)
        tool_bar.setNavigationOnClickListener { onClick() }
    }

    fun setRightMenu(@StringRes resId: Int, onClick: () -> Unit) {
        tool_bar.findViewById<TextView>(R.id.tv_right)
            ?.setText(resId)
        tool_bar.findViewById<TextView>(R.id.tv_right)
            ?.setOnClickListener {
                onClick()
            }
    }

    fun setRightMenu(
        right: CharSequence,
        onClick: () -> Unit
    ) {
        tool_bar.findViewById<TextView>(R.id.tv_right)
            ?.text = right
        tool_bar.findViewById<TextView>(R.id.tv_right)
            ?.setOnClickListener {
                onClick()
            }
    }

    fun setRightMenuColor(@ColorRes resId: Int) {
        tool_bar.findViewById<TextView>(R.id.tv_right)
            ?.setTextColor(ResourcesCompat.getColor(resources, resId, null))
    }
}