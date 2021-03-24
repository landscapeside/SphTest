package com.landside.sphtest.extension

import com.landside.sphtest.dto.BaseDto
import com.landside.support.exceptions.ServerException
import io.reactivex.Observable

fun <T : BaseDto> Observable<T>.filterResult(): Observable<T> =
    this.filter {
        if (it.success == true) {
            return@filter true
        } else {
            throw ServerException("服务器异常")
        }
    }