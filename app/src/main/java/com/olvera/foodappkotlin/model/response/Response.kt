package com.olvera.foodappkotlin.model.response

import java.io.Serializable

data class Response(
    val statusCode: Int,
    val message: String,
    val data: Any?,
    val meta: Map<String, Serializable> = emptyMap()
)
