package com.example.cookitup.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun getScreenWidth(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp
}

@Composable
fun getScreenHeight(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp.dp
}

@Composable
fun isSmallScreen(): Boolean {
    val screenWidth = getScreenWidth()
    return screenWidth < 450.dp
}

// Responsive text sizes
@Composable
fun getBottomBarTextSize(): TextUnit {
    return if (isSmallScreen()) {
        10.sp // Smaller text for small screens
    } else {
        12.sp // Normal text for larger screens
    }
}

@Composable
fun getBodyTextSize(): TextUnit {
    return if (isSmallScreen()) {
        14.sp
    } else {
        16.sp
    }
}

@Composable
fun getTitleTextSize(): TextUnit {
    return if (isSmallScreen()) {
        18.sp
    } else {
        22.sp
    }
}

@Composable
fun getHeadlineTextSize(): TextUnit {
    return if (isSmallScreen()) {
        24.sp
    } else {
        28.sp
    }
}
