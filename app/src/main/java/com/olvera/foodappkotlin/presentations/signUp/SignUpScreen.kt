package com.olvera.foodappkotlin.presentations.signUp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.olvera.foodappkotlin.components.FoodTextField
import com.olvera.foodappkotlin.state.SignUpEvent
import com.olvera.foodappkotlin.util.NetworkResult

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel
) {

    val signUp by viewModel.registrationStatus.collectAsState()
    val signUpState by viewModel.signUpState.collectAsState()


    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // React to changes in the registration status
    LaunchedEffect(key1 = signUp) {
        when (signUp) {
            is NetworkResult.Success -> {
                // On success, trigger the navigation event
            }

            is NetworkResult.Error -> {
                // On error, show a toast with the message from the API
                val errorMessage = (signUp as NetworkResult.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

            else -> {
                // Do nothing for Idle or Loading states in this effect
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Create an Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        FoodTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Name",
            value = signUpState.name,
            placeholder = "Enter your full name",
            onValueChange = { newValue ->
                viewModel.onEvent(SignUpEvent.NameChanged(newValue))
            },
            isError = signUpState.nameError != null,
            errorMessage = signUpState.nameError ?: "",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        FoodTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Email",
            value = signUpState.email,
            placeholder = "Enter your email",
            onValueChange = { newValue ->
                viewModel.onEvent(SignUpEvent.EmailChanged(newValue))
            },
            isError = signUpState.emailError != null,
            errorMessage = signUpState.emailError ?: "",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        FoodTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Password",
            value = signUpState.password,
            placeholder = "Enter your password",
            onValueChange = { newValue ->
                viewModel.onEvent(SignUpEvent.PasswordChanged(newValue))
            },
            isError = signUpState.passwordError != null,
            errorMessage = signUpState.passwordError ?: "",
            isPassword = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    viewModel.onEvent(SignUpEvent.SignUp)
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        FoodTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Address",
            value = signUpState.address,
            placeholder = "Enter your address",
            onValueChange = { newValue ->
                viewModel.onEvent(SignUpEvent.AddressChanged(newValue))
            },
            isError = signUpState.addressError != null,
            errorMessage = signUpState.addressError ?: "",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    viewModel.onEvent(SignUpEvent.SignUp)
                }
            )

        )

        Spacer(modifier = Modifier.height(16.dp))

        FoodTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Phone Number",
            value = signUpState.phoneNumber,
            placeholder = "Enter your number",
            onValueChange = { newValue ->
                viewModel.onEvent(SignUpEvent.PhoneNumberChanged(newValue))
            },
            isError = signUpState.phoneNumberError != null,
            errorMessage = signUpState.phoneNumberError ?: "",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    viewModel.onEvent(SignUpEvent.SignUp)
                }
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sign Up Button
        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.onEvent(SignUpEvent.SignUp)
            },
            modifier = Modifier.fillMaxWidth(),
            // Disable the button while loading
            enabled = signUp !is NetworkResult.Loading
        ) {
            if (signUp is NetworkResult.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = "Sign Up")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Link to Login Screen
        Row {
            Text(text = "Already have an account? ")
            Text(
                text = "Login",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { }
            )
        }

    }
}
