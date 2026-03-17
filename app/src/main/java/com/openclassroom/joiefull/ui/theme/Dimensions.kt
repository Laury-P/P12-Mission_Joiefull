package com.openclassroom.joiefull.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class JoieFullDimensions(
    /** Padding entre deux colonnes dans Catalogues */
    val defaultSmallPadding: Dp,

    val defaultMediumPadding: Dp,

    /** Padding entre photo et description dans Detail*/
    val defaultBigPadding: Dp,

    /** Padding entre Photo et description dans Catalogue */
    val smallPadding: Dp, //

    /** Padding entre PP et Text nouveaux commentaire */
    val doublePadding: Dp,

    /** Padding des icons de navigations dans la photo dans Detail et pour la photo de profil */
    val iconPadding: Dp,

    val screenPadding: Dp,

    val profilePictureSize: Dp,
    val starSizeRating: Dp,
    val productCardWidth: Dp,

    val detailScreenImageRatio: Float,
    val catalogueScreenImageRatio: Float,
    val roundedCornerShape: Dp,
    )

val compactDimensions = JoieFullDimensions(
    defaultSmallPadding = 8.dp,
    defaultMediumPadding = 12.dp,
    defaultBigPadding = 24.dp,
    smallPadding = 8.dp,
    doublePadding = 16.dp,
    iconPadding = 16.dp,
    screenPadding = 16.dp,
    profilePictureSize =39.dp,
    starSizeRating = 25.dp,
    productCardWidth = 198.dp,
    detailScreenImageRatio = 0.75f,
    catalogueScreenImageRatio = 1f,
    roundedCornerShape = 16.dp,
)

val tabletDimensions = JoieFullDimensions(
    defaultSmallPadding = 8.dp,
    defaultMediumPadding = 12.dp,
    defaultBigPadding = 24.dp,
    smallPadding = 12.dp,
    doublePadding = 24.dp,
    iconPadding = 20.dp,
    screenPadding = 32.dp,
    profilePictureSize = 43.dp,
    starSizeRating = 30.dp,
    productCardWidth = 234.dp,
    detailScreenImageRatio = 1f,
    catalogueScreenImageRatio = 0.9f,
    roundedCornerShape = 16.dp,
)

val LocalAppDimensions = staticCompositionLocalOf { compactDimensions }

