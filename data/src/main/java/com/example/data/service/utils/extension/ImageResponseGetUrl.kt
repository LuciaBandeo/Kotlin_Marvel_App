package com.example.data.service.utils.extension

import com.example.data.service.response.ImageResponse
import com.example.domain.utils.Constants.DOT

fun ImageResponse.getURL(): String {
    return "${this.path}$DOT${this.extension}"
}
