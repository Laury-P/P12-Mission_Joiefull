package com.openclassroom.joiefull.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
        Box(modifier = modifier
            .background(Orange),
            contentAlignment = Alignment.Center,
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_app_name),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .width(199.dp)
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
