package garden.mobi.kmptemplate.domain.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus

@Suppress("FunctionName")
fun GlobalIoScope() = MainScope().let { it.plus(it.coroutineContext + Dispatchers.IO) }

@Suppress("FunctionName")
fun GlobalDefaultBackgroundScope() = MainScope().let { it.plus(it.coroutineContext + Dispatchers.Default) }

