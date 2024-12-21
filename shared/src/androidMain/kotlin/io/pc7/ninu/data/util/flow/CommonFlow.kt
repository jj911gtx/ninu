package io.pc7.ninu.data.util.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

actual class CommonFlow<T> actual constructor(private val flow: Flow<T>) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        flow.collect(collector)
    }
}