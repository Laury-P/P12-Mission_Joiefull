package com.openclassroom.joiefull.ui.composable_item

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
    size: Int = 25,
) {
    Row(modifier = modifier) {
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
    size: Int,
    onClick: () -> Unit
) {
    val tint by animateColorAsState(
        targetValue = if (isActive) selectedColor else unselectedColor.copy(0.3f),
        label = "Rating color transition"
    )
    Icon(
        imageVector = if (isActive) Icons.Filled.Star else Icons.TwoTone.Star,
        contentDescription = if (isDisplayOnly) "Noter $ratingValue sur $maxRating" else null,
        tint = tint,
        modifier = Modifier
            .size(size.dp)
            .then(
                if (!isDisplayOnly) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onClick() }
                    )
                } else Modifier
            )
    )
}

@Preview
@Composable
fun RatingBarPreview() {
    RatingBar(rating = 3)
}