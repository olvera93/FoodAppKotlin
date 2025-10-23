package com.olvera.foodappkotlin.presentations.signUp

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.foodappkotlin.model.request.RegisterRequest
import com.olvera.foodappkotlin.model.response.Response
import com.olvera.foodappkotlin.repository.auth.AuthRepository
import com.olvera.foodappkotlin.state.SignUpEvent
import com.olvera.foodappkotlin.state.SignUpState
import com.olvera.foodappkotlin.util.NetworkResult
import com.olvera.foodappkotlin.util.use_case.ValidateEmail
import com.olvera.foodappkotlin.util.use_case.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _registrationStatus = MutableStateFlow<NetworkResult<Response>>(NetworkResult.Idle())
    val registrationStatus = _registrationStatus.asStateFlow()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.NameChanged -> {
                _signUpState.update { it.copy(name = event.name) }
            }

            is SignUpEvent.EmailChanged -> {
                _signUpState.update { it.copy(email = event.email) }
            }

            is SignUpEvent.PasswordChanged -> {
                _signUpState.update { it.copy(password = event.password) }
            }

            is SignUpEvent.AddressChanged -> {
                _signUpState.update { it.copy(address = event.address) }
            }
            is SignUpEvent.PhoneNumberChanged -> {
                _signUpState.update { it.copy(phoneNumber = event.phoneNumber) }
            }
            is SignUpEvent.RolesChanged -> {
                _signUpState.update { it.copy(roles = event.roles) }
            }

            is SignUpEvent.SignUp -> {
                signUp()
            }


        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun signUp() {
        val state = _signUpState.value
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val nameResult = state.name.isNotBlank()
        val addressResult = state.address.isNotBlank()
        val phoneNumberResult = state.phoneNumber.isNotBlank()


        val hasError = listOf(emailResult, passwordResult).any { !it.successful } || !nameResult

        if (hasError) {
            _signUpState.update {
                it.copy(
                    nameError = if (!nameResult) "Name cannot be empty" else null,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    addressError = if (!addressResult) "Address cannot be empty" else null,
                    phoneNumberError = if (!phoneNumberResult) "Phone number cannot be empty" else null
                )
            }
            return
        }

        viewModelScope.launch {

            _registrationStatus.value = NetworkResult.Loading()
            val request = RegisterRequest(
                name = state.name,
                email = state.email,
                password = state.password,
                address = state.address,
                phoneNumber = state.phoneNumber,
                roles = state.roles
            )
            _registrationStatus.value = repository.register(request)
        }
    }
}
