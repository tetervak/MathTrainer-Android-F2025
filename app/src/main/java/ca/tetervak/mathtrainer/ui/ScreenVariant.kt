package ca.tetervak.mathtrainer.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class ScreenVariant {
    PHONE_VERTICAL, PHONE_HORIZONTAL, TABLET_VERTICAL, TABLET_HORIZONTAL
}

fun getScreenVariant(
    windowWidth: WindowWidthSizeClass,
    windowHeight: WindowHeightSizeClass
): ScreenVariant =
    when(windowWidth) {
        WindowWidthSizeClass.Compact -> ScreenVariant.PHONE_VERTICAL
        WindowWidthSizeClass.Medium -> when(windowHeight){
            WindowHeightSizeClass.Compact -> ScreenVariant.PHONE_HORIZONTAL
            else -> ScreenVariant.TABLET_VERTICAL
        }
        else -> when(windowHeight){
            WindowHeightSizeClass.Compact -> ScreenVariant.PHONE_HORIZONTAL
            WindowHeightSizeClass.Medium -> ScreenVariant.TABLET_HORIZONTAL
            else -> ScreenVariant.TABLET_VERTICAL
        }
    }