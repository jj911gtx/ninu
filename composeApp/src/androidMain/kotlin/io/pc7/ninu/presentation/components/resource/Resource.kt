package io.pc7.ninu.presentation.components.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.pc7.ninu.data.network.error.Error
import io.pc7.ninu.domain.model.Resource
import io.pc7.ninu.domain.model.ResultMy
import io.pc7.ninu.presentation.components.LoadingDialog


@Composable
fun <D, E: Error>Resource<ResultMy<D, E>>.Display(
    modifier: Modifier = Modifier,
    successDisplay: @Composable (D) -> Unit,
) {
    when(this){
        Resource.Loading -> LoadingDialog(true)
        is Resource.Result -> this.data.Display(successDisplay = successDisplay)
    }

}