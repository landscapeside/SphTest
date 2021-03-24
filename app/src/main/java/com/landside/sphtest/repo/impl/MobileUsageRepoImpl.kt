package com.landside.sphtest.repo.impl

import com.landside.sphtest.apis.MobileUsageApi
import com.landside.sphtest.apis.RetrofitService
import com.landside.sphtest.domin.MobileUsageItem
import com.landside.sphtest.extension.filterResult
import com.landside.sphtest.repo.MobileUsageRepo
import com.landside.sphtest.repo.mapper.MobileUsagesMapper
import io.reactivex.Observable
import javax.inject.Inject

class MobileUsageRepoImpl @Inject constructor(val service: RetrofitService) : MobileUsageRepo {
    override fun getUsages(id: String): Observable<List<MobileUsageItem>> =
        service.createApi(MobileUsageApi::class.java)
            .getUsages(id)
            .filterResult()
            .map(MobileUsagesMapper)
}