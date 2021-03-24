package com.landside.sphtest.repo.mapper

import com.landside.sphtest.domin.MobileUsageItem
import com.landside.sphtest.dto.MobileUsageDto
import io.reactivex.functions.Function

object MobileUsagesMapper : Function<MobileUsageDto, List<MobileUsageItem>> {

    fun <T> List<T>.scan(predicate: (T?, T) -> Boolean): Boolean {
        var success = true
        forEachIndexed { index, t ->
            if (!predicate(getOrNull(index - 1), t)) {
                success = false
                return@forEachIndexed
            }
        }
        return success
    }

    override fun apply(dto: MobileUsageDto): List<MobileUsageItem> {
        val result = mutableListOf<MobileUsageItem>()
        val records = dto.result?.records?.toMutableList() ?: mutableListOf()
        (2008..2018).forEach { year ->
            result.add(
                MobileUsageItem(
                    year = year.toString(),
                    value = records.sumByDouble {
                        if (it?.quarter?.startsWith("$year") == true) it?.volume_of_mobile_data?.toDouble()
                            ?: 0.0 else 0.0
                    },
                    hasDecrease = records?.filter { it?.quarter?.startsWith("$year") == true }
                        .scan { record, record2 ->
                            record2?.volume_of_mobile_data?.toDouble() ?: 0.0 >= record?.volume_of_mobile_data?.toDouble() ?: 0.0
                        }.not()
                ))
            Unit
        }
        return result
    }
}