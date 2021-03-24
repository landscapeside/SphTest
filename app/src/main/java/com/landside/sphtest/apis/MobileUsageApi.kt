package com.landside.sphtest.apis

import com.landside.sphtest.dto.MobileUsageDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MobileUsageApi{
    @GET("action/datastore_search")
    fun getUsages(
        @Query("resource_id") id:String
    ):Observable<MobileUsageDto>
}