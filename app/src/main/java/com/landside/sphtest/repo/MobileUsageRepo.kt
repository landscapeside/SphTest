package com.landside.sphtest.repo

import com.landside.sphtest.domin.MobileUsageItem
import io.reactivex.Observable

interface MobileUsageRepo {
    fun getUsages(id:String):Observable<List<MobileUsageItem>>
}