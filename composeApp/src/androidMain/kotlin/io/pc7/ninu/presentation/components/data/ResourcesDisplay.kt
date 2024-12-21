package io.pc7.ninu.presentation.components.data

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.pc7.ninu.domain.model.Resource


@Composable
fun <D> Resource<D>.ResourcesDisplay(
    modifier: Modifier = Modifier,
    loadingContent: (@Composable () -> Unit)? = null,
    resultContent: @Composable (data: D) -> Unit
) {
    when (this) {
        is Resource.Loading -> {
            loadingContent?.invoke() ?: Text(text = "Loading")

        }
        is Resource.Result -> {
            resultContent(data)
        }
    }
}