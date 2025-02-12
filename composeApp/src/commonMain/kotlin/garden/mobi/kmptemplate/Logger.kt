package garden.mobi.kmptemplate

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initLogger() = Napier.base(DebugAntilog())

@Suppress("unused", "NOTHING_TO_INLINE", "KotlinRedundantDiagnosticSuppress")
object Logger {
    inline fun d(message: String, throwable: Throwable? = null) = Napier.d(message = message, throwable = throwable)
    inline fun w(message: String) = Napier.w(message = message)
    inline fun e(message: String) = Napier.e(message = message)
    inline fun e(message: String?, throwable: Throwable?) = Napier.e(message = message ?: "Error", throwable = throwable)
    inline fun e(throwable: Throwable) = Napier.e(message = throwable.message ?: "Error", throwable = throwable)
}