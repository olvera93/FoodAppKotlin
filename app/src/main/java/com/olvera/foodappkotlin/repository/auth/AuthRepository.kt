package com.olvera.foodappkotlin.repository.auth

import android.util.Log
import com.olvera.foodappkotlin.data.FoodApi
import com.olvera.foodappkotlin.model.request.RegisterRequest
import com.olvera.foodappkotlin.model.response.Response
import com.olvera.foodappkotlin.util.NetworkResult
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: FoodApi
) {

    suspend fun register(request: RegisterRequest): NetworkResult<Response> {
        val response = try {
            api.register(request)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error occurred while registering: ${e.message}", e)
            return NetworkResult.Error("An error occurred while registering")
        }
        return NetworkResult.Success(response)
    }
}