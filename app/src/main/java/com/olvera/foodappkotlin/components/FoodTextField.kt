package com.olvera.foodappkotlin.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoodTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    isSingleLine: Boolean = true,
    isPassword: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {


    Column(modifier = modifier) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            letterSpacing = 0.35.sp
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            placeholder = { Text(text = placeholder) },
            shape = MaterialTheme.shapes.large,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = isSingleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FoodTextFieldPreview() {
    FoodTextField(
        label = "Name",
        value = "",
        placeholder = "Enter your name",
        onValueChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FoodTextFieldErrorPreview() {
    FoodTextField(
        label = "Email",
        value = "invalid-email",
        placeholder = "Enter your email",
        onValueChange = {},
        isError = true,
        errorMessage = "Invalid email format"
    )
}

@Preview(showBackground = true)
@Composable
fun FoodTextFieldPasswordPreview() {
    FoodTextField(
        label = "Password",
        value = "Password",
        placeholder = "Enter your password",
        onValueChange = {},
        isPassword = true
    )
}