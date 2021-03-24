package com.landside.sphtest.ui.mobile

import android.view.View
import android.widget.TextView
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.landside.shadowstate_annotation.BindState
import com.landside.sphtest.R
import com.landside.sphtest.domin.MobileUsage
import com.landside.sphtest.domin.MobileUsageItem
import com.landside.sphtest.ui.BaseActivity
import com.landside.support.extensions.toVisibility
import com.landside.support.extensions.toast
import kotlinx.android.synthetic.main.activity_mobile_usage.*
import kotlinx.android.synthetic.main.item_usage.*

@BindState(state = MobileUsage::class,agent = MobileUsageAgent::class)
class MobileUsageActivity:BaseActivity<MobileUsageView,MobileUsagePresenter>(),MobileUsageView {
    override val layoutId: Int
        get() = R.layout.activity_mobile_usage

    lateinit var adapter:BindingAdapter

    override fun initViews() {
        adapter = usages.linear().setup {
            addType<MobileUsageItem>(R.layout.item_usage)
            onBind {
                findView<TextView>(R.id.tv_year).text = "year:${getModel<MobileUsageItem>().year}"
                findView<TextView>(R.id.tv_usage_value).text = getModel<MobileUsageItem>().value.toString()
                findView<View>(R.id.decrease).visibility = getModel<MobileUsageItem>().hasDecrease.toVisibility()
                findView<View>(R.id.decrease).setOnClickListener {
                    toast { "has decrease!" }
                }
            }
        }
    }

    override fun onLoad() {
        presenter.load()
    }

    override fun updateUsages(usages: List<MobileUsageItem>) {
        adapter.models = usages
    }
}