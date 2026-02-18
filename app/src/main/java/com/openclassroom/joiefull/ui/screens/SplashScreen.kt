package com.openclassroom.joiefull.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openclassroom.joiefull.R
import com.openclassroom.joiefull.ui.theme.JoiefullTheme
import com.openclassroom.joiefull.ui.theme.Orange


@Composable
fun SplashScreen(modifier: Modifier = Modifier) {

    BoxWithConstraints(
        modifier = modifier
            .background(Orange),
        contentAlignment = Alignment.Center,
    ) {
        // Adaptation de la taille du logo par rapport à la taille de l'écran
        val logoFraction = if (maxWidth < 600.dp) 0.5f else 0.3f
        val logoWidth = (maxWidth * logoFraction)
        val logoHeight = logoWidth / 4f

        Image(
            painter = painterResource(id = R.drawable.ic_app_name),
            contentDescription = null,
            modifier = Modifier
                .width(logoWidth)
                .height(logoHeight)
        )

    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    JoiefullTheme {
        SplashScreen()
    }
}
