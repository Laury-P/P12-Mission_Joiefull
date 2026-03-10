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

    val profilePictureSize: Dp,
    val starSizeRating: Dp,
    )

val compactDimensions = JoieFullDimensions(
    defaultSmallPadding = 8.dp,
    defaultMediumPadding = 12.dp,
    defaultBigPadding = 24.dp,
    smallPadding = 8.dp,
    doublePadding = 16.dp,
    iconPadding = 16.dp,
    profilePictureSize =39.dp,
    starSizeRating = 25.dp
)

val tabletDimensions = JoieFullDimensions(
    defaultSmallPadding = 8.dp,
    defaultMediumPadding = 12.dp,
    defaultBigPadding = 24.dp,
    smallPadding = 12.dp,
    doublePadding = 24.dp,
    iconPadding = 20.dp,
    profilePictureSize = 43.dp,
    starSizeRating = 30.dp
)

val LocalAppDimensions = staticCompositionLocalOf { compactDimensions }

