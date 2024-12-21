package io.pc7.ninu.presentation.components.util

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun rememberKeyboardVisibility(): State<Boolean> {
    val view = LocalView.current
    val density = LocalDensity.current

    val isKeyboardVisible = remember { mutableStateOf(false) }

    DisposableEffect(view) {
        val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)

            val screenHeight = view.rootView.height
            val visibleHeight = rect.height()

            val keyboardHeight = screenHeight - visibleHeight

            isKeyboardVisible.value = keyboardHeight > with(density) { 100.dp.toPx() }
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
    }
    return isKeyboardVisible
}