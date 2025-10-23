package com.olvera.foodappkotlin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.olvera.foodappkotlin.presentations.onBoarding.OnBoardingScreen
import com.olvera.foodappkotlin.presentations.signUp.SignUpScreen
import com.olvera.foodappkotlin.presentations.signUp.SignUpViewModel

@Composable
fun NavManager(
    modifier: Modifier = Modifier,
    signUpViewModel: SignUpViewModel
) {
     val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            SignUpScreen(modifier, signUpViewModel)
        }
    }
}