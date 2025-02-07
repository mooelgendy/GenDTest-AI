package com.gendtest.ai.model

import com.google.gson.annotations.SerializedName

data class OllamaRequest(
    @SerializedName("model") val model: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("stream") val stream: Boolean = false
)