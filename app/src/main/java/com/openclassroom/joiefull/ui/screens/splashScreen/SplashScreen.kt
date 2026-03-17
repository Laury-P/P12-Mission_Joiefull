package com.openclassroom.joiefull.ui.screens.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.openclassroom.joiefull.R
import com.openclassroom.joiefull.ui.theme.JoiefullTheme
import com.openclassroom.joiefull.ui.theme.Orange


@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel(), isTablet: Boolean) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state == SplashUiState.Ready) {
            navController.navigate("catalogue_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }
    when (state) {
        SplashUiState.Ready -> {}
        SplashUiState.Loading -> {
            SplashContent(isTablet = isTablet)
        }

        is SplashUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center){
                Text(
                    text = "Erreur : ${(state as SplashUiState.Error).message}",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp),
                )
            }


        }
    }
}


@Composable
fun SplashContent(isTablet: Boolean) {
    Box(
        modifier = Modifier
            .background(Orange)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        // Adaptation de la taille du logo par rapport à la taille de l'écran
        val logoFraction = if (isTablet) 0.5f else 0.3f

        Image(
            painter = painterResource(id = R.drawable.ic_app_name),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(logoFraction)
                .aspectRatio(4f)
        )

    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    JoiefullTheme {
        SplashContent(isTablet = false)
    }
}
