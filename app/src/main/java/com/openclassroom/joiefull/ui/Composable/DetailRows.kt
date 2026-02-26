package com.openclassroom.joiefull.ui.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassroom.joiefull.domain.Product

@Composable
fun DetailRows(product: Product){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = product.name,
            fontSize = 14.sp,
            fontWeight = SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
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