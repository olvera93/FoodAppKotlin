package com.olvera.foodappkotlin.state

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val roles: List<String> = emptyList(),
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)
