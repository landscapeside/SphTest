package com.landside.sphtest.dto

data class MobileUsageDto(
    val result: Result?
):BaseDto()

data class Result(
    val records: List<Record?>?,
    val resource_id: String?,
    val total: Int?
)

data class Record(
    val _id: Int?,
    val quarter: String?,
    val volume_of_mobile_data: String?
)