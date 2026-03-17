package com.openclassroom.joiefull.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val PhoneTypography = Typography(
    bodyMedium = TextStyle(fontSize = 14.sp,),
    bodyLarge = TextStyle(fontSize = 16.sp,),
    labelSmall = TextStyle(fontSize = 14.sp,),
    labelLarge = TextStyle(fontSize = 14.sp,),
    titleMedium = TextStyle(fontSize = 18.sp,),
    headlineMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
)

val TabletTypography = Typography(
    bodyMedium = TextStyle(fontSize = 18.sp,),
    bodyLarge = TextStyle(fontSize = 20.sp,),
    labelSmall = TextStyle(fontSize = 14.sp,),
    labelLarge = TextStyle(fontSize = 18.sp,),
    titleMedium = TextStyle(fontSize = 22.sp,),
    headlineMedium = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
)


