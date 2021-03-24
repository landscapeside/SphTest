package com.landside.sphtest.ui.mobile

import com.landside.sphtest.domin.MobileUsageItem
import com.landside.support.mvp.BaseView

interface MobileUsageView:BaseView {
    fun updateUsages(usages:List<MobileUsageItem>)
}