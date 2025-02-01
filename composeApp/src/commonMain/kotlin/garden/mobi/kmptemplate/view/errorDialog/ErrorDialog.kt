package garden.mobi.kmptemplate.view.errorDialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.*
import garden.mobi.kmptemplate.view.common.composable.CtaButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    error: AppError,
    onDismissed: (error: AppError) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissed(error) },
        containerColor = Color.LightGray,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            val titleText: String?
            val messageText: String

            when (error) {
//                is Error.NoConnectionError -> {
//                    titleText = stringResource(Res.string.no_connection_dialog_title)
//                    messageText = stringResource(Res.string.no_connection_dialog_desc)
//                }
//
//                is Error.ConnectionError -> {
//                    titleText = stringResource(Res.string.connection_error_dialog_title)
//                    messageText = stringResource(Res.string.connection_error_dialog_desc)
//                }
//
                is AppError.OtherError -> {
                    titleText = error.title
                    messageText = error.message
                }

                is AppError.UnexpectedError -> {
                    titleText = stringResource(Res.string.generic_error_dialog_title)
                    messageText = error.message ?: stringResource(Res.string.generic_error_dialog_desc)
                }
            }

            val positiveBtnText = stringResource(
                when (error.positiveBtn) {
                    AppError.PositiveBtn.OK -> Res.string.ok
                    AppError.PositiveBtn.RETRY -> Res.string.retry
                }
            )

            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .padding(bottom = 12.dp)
            )

            titleText?.let {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Text(
                text = messageText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            CtaButton(
                text = positiveBtnText,
                onClick = { onDismissed(error) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview2() = ErrorDialog(error = AppError.OtherError(message = "Generic error"), onDismissed = {})

@Preview
@Composable
private fun Preview3() = ErrorDialog(error = AppError.UnexpectedError(throwable = RuntimeException()), onDismissed = {})

