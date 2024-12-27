package io.pc7.ninu.presentation.components.util

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme


@Composable
fun BackgroundHandling(

) {

    val activity = LocalContext.current as Activity

    LaunchedEffect(Unit) {

        activity.setStatusBar()
    }

//    DisposableEffect(Unit) {
//        onDispose {
//            activity.setBackground()
//        }
//    }

}

fun Activity.setStatusBar(){
    window.statusBarColor = colorScheme.black.toArgb()
    setBottomBar(true)
//    window.navigationBarColor = colorScheme.black.toArgb()
}

fun Activity.setBottomBar(black: Boolean){
    window.navigationBarColor = if(black){
        colorScheme.black.toArgb()
    }else{
        colorScheme.primary.toArgb()
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
fun Activity.setBackgroundImage(){
    window.setBackgroundDrawable(this.getDrawable(R.drawable.ozadje))
}


@SuppressLint("UseCompatLoadingForDrawables")
fun Activity.removeBackgroundImage(){
    window.setBackgroundDrawableResource(android.R.color.transparent)
}