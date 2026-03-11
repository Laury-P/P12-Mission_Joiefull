package com.openclassroom.joiefull.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.openclassroom.joiefull.ui.theme.JoiefullTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import com.openclassroom.joiefull.ui.screens.CatalogueContainer
import com.openclassroom.joiefull.ui.screens.ScreenRoutes
import com.openclassroom.joiefull.ui.screens.detailScreen.DetailScreen
import com.openclassroom.joiefull.ui.screens.splashScreen.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )
        setContent {
            val navController = rememberNavController()
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val isTablet = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

            JoiefullTheme (isTablet = isTablet) {
                JoiefullNavHost(navController = navController, isTablet = isTablet)
            }
        }
    }

}

@Composable
fun JoiefullNavHost(navController: NavHostController, isTablet: Boolean) {

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SplashScreen.route
    ) {
        composable(route = ScreenRoutes.SplashScreen.route) {
            SplashScreen(navController = navController, isTablet=isTablet)
        }
        composable(route = ScreenRoutes.CatalogueScreen.route) {
           CatalogueContainer(navController = navController, isTablet = isTablet)
        }

        if (!isTablet){
            composable(
                route = ScreenRoutes.DetailScreen.route,
                arguments = ScreenRoutes.DetailScreen.navArguments
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
                DetailScreen(productId = productId, navController = navController, isTablet = isTablet)
            }
        }
    }
}