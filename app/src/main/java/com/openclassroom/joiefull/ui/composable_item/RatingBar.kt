package com.openclassroom.joiefull.ui.composable_item

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.openclassroom.joiefull.ui.theme.Orange

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    onRatingChange: (Int) -> Unit = {},
    isDisplayOnly: Boolean = false,
    maxRating: Int = 5,
    selectedColor: Color = Orange,
    unselectedColor: Color = Color.Gray,
    size: Dp,
) {
    Row(modifier = modifier.semantics(mergeDescendants = true) {
        contentDescription = if (isDisplayOnly) "Note : $rating sur $maxRating" else ""
    }
    ) {
        for (value in 1..maxRating) {
            StarIcon(
                ratingValue = value,
                maxRating = maxRating,
                isActive = value <= rating,
                isDisplayOnly = isDisplayOnly,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                size = size,
                onClick = { onRatingChange(value) }
            )
        }

    }


}

@Composable
fun StarIcon(
    ratingValue: Int,
    maxRating: Int,
    isActive: Boolean,
    isDisplayOnly: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    size: Dp,
    onClick: () -> Unit
) {
    val tint by animateColorAsState(
        targetValue = if (isActive) selectedColor else unselectedColor.copy(0.3f),
        label = "Rating color transition"
    )

    val modifierAction = if (!isDisplayOnly) {
        Modifier
            .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() },
                onClickLabel = "Noter $ratingValue sur $maxRating"
            )
            .semantics {
                contentDescription = "Etoile $ratingValue"
                role = Role.Button
            }
    } else Modifier
    Box(modifier = modifierAction,
        contentAlignment = Alignment.Center){
        Icon(
            imageVector = if (isActive) Icons.Filled.Star else Icons.TwoTone.Star,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }

}

@Preview
@Composable
fun RatingBarPreview() {
    RatingBar(rating = 3, onRatingChange = {}, size = 25.dp)
}
