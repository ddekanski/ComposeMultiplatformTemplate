package garden.mobi.kmptemplate.view

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.muli
import composemultiplatformtemplate.composeapp.generated.resources.muli_bold
import composemultiplatformtemplate.composeapp.generated.resources.muli_bold_italic
import composemultiplatformtemplate.composeapp.generated.resources.muli_italic
import composemultiplatformtemplate.composeapp.generated.resources.muli_light
import composemultiplatformtemplate.composeapp.generated.resources.muli_light_italic
import composemultiplatformtemplate.composeapp.generated.resources.teko_light
import composemultiplatformtemplate.composeapp.generated.resources.teko_medium
import org.jetbrains.compose.resources.Font

@Composable
private fun AccentFontFamily() = FontFamily(
    Font(Res.font.teko_light, style = FontStyle.Normal, weight = FontWeight.W400),
    Font(Res.font.teko_light, style = FontStyle.Italic, weight = FontWeight.W400),
    Font(Res.font.teko_medium, style = FontStyle.Normal, weight = FontWeight.W500),
    Font(Res.font.teko_medium, style = FontStyle.Italic, weight = FontWeight.W500),
)

@Composable
private fun MainFontFamily() = FontFamily(
    Font(Res.font.muli_light, style = FontStyle.Normal, weight = FontWeight.W400),
    Font(Res.font.muli_light_italic, style = FontStyle.Italic, weight = FontWeight.W400),
    Font(Res.font.muli, style = FontStyle.Normal, weight = FontWeight.W500),
    Font(Res.font.muli_italic, style = FontStyle.Italic, weight = FontWeight.W500),
    Font(Res.font.muli_bold, style = FontStyle.Normal, weight = FontWeight.W700),
    Font(Res.font.muli_bold_italic, style = FontStyle.Italic, weight = FontWeight.W700),
)

@Composable
fun AppTypography() = Typography().run {

    val mainFontFamily = MainFontFamily()
    val accentFontFamily = AccentFontFamily()

    copy(
        displayLarge = displayLarge.copy(fontFamily = mainFontFamily),
        displayMedium = displayMedium.copy(fontFamily = mainFontFamily),
        displaySmall = displaySmall.copy(fontFamily = mainFontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = accentFontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = accentFontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = accentFontFamily),
        titleLarge = titleLarge.copy(fontFamily = accentFontFamily),
        titleMedium = titleMedium.copy(fontFamily = accentFontFamily),
        titleSmall = titleSmall.copy(fontFamily = accentFontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = mainFontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = mainFontFamily),
        bodySmall = bodySmall.copy(fontFamily = mainFontFamily),
        labelLarge = labelLarge.copy(fontFamily = mainFontFamily),
        labelMedium = labelMedium.copy(fontFamily = mainFontFamily),
        labelSmall = labelSmall.copy(fontFamily = mainFontFamily),
    )
}