package com.openclassroom.joiefull.ui.screens.detailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.openclassroom.joiefull.domain.Comment
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.domain.User
import com.openclassroom.joiefull.ui.composable_item.DetailRows
import com.openclassroom.joiefull.ui.composable_item.LikesDisplay
import com.openclassroom.joiefull.ui.theme.JoiefullTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    productId: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController, isTablet: Boolean) {

    val product = viewModel.getProduct(productId)

    if (product == null) // navController.popBackStack() //TODO: gerer erreur de nav si ecran compact
    else DetailContent(modifier = modifier, product = product, user = User(0, "", "", null), comments = null, isTablet = isTablet)


}

@Composable
fun DetailContent(
    product: Product,
    user: User,
    comments: List<Comment>?,
    modifier: Modifier = Modifier,
    onLikeClick: (Product) -> Unit = {},
    isTablet: Boolean
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        item {
            ProductDetail(product, onLikeClick, isTablet = isTablet)
        }
        item {
            LeaveComment(product, user = user)
        }
        comments?.forEach { comment ->
            item {
                CommentSection(comment)
            }
        }
    }
}

@Composable
fun ProductDetail(product: Product, onLikeClick: (Product) -> Unit, isTablet: Boolean) {
    Column(modifier = Modifier.padding(16.dp)) {
        Box {
            val ratio = if (isTablet) 1f else 0.75f
            AsyncImage(
                model = product.pictureUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(ratio),
                contentScale = ContentScale.Crop


            )
            if (!isTablet){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(17.dp)
                )
            }
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = null,
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(17.dp)
            )
            LikesDisplay(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(11.dp),
                likes = product.likes,
                onLikeClick = { onLikeClick(product) },
                textStyle = MaterialTheme.typography.titleMedium
            )

        }
        DescriptionSection(product)
    }

}


@Composable
fun DescriptionSection(product: Product, modifier: Modifier = Modifier) {
    Spacer(Modifier.height(24.dp))
    DetailRows(product, modifier = modifier, textStyle = MaterialTheme.typography.titleMedium)
    Text(
        text = product.description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.padding(top = 12.dp)
    )
}

@Composable
fun LeaveComment(product: Product, modifier: Modifier = Modifier, user: User) {
}


@Composable
fun CommentSection(comment: Comment, modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
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

    val user = User(
        id = 1234,
        name = "Doe",
        firstname = "Jane",
        profilePicture = "fakeURL"
    )

    JoiefullTheme {
        DetailContent(product, comments = null, user = user, isTablet = false)
    }
}