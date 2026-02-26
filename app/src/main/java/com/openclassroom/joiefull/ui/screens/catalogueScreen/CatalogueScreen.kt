package com.openclassroom.joiefull.ui.screens.catalogueScreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.ui.Composable.DetailRows
import com.openclassroom.joiefull.ui.Composable.LikesDisplay
import com.openclassroom.joiefull.ui.theme.JoiefullTheme




@Composable
fun CatalogueScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CatalogueViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit,
) {

    val catalogue by viewModel.catalogue.collectAsStateWithLifecycle()

    LazyColumn (modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
    ) {
        catalogue.forEach { (category, products) ->
            item {
                Text(
                    text = category,
                    fontSize = 22.sp,
                    fontWeight = SemiBold,
                    modifier = Modifier
                        .padding(8.dp)
                        .semantics{heading()}
                        .focusable()
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    products.forEach { product ->
                        item {
                            ProductCard(
                                product = product,
                                onProductClick = onProductClick,
                                onLikeClick = { viewModel.onLikeClick(product) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(modifier: Modifier = Modifier, product: Product, onProductClick: (Product) -> Unit = {}, onLikeClick: (Product) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .width(198.dp)
            .clickable(enabled = true, onClick = {onProductClick(product)}, onClickLabel = "Voir le détail du produit")
            .clearAndSetSemantics {
                contentDescription = buildString {
                    append(product.name)
                    if (product.rate != null) append ("Noté ${product.rate} étoiles,")
                    append(", ${product.currentPrice}€")
                    if (product.originalPrice != product.currentPrice) append(", en reduction")
                    append(", ${product.likes} mentions j'aime.")
                }
                customActions = listOf(
                    CustomAccessibilityAction(
                        label = "Ajouter au likes", //TODO rendre dynamique
                        action = {
                            onLikeClick(product)
                            true
                        }
                    ),
                    CustomAccessibilityAction(
                        label = "Voir le détail du produit",
                        action = {
                            onProductClick(product)
                            true
                        }
                    )
                )
            },
    ) {
        Box(
            modifier = Modifier
                .size(198.dp)
        ) {
            AsyncImage(
                model = product.pictureUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )

            LikesDisplay(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(11.dp),
                likes = product.likes,
                onLikeClick = { onLikeClick(product) }
            )
        }
        DetailRows(product)

    }
}


@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    val product = Product(
        id = 0,
        pictureUrl = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/accessories/1.jpg",
        description = "Sac à main orange posé sur une poignée de porte",
        name = "Sac à main orange",
        category = "ACCESSORIES",
        likes = 56,
        currentPrice = 69.99,
        originalPrice = 120.99,
        rate = 4.5
    )
    JoiefullTheme {
        ProductCard(modifier = Modifier, product = product, onProductClick = {}, onLikeClick = {})
    }
}