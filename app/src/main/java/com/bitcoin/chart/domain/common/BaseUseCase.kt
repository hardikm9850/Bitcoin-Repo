package com.bitcoin.chart.domain.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<Input, Output> {
    protected abstract fun onBuild(params: Input): Flow<Output>

    protected fun defaultBackgroundScheduler() = Dispatchers.IO

    fun build(params: Input): Flow<Output> = flow { emitAll(onBuild(params)) }
        .flowOn(defaultBackgroundScheduler())
}