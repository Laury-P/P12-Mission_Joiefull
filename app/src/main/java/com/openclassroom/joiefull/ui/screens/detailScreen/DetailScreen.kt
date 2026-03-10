package com.openclassroom.joiefull.ui.screens.detailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.openclassroom.joiefull.domain.Comment
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.domain.User
import com.openclassroom.joiefull.ui.composable_item.DetailRows
import com.openclassroom.joiefull.ui.composable_item.LikesDisplay
import com.openclassroom.joiefull.ui.composable_item.RatingBar
import com.openclassroom.joiefull.ui.theme.JoiefullTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    productId: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
    isTablet: Boolean
) {

    val product by viewModel.getProduct(productId).collectAsStateWithLifecycle()
    val currentProduct = product

    LaunchedEffect(productId) { viewModel.loadComments(productId) }
    val commentsState by viewModel.commentsUiState.collectAsStateWithLifecycle()
    val comments = commentsState.comments

    val user =
        User(0, "Dupont", "Mariette", "https://xsgames.co/randomusers/assets/avatars/female/9.jpg")

    if (currentProduct == null) {
        if (!isTablet) LaunchedEffect(Unit) { navController.popBackStack() }
    } else DetailContent(
        modifier = modifier,
        product = currentProduct,
        user = user,
        comments = comments,
        isTablet = isTablet,
        goBackClick = { navController.popBackStack() },
        onLikeClick = { viewModel.onLikeClick(currentProduct) },
        onCommentSubmit = { comment, rate ->
            viewModel.addComment(
                Comment(
                    idComment = 0,
                    idProduct = currentProduct.id,
                    idUser = user.id,
                    userName = user.firstname + " " + user.name,
                    userProfilePicture = user.profilePicture,
                    comment = comment,
                    rate = rate
                )
            )
        },
        isLoading = commentsState.isLoading
    )

}

@Composable
fun DetailContent(
    product: Product,
    user: User,
    comments: List<Comment>?,
    modifier: Modifier = Modifier,
    onLikeClick: (Product) -> Unit = {},
    isTablet: Boolean,
    goBackClick: () -> Unit = {},
    onCommentSubmit: (String, Int) -> Unit,
    isLoading: Boolean
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        item {
            ProductDetail(
                product,
                onLikeClick = { onLikeClick(product) },
                isTablet = isTablet,
                goBackClick = goBackClick
            )
        }
        item {
            LeaveComment(
                user = user,
                modifier = Modifier.padding(horizontal = 16.dp),
                onCommentSubmit = onCommentSubmit
            )
        }
        if (isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        items(comments ?: emptyList()) {
            CommentSection(comment = it, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun ProductDetail(
    product: Product,
    onLikeClick: (Product) -> Unit,
    isTablet: Boolean,
    goBackClick: () -> Unit
) {
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
            if (!isTablet) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(17.dp)
                        .clickable(
                            true,
                            onClick = goBackClick,
                            onClickLabel = "Retour à la liste des produits"
                        )
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
                isProductLikedByUser = product.isLikedByCurrentUser,
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
fun LeaveComment(
    modifier: Modifier = Modifier,
    user: User,
    onCommentSubmit: (String, Int) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    var ratingState by remember { mutableIntStateOf(0) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.profilePicture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(shape = CircleShape)
                    .size(40.dp)
            )

            RatingBar(
                rating = ratingState,
                onRatingChange = { ratingState = it },
            )
        }

        OutlinedTextField(
            onValueChange = { textState = it },
            value = textState,
            modifier = Modifier.padding(top = 20.dp),
            placeholder = { Text("Partagez ici vos impressions sur cette pièce") }
        )

        Button(
            onClick = {
                if (textState.isNotBlank()) {
                    onCommentSubmit(textState, ratingState)
                    textState = ""
                    ratingState = 0
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp),
        ) {
            Text(text = "Envoyer")
        }
    }

}


@Composable
fun CommentSection(comment: Comment, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        HorizontalDivider(
            Modifier.padding(vertical = 24.dp),
            DividerDefaults.Thickness,
            DividerDefaults.color
        )
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = comment.userProfilePicture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(shape = CircleShape)
                    .size(40.dp)

            )
            Column(modifier = Modifier) {
                Text(text = comment.userName, style = MaterialTheme.typography.titleMedium)
                RatingBar(rating = comment.rate, isDisplayOnly = true)
            }
        }
        Text(
            text = comment.comment,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 12.dp)
        )
    }

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
        DetailContent(
            product,
            comments = null,
            user = user,
            isTablet = false,
            goBackClick = {},
            onCommentSubmit = { _, _ -> },
            isLoading = true
        )
    }
}