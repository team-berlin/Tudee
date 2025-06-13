package com.example.tudee.designsystem.theme.textstyle

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tudee.R

val LocalTudeeTextStyle = staticCompositionLocalOf { TudeeTextStyle }

val nunito = FontFamily(
    Font(R.font.nunito_regular, weight = FontWeight.Normal),
    Font(R.font.nunito_medium, weight = FontWeight.Medium),
    Font(R.font.nunito_semibold, weight = FontWeight.SemiBold),
    Font(R.font.nunito_bold, weight = FontWeight.Bold),
    Font(R.font.nunito_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.nunito_black, weight = FontWeight.Black),
    Font(R.font.nunito_light, weight = FontWeight.Light),
)
val TudeeTextStyle = TudeeTextStyles(
    headline = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 30.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp
        )
    ),
    title = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 22.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp
        )
    ),
    body = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 22.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 17.sp
        )
    ),
    label = SizedTextStyle(
        large = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 19.sp
        ),
        medium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 14.sp
        ),
        small = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    )
)