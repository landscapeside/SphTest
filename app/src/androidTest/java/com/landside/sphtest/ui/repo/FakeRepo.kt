package com.landside.sphtest.ui.repo

import com.landside.sphtest.domin.MobileUsageItem
import com.landside.sphtest.repo.MobileUsageRepo
import io.reactivex.Observable

class FakeRepo:MobileUsageRepo {
    val normalUsages = listOf<MobileUsageItem>(
        MobileUsageItem(
            year = "2009",
            value = 1.0,
            hasDecrease = false
        ),
        MobileUsageItem(
            year = "2010",
            value = 2.0,
            hasDecrease = true
        ),
        MobileUsageItem(
            year = "2011",
            value = 3.0,
            hasDecrease = false
        )
    )


    override fun getUsages(id: String): Observable<List<MobileUsageItem>> =
        Observable.just(normalUsages)
}