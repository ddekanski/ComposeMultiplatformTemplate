package garden.mobi.kmptemplate.view.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import garden.mobi.kmptemplate.view.Pink
import garden.mobi.kmptemplate.view.PrimaryDisabled

@Composable
fun CtaButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        containerColor = Color.Pink,
        disabledContainerColor = Color.PrimaryDisabled,
        disabledContentColor = Color.White,
    ),
) {
    Button(
        shape = RoundedCornerShape(12.dp),
        colors = colors,
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)

    ) {
        Text(
            text = text,
            style = TextStyle.Default,
        )
    }
}

