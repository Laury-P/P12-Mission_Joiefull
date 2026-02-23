package com.openclassroom.joiefull.ui.screens.catalogueScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.ui.theme.JoiefullTheme
import com.openclassroom.joiefull.ui.theme.Orange




@Composable
fun CatalogueScreen(navController: NavController, viewModel: CatalogueViewModel = hiltViewModel()) {

    val catalogue by viewModel.catalogue.collectAsStateWithLifecycle()

    LazyColumn (modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(start = 16.dp)
    ) {
        catalogue.forEach { (category, products) ->
            item {
                Text(
                    text = category,
                    fontSize = 22.sp,
                    fontWeight = SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    products.forEach { product ->
                        item {
                            ProductCard(product = product)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(modifier: Modifier = Modifier, product: Product) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier.width(198.dp),
    ) {
        Box(
            modifier = Modifier
                .size(198.dp)
        ) {
            AsyncImage(
                model = product.pictureUrl,
                contentDescription = product.description,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )

            LikesDisplay(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(11.dp),
                likes = product.likes)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name, fontSize = 14.sp, fontWeight = SemiBold
            )
            RatingItem(rating = product.rate ?: 0.0)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = product.currentPrice.toString() + "€", fontSize = 14.sp)
            Text(
                text = product.originalPrice.toString() + "€",
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.7f),
                textDecoration = TextDecoration.LineThrough
            )
        }

    }
}

@Composable
fun LikesDisplay(modifier: Modifier = Modifier, likes: Int) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(8.dp, 4.dp)
    ) {
        val fontSize = 14.sp
        val iconSize = with(LocalDensity.current) { fontSize.toDp() }

        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            Modifier
                .size(iconSize)
                .align(Alignment.CenterVertically)

        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = likes.toString(), fontSize = fontSize, fontWeight = SemiBold)
    }
}

@Composable
fun RatingItem(rating: Double) {
    Row {
        val fontSize = 14.sp
        val iconSize = with(LocalDensity.current) { fontSize.toDp() }

        Icon(
            imageVector = Icons.Filled.Star,
            tint = Orange,
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = rating.toString(), fontSize = fontSize)
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
        ProductCard(modifier = Modifier, product = product)
    }
}