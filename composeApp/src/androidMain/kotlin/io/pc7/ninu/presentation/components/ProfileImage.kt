package io.pc7.ninu.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import io.pc7.ninu.R


@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    size: Dp
) {


    Image(painter = painterResource(id = R.drawable.profile_picture), contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .size(size)
    )

}