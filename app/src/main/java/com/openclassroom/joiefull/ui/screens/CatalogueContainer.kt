package com.openclassroom.joiefull.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.ui.screens.catalogueScreen.CatalogueScreen
import com.openclassroom.joiefull.ui.screens.detailScreen.DetailScreen

@Composable
fun CatalogueContainer(
    navController: NavController,
    isTablet: Boolean
) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    if (isTablet) {
        Row(Modifier.fillMaxSize()) {
            CatalogueScreen(
                navController = navController,
                onProductClick = { product -> selectedProduct = product },
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = 32.dp)
            )

            Spacer(modifier = Modifier.width(31.dp))

            selectedProduct?.let { product ->
                DetailScreen(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(end = 32.dp),
                    productId = product.id,
                    navController = navController,
                    isTablet = isTablet
                )

            } ?: DetailPlaceholder(modifier = Modifier.weight(0.4f).padding(end = 32.dp))

        }
    } else {
        CatalogueScreen(
            navController = navController,
            onProductClick = { product ->
            navController.navigate("detail_screen/${product.id}") },
            modifier = Modifier.padding(start = 16.dp)
        )
    }

}

@Composable
fun DetailPlaceholder(modifier: Modifier = Modifier) {
    Box (modifier = modifier.fillMaxSize().background(Color.LightGray)) {
        Text("Selectionnez un produit")
    }
}