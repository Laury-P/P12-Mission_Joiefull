package com.openclassroom.joiefull.ui.composable_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp

@Composable
fun LikesDisplay(modifier: Modifier = Modifier, likes: Int,isProductLikedByUser: Boolean, onLikeClick: () -> Unit = {}, textStyle: TextStyle) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(8.dp, 4.dp)
            .clickable(enabled = true, onClick = { onLikeClick() })
            .semantics{hideFromAccessibility()},
    ) {

        val iconSize = with(LocalDensity.current) { textStyle.fontSize.toDp() }

        Icon(
            imageVector = if(isProductLikedByUser) Icons.Filled.Favorite else  Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = if (isProductLikedByUser) Color.Red else Color.Black,
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = likes.toString(),
            style = textStyle,
            fontWeight = SemiBold)
    }
}