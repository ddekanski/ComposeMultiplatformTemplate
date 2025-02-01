package garden.mobi.kmptemplate.view.errorDialog


sealed class AppError(open val positiveBtn: PositiveBtn, open val finishOnDismiss: Boolean) {
//    data class NoConnectionError(
//        override val positiveBtn: PositiveBtn = PositiveBtn.OK,
//        override val finishOnDismiss: Boolean = false
//    ) : Error(positiveBtn = positiveBtn, finishOnDismiss = finishOnDismiss)
//
//    data class ConnectionError(
//        override val positiveBtn: PositiveBtn = PositiveBtn.OK,
//        override val finishOnDismiss: Boolean = false
//    ) : Error(positiveBtn = positiveBtn, finishOnDismiss = finishOnDismiss)
//
    data class OtherError(
        val title: String? = null,
        val message: String,
        override val positiveBtn: PositiveBtn = PositiveBtn.OK,
        override val finishOnDismiss: Boolean = false
    ) : AppError(positiveBtn = positiveBtn, finishOnDismiss = finishOnDismiss)

    data class UnexpectedError(
        val throwable: Throwable,
        val message: String? = throwable.message,
        override val positiveBtn: PositiveBtn = PositiveBtn.OK,
        override val finishOnDismiss: Boolean = false,
    ) : AppError(positiveBtn = positiveBtn, finishOnDismiss = finishOnDismiss)

    enum class PositiveBtn { OK, RETRY }
}
