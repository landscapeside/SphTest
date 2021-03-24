package com.landside.sphtest.apis

import com.landside.rehost.ReHost
import javax.inject.Inject

class RetrofitService @Inject
constructor() {

    fun <T> createApi(clazz: Class<T>): T {
        return ReHost.createApi0(clazz)
    }
}
