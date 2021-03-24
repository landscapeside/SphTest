package com.landside.sphtest.ui.mobile

import android.os.Bundle
import com.landside.shadowstate.StateAgent
import com.landside.sphtest.domin.MobileUsage

class MobileUsageAgent:StateAgent<MobileUsage,MobileUsageView>() {
    override fun conf() {
        listen({it.usages},{view?.updateUsages(it)})
    }

    override fun initState(bundle: Bundle?): MobileUsage =
        MobileUsage(
            id = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
        )
}