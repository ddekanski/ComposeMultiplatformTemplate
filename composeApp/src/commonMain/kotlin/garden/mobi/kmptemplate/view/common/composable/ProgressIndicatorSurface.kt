package garden.mobi.kmptemplate.view.common.composable

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import garden.mobi.kmptemplate.view.PrimaryLight

@Composable
fun ProgressIndicatorSurface() {
    Surface(color = Color.White.copy(alpha = 0.5f), modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = Color.PrimaryLight,
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
    }
}
