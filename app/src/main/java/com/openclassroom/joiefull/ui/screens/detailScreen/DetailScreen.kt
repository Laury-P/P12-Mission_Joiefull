package com.openclassroom.joiefull.ui.screens.detailScreen

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
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
import com.openclassroom.joiefull.util.DataState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    productId: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
    isTablet: Boolean
) {
    val dims = JoiefullTheme.dimensions
    val showShareDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val user =
        User(0, "Dupont", "Mariette", "https://xsgames.co/randomusers/assets/avatars/female/9.jpg")

    val loadState by viewModel.isCatalogueReady.collectAsStateWithLifecycle()

    val product by viewModel.getProduct(productId).collectAsStateWithLifecycle()
    val currentProduct = product

    when (loadState) {
        is DataState.Error -> {
            Log.d("DetailScreen", "Error")

            LaunchedEffect(loadState) {
                Toast.makeText(
                    context,
                    "Une erreur est survenue",
                    Toast.LENGTH_SHORT
                ).show()
                if (!isTablet) {
                    navController.popBackStack()
                }
            }
        }

        is DataState.Loading -> {
            LoadingView()
        }

        is DataState.Success -> {
            if (currentProduct == null) {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        "Le produit n'existe pas",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (!isTablet) {
                        navController.popBackStack()
                    }
                }
            } else {
                LaunchedEffect(productId) { viewModel.loadComments(productId) }

                val commentsState by viewModel.commentUiState.collectAsStateWithLifecycle()
                val comments = commentsState.comments

                DetailContent(
                    modifier = modifier.padding(horizontal = dims.screenPadding),
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
                    isLoading = commentsState.isLoading,
                    onShareClick = { showShareDialog.value = true }
                )
            }
        }
    }


    if (showShareDialog.value) {
        val userComment = remember { mutableStateOf("") }
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = { showShareDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        shareProduct(context = context, product, userComment.value)
                        showShareDialog.value = false
                    },
                    content = {
                        Text("Partager")
                    }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { showShareDialog.value = false },
                    content = {
                        Text("Annuler")
                    }
                )
            },
            title = {
                Text("Partager ce produit")
            },
            text = {
                OutlinedTextField(
                    value = userComment.value,
                    onValueChange = { userComment.value = it },
                    label = { Text("Ajouter un commentaire") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

fun shareProduct(context: Context, product: Product?, comment: String) {
    if (product == null) return
    val deepLink = "joiefull://details/${product.id}"

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "$comment\n\nVoir le produit : $deepLink")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, product.name)
    context.startActivity(shareIntent)
}

@Composable
fun LoadingView() {
    Text("Chargement en cours...")
    CircularProgressIndicator()
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
    isLoading: Boolean,
    onShareClick: () -> Unit
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
                goBackClick = goBackClick,
                onShareClick = onShareClick,
            )
        }
        item {
            LeaveComment(
                user = user,
                modifier = Modifier,
                onCommentSubmit = onCommentSubmit
            )
        }
        if (isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.semantics {
                        contentDescription = "Chargement des commentaires"
                    })
                }
            }
        }

        items(comments ?: emptyList()) {
            CommentSection(comment = it, modifier = Modifier)
        }
    }
}

