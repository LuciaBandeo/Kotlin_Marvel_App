package com.example.data.service.response

import com.google.gson.annotations.SerializedName

class DataResponse(
    @SerializedName("data")
    val data: ResultResponse
)
