package com.fraggeil.ticketator.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.roboto_black
import ticketator.composeapp.generated.resources.roboto_blackitalic
import ticketator.composeapp.generated.resources.roboto_bold
import ticketator.composeapp.generated.resources.roboto_bolditalic
import ticketator.composeapp.generated.resources.roboto_italic
import ticketator.composeapp.generated.resources.roboto_light
import ticketator.composeapp.generated.resources.roboto_lightitalic
import ticketator.composeapp.generated.resources.roboto_medium
import ticketator.composeapp.generated.resources.roboto_mediumitalic
import ticketator.composeapp.generated.resources.roboto_regular
import ticketator.composeapp.generated.resources.roboto_thin
import ticketator.composeapp.generated.resources.roboto_thinitalic

@Composable
fun RobotoFontFamily() = FontFamily(
    Font(Res.font.roboto_black, FontWeight.Black),
    Font(Res.font.roboto_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(Res.font.roboto_bold, FontWeight.Bold),
    Font(Res.font.roboto_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.roboto_light, FontWeight.Light),
    Font(Res.font.roboto_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.roboto_medium, FontWeight.Medium),
    Font(Res.font.roboto_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.roboto_regular, FontWeight.Normal),
    Font(Res.font.roboto_thin, FontWeight.Thin),
    Font(Res.font.roboto_thinitalic, FontWeight.Thin, FontStyle.Italic),
)

// Default Material 3 typography values
val baseline = Typography()

@Composable
fun AppTypography() = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = RobotoFontFamily()),
    displayMedium = baseline.displayMedium.copy(fontFamily = RobotoFontFamily()),
    displaySmall = baseline.displaySmall.copy(fontFamily = RobotoFontFamily()),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = RobotoFontFamily()),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = RobotoFontFamily()),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = RobotoFontFamily()),
    titleLarge = baseline.titleLarge.copy(fontFamily = RobotoFontFamily()),
    titleMedium = baseline.titleMedium.copy(fontFamily = RobotoFontFamily()),
    titleSmall = baseline.titleSmall.copy(fontFamily = RobotoFontFamily()),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = RobotoFontFamily()),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = RobotoFontFamily()),
    bodySmall = baseline.bodySmall.copy(fontFamily = RobotoFontFamily()),
    labelLarge = baseline.labelLarge.copy(fontFamily = RobotoFontFamily()),
    labelMedium = baseline.labelMedium.copy(fontFamily = RobotoFontFamily()),
    labelSmall = baseline.labelSmall.copy(fontFamily = RobotoFontFamily()),
)

