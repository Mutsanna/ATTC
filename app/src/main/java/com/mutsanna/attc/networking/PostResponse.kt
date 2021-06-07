package com.mutsanna.attc.networking

import com.google.gson.annotations.SerializedName

data class PostResponse (
    val filename: String?,
    val prediction: String?,
    val success: Boolean
    )