@Composable
fun ProductDetail(
    product: Product,
    onLikeClick: (Product) -> Unit,
    isTablet: Boolean,
    goBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    val dims = JoiefullTheme.dimensions
    Column(modifier = Modifier) {
        Box(modifier = Modifier.semantics(mergeDescendants = true) {
            contentDescription = buildString {
                append(product.description)
                append(". ${product.likes} mentions j'aime. ${if (product.isLikedByCurrentUser) "J'aime déjà" else "Pas encore aimer"}")
            }
            customActions = listOf(
                CustomAccessibilityAction(
                    label = if (product.isLikedByCurrentUser) "Retirer des likes" else "Ajouter aux likes",
                    action = {
                        onLikeClick(product)
                        true
                    }
                ),
                CustomAccessibilityAction(
                    label = "Partager ce produit",
                    action = {
                        onShareClick()
                        true
                    }
                ),
                CustomAccessibilityAction(
                    label = "Retour à la liste des produits",
                    action = {
                        goBackClick()
                        true
                    }
                )
            )
        }) {
            AsyncImage(
                model = product.pictureUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dims.roundedCornerShape))
                    .aspectRatio(dims.detailScreenImageRatio),
                contentScale = ContentScale.Crop


            )
            if (!isTablet) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Flèche retour",
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(dims.iconPadding)
                        .clickable(
                            true,
                            onClick = goBackClick,
                            onClickLabel = "Retour à la liste des produits"
                        )
                        .semantics { role = Role.Button }
                )
            }
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Partager",
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(dims.iconPadding)
                    .clickable(
                        true,
                        onClick = onShareClick,
                        onClickLabel = "Partager ce produit"
                    )
                    .semantics { role = Role.Button }


            )
            LikesDisplay(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(dims.iconPadding),
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
    val dims = JoiefullTheme.dimensions
    Column(modifier = modifier.clearAndSetSemantics {
        contentDescription = buildString {
            append(product.name)
            if (product.rate != null) append("Noté ${product.rate} étoiles,")
            append(", ${product.currentPrice} euros")
            if (product.originalPrice != product.currentPrice) append(", en reduction. Prix original: ${product.originalPrice}€")
        }
    }) {
        Spacer(Modifier.height(dims.defaultBigPadding))
        DetailRows(product, modifier = modifier, textStyle = MaterialTheme.typography.titleMedium)
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(top = dims.defaultMediumPadding)
        )
    }

}

@Composable
fun LeaveComment(
    modifier: Modifier = Modifier,
    user: User,
    onCommentSubmit: (String, Int) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    var ratingState by remember { mutableIntStateOf(0) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val dims = JoiefullTheme.dimensions


    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(top = dims.defaultBigPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.profilePicture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = dims.iconPadding)
                    .clip(shape = CircleShape)
                    .size(dims.profilePictureSize)
                    .semantics {
                        contentDescription = "Photo de profil de ${user.firstname} ${user.name}"
                    }
            )

            RatingBar(
                rating = ratingState,
                onRatingChange = { ratingState = it },
                size = dims.starSizeRating,
            )
        }

        OutlinedTextField(
            onValueChange = { textState = it },
            value = textState,
            modifier = Modifier
                .padding(top = dims.doublePadding)
                .fillMaxWidth(),
            placeholder = { Text("Partagez ici vos impressions sur cette pièce") }
        )

        Button(
            onClick = {
                if (textState.isNotBlank()) {
                    onCommentSubmit(textState, ratingState)
                    keyboardController?.hide()
                    textState = ""
                    ratingState = 0
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = dims.doublePadding)
                .semantics {
                    onClick(label = "Envoyer le commentaire") {
                        false
                    }
                },
        ) {
            Text(text = "Envoyer")
        }
    }

}


@Composable
fun CommentSection(comment: Comment, modifier: Modifier = Modifier) {
    val dims = JoiefullTheme.dimensions
    Column(
        modifier = modifier
            .clearAndSetSemantics {
                contentDescription =
                    "Commentaire de ${comment.userName}. Note : ${comment.rate}. ${comment.comment}"
            }
    ) {
        HorizontalDivider(
            Modifier.padding(vertical = dims.doublePadding),
            DividerDefaults.Thickness,
            DividerDefaults.color
        )
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = comment.userProfilePicture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = dims.iconPadding)
                    .clip(shape = CircleShape)
                    .size(dims.profilePictureSize)

            )
            Column(modifier = Modifier) {
                Text(text = comment.userName, style = MaterialTheme.typography.titleMedium)
                RatingBar(rating = comment.rate, isDisplayOnly = true, size = dims.starSizeRating)
            }
        }
        Text(
            text = comment.comment,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = dims.defaultMediumPadding)
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
            isLoading = true,
            onShareClick = {}
        )
    }
}