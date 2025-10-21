package com.olvera.foodappkotlin.repository.auth

import retrofit2.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.olvera.foodappkotlin.data.FoodApi
import com.olvera.foodappkotlin.model.request.RegisterRequest
import com.olvera.foodappkotlin.model.response.Response
import com.olvera.foodappkotlin.util.NetworkResult
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: FoodApi
) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun register(request: RegisterRequest): NetworkResult<Response> {
        return try {
            val apiResponse = api.register(request)

            // Your API might return a 2xx code but still have a business logic "error" message.
            // This handles both successful responses and error responses from the server.
            NetworkResult.Success(apiResponse)

        } catch (e: Exception) {
            when (e) {
                // This specifically catches HTTP errors like 400, 409, 500, etc.
                is HttpException -> {
                    // Try to parse the detailed error message from the server's response body.
                    val errorResponse = e.response()?.errorBody()?.string()
                    if (!errorResponse.isNullOrBlank()) {
                        // If the backend sends a clear message, use it.
                        // You might need to parse this if it's JSON. For now, let's assume it's a simple string.
                        // Example: "{\"message\":\"Email already exists\"}"
                        // A more robust solution would use a JSON parser here.
                        Log.e("AuthRepository", "HTTP Error Body: $errorResponse")
                        NetworkResult.Error(errorResponse)
                    } else {
                        // Fallback if the error body is empty.
                        NetworkResult.Error("HTTP Error ${e.code()}: ${e.message()}")
                    }
                }
                // This catches network errors like no internet connection.
                is IOException -> {
                    Log.e("AuthRepository", "Network Error: ${e.message}", e)
                    NetworkResult.Error("Network error. Please check your connection.")
                }
                // Catch-all for any other unexpected exceptions.
                else -> {
                    Log.e("AuthRepository", "Unknown Error: ${e.message}", e)
                    NetworkResult.Error("An unknown error occurred.")
                }
            }
        }
    }
}