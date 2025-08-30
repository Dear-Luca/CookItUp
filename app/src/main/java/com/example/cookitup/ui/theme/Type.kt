package com.example.cookitup.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Responsive Typography
@Composable
fun responsiveTypography() = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = getBodyTextSize(),
        lineHeight = (getBodyTextSize().value * 1.5).sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = getTitleTextSize(),
        lineHeight = (getTitleTextSize().value * 1.3).sp,
        letterSpacing = 0.15.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = getHeadlineTextSize(),
        lineHeight = (getHeadlineTextSize().value * 1.2).sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (getBodyTextSize().value - 2).sp,
        lineHeight = (getBodyTextSize().value * 1.4).sp,
        letterSpacing = 0.25.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = getBottomBarTextSize(),
        lineHeight = (getBottomBarTextSize().value * 1.4).sp,
        letterSpacing = 0.5.sp
    )
)

// Fallback static typography for non-composable contexts
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
