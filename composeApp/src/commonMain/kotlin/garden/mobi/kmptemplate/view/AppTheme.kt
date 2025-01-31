package garden.mobi.kmptemplate.view

import androidx.compose.material3.MaterialTheme
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

    val colorScheme = MaterialTheme.colorScheme.copy(
        primary = Color.PrimaryLight
    )

    MaterialTheme(colorScheme = colorScheme, content = content)
}

@Composable
private fun InitCoil() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).logger(DebugLogger()).build()
    }
}