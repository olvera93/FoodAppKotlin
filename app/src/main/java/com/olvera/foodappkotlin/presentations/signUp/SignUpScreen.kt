package com.olvera.foodappkotlin.presentations.signUp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
    }


}