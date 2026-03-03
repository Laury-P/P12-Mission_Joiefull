package com.openclassroom.joiefull.ui.composable_item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassroom.joiefull.ui.theme.Orange

@Composable
fun RateDisplay(rating: Double, textStyle: TextStyle) {
    Row {
        val iconSize = with(LocalDensity.current) { textStyle.fontSize.toDp() }

        Icon(
            imageVector = Icons.Filled.Star,
            tint = Orange,
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = rating.toString(),
            style = textStyle,)
    }
}