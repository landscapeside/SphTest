package com.landside.sphtest.ui.mobile

import android.os.Bundle
import com.landside.shadowstate.StateAgent
import com.landside.sphtest.domin.MobileUsage

class MobileUsageAgent:StateAgent<MobileUsage,MobileUsageView>() {
    override fun conf() {

    }

    override fun initState(bundle: Bundle?): MobileUsage =
        MobileUsage(
            id = ""
        )
}