package com.olvera.foodappkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.olvera.foodappkotlin.navigation.NavManager
import com.olvera.foodappkotlin.presentations.signUp.SignUpViewModel
import com.olvera.foodappkotlin.ui.theme.FoodAppKotlinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val signUpViewModel: SignUpViewModel by viewModels()

        setContent {
            FoodAppKotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavManager(
                        modifier = Modifier.padding(innerPadding),
                        signUpViewModel = signUpViewModel
                    )
                }
            }
        }
    }
}
