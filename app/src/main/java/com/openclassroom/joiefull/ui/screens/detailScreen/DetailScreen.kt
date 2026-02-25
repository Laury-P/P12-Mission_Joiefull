package com.openclassroom.joiefull.ui.screens.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.w3c.dom.Text

@Composable
fun DetailScreen (modifier: Modifier = Modifier, productId: Int) {
    Box (modifier = modifier.fillMaxSize().background(Color.LightGray)) {
        Text(productId.toString())
    }

}