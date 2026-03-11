package com.openclassroom.joiefull.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.ui.screens.catalogueScreen.CatalogueScreen
import com.openclassroom.joiefull.ui.screens.detailScreen.DetailScreen
import com.openclassroom.joiefull.ui.theme.JoiefullTheme

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
            )

            selectedProduct?.let { product ->
                DetailScreen(
                    modifier = Modifier
                        .weight(0.4f),
                    productId = product.id,
                    navController = navController,
                    isTablet = isTablet
                )

            } ?: DetailPlaceholder(modifier = Modifier.weight(0.4f))

        }
    } else {
        CatalogueScreen(
            navController = navController,
            onProductClick = { product ->
            navController.navigate("detail_screen/${product.id}") },
            modifier = Modifier
        )
    }

}

@Composable
fun DetailPlaceholder(modifier: Modifier = Modifier) {
    val dims = JoiefullTheme.dimensions

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(dims.screenPadding)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)), // Un gris très doux
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ShoppingCart, // Ou une icône de vêtement
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(dims.defaultSmallPadding))
            Text(
                text = "Sélectionnez un article\npour voir les détails",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}