package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorSegmentOverrideDarkSurface = androidx.compose.ui.graphics.Color(0xFF1E2120)

private val DarkColorScheme = darkColorScheme(
    primary = NatPrimary,
    onPrimary = NatOnPrimary,
    primaryContainer = NatPrimaryContainer,
    onPrimaryContainer = NatOnPrimaryContainer,
    secondary = NatSecondary,
    onSecondary = NatOnSecondary,
    secondaryContainer = NatSecondaryContainer,
    onSecondaryContainer = NatOnSecondaryContainer,
    background = NatOnBackground, // Darker for high contrast dark mode
    onBackground = NatBackground,
    surface = ColorSegmentOverrideDarkSurface,
    onSurface = NatBackground,
    surfaceVariant = NatOnSurfaceVariant,
    onSurfaceVariant = NatSurfaceVariant,
    outline = NatOnSurfaceVariant,
    error = NatError,
    onError = NatOnError
)

private val LightColorScheme = lightColorScheme(
    primary = NatPrimary,
    onPrimary = NatOnPrimary,
    primaryContainer = NatPrimaryContainer,
    onPrimaryContainer = NatOnPrimaryContainer,
    secondary = NatSecondary,
    onSecondary = NatOnSecondary,
    secondaryContainer = NatSecondaryContainer,
    onSecondaryContainer = NatOnSecondaryContainer,
    background = NatBackground,
    onBackground = NatOnBackground,
    surface = NatSurface,
    onSurface = NatOnSurface,
    surfaceVariant = NatSurfaceVariant,
    onSurfaceVariant = NatOnSurfaceVariant,
    outline = NatOutline,
    error = NatError,
    onError = NatOnError
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to strictly enforce our custom theme colors
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
