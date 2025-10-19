package com.olvera.foodappkotlin.data

import com.olvera.foodappkotlin.model.request.RegisterRequest
import com.olvera.foodappkotlin.model.response.Response
import com.olvera.foodappkotlin.util.Constants.ENDPOINT_SIGN_UP
import retrofit2.http.Body
import retrofit2.http.POST

interface FoodApi {

    @POST(ENDPOINT_SIGN_UP)
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response
}