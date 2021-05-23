package com.bitcoin.chart.util.extension

import com.bitcoin.chart.ui.common.UIController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.doToggleLoadingStateOf(
    controller: UIController?
): Flow<T> = this
    .onStart { controller?.setLoading(true) }
    .onCompletion { controller?.setLoading(false) }

fun <T> flowSingle(action: suspend () -> T): Flow<T> {
    return flow { emit(action.invoke()) }
}
