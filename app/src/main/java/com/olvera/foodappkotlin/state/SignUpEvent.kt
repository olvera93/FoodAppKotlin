package com.olvera.foodappkotlin.state

 sealed class SignUpEvent {

     data class NameChanged(val name: String) : SignUpEvent()
     data class EmailChanged(val email: String) : SignUpEvent()
     data class PasswordChanged(val password: String) : SignUpEvent()
     object SignUp : SignUpEvent()
}