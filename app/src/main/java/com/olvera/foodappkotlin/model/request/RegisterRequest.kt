package com.olvera.foodappkotlin.model.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val address: String,
    val phoneNumber: String,
    val roles: List<String>
)