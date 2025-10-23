package com.olvera.foodappkotlin.state

 sealed class SignUpEvent {

     data class NameChanged(val name: String) : SignUpEvent()
     data class EmailChanged(val email: String) : SignUpEvent()
     data class PasswordChanged(val password: String) : SignUpEvent()

     data class AddressChanged(val address: String): SignUpEvent()

     data class PhoneNumberChanged(val phoneNumber: String): SignUpEvent()

     data class RolesChanged(val roles: List<String>): SignUpEvent()

     object SignUp : SignUpEvent()
}