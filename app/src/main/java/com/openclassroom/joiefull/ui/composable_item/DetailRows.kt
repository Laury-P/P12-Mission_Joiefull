package com.openclassroom.joiefull.ui.composable_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.ui.theme.JoiefullTheme

@Composable
fun DetailRows(product: Product, modifier: Modifier = Modifier, textStyle: TextStyle){
    val dims = JoiefullTheme.dimensions
    Column(modifier = modifier.padding(top = dims.smallPadding)){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                text = product.name,
                style = textStyle,
                fontWeight = SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            RateDisplay(rating = product.rate ?: 0.0, textStyle = textStyle)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = product.currentPrice.toString() + "€",
                style = textStyle)

            if (product.currentPrice != product.originalPrice){
                Text(
                    text = product.originalPrice.toString() + "€",
                    style = textStyle,
                    color = Color.Black.copy(alpha = 0.7f),
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
    }

}