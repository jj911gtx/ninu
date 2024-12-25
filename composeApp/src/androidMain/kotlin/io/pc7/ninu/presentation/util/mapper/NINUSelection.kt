package io.pc7.ninu.presentation.util.mapper

import androidx.compose.ui.graphics.Color
import io.pc7.ninu.domain.model.perfume.NINUSelection


fun NINUSelection.toColor(): Color{
    return Color(this.toHex())
}