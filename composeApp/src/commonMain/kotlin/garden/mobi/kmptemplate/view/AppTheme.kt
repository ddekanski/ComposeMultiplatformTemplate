package garden.mobi.kmptemplate.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.util.DebugLogger

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    InitCoil()

    val lightColors = lightColors(     primary = Color.PrimaryLight )
    val darkColors = darkColors(     primary = Color.PrimaryDark )
    val colors = if (isSystemInDarkTheme()) darkColors else lightColors

    MaterialTheme(colors = colors, content = content)
}

@Composable
private fun InitCoil() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).logger(DebugLogger()).build()
    }
}