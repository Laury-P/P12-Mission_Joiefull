package com.openclassroom.joiefull.ui.screens

import androidx.navigation.NamedNavArgument

sealed class Screen (
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
){
    object SplashScreen: Screen("splash_screen")
    object CatalogueScreen: Screen("catalogue_screen")
    object DetailScreen: Screen("detail_screen")
}



