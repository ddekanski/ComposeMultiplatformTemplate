package garden.mobi.kmptemplate.view.common.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus

val ViewModel.backgroundViewModelScope: CoroutineScope get() = viewModelScope + (viewModelScope.coroutineContext + Dispatchers.Default)
