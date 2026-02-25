package com.openclassroom.joiefull.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun DetermineIsTablet(): Boolean {
    val configuration = LocalConfiguration.current
    return (configuration.screenWidthDp >= 600)
}