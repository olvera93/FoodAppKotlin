package com.olvera.foodappkotlin.repository.auth

import retrofit2.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.olvera.foodappkotlin.data.FoodApi
import com.olvera.foodappkotlin.model.request.RegisterRequest
import com.olvera.foodappkotlin.model.response.Response
import com.olvera.foodappkotlin.util.NetworkResult
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: FoodApi
) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun register(request: RegisterRequest): NetworkResult<Response> {
        return try {
            val apiResponse = api.register(request)
            NetworkResult.Success(apiResponse)

        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val errorResponse = e.response()?.errorBody()?.string()
                    val message = try {
                        if (!errorResponse.isNullOrBlank()) {
                            val json = JSONObject(errorResponse)
                            json.optString("message", "An unknown error occurred")
                        } else {
                            "HTTP Error ${e.code()}: ${e.message()}"
                        }
                    } catch (ex: JSONException) {
                        "An unknown error occurred"
                    }

                    Log.e("AuthRepository", "Backend Error Message: $message")
                    NetworkResult.Error(message)
                }

                is IOException -> {
                    Log.e("AuthRepository", "Network Error: ${e.message}", e)
                    NetworkResult.Error("Network error. Please check your connection.")
                }

                else -> {
                    Log.e("AuthRepository", "Unknown Error: ${e.message}", e)
                    NetworkResult.Error("An unknown error occurred.")
                }
            }}
    }
}