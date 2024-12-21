package io.pc7.ninu.presentation.components.resource

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.pc7.ninu.data.network.error.Error
import io.pc7.ninu.domain.model.ResultMy


@Composable
fun <D, E: Error>ResultMy<D, E>.Display(
    modifier: Modifier = Modifier,
    successDisplay: @Composable (D) -> Unit,
) {
    Box(modifier = modifier){
        when(this@Display){
            is ResultMy.Error -> {
                this@Display.error.Display()
            }
            is ResultMy.Success -> successDisplay(this@Display.data)
        }
    }


}