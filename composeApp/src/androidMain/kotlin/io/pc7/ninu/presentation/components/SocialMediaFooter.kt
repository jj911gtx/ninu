package io.pc7.ninu.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R


@Composable
fun SocialMediaFooter(
    modifier: Modifier = Modifier,
    onLoginRegisterClick: () -> Unit,
    loginPage: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier

    ) {
        Text(
            text = stringResource(R.string.or_continue_with),
            color = colorScheme.white,
            style = MaterialTheme.typography.titleLarge
        )


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.8f)

        ) {
            SocialMediaBracket(iconId = R.drawable.icon_google,
                onClick = {}
            )
            SocialMediaBracket(iconId = R.drawable.icon_apple_inc,
                onClick = {}
            )
            SocialMediaBracket(iconId = R.drawable.icon_facebook,
                onClick = {}
            )
        }


        Row {
            Text(text = if(loginPage) stringResource(R.string.register_here) else stringResource(R.string.already_have_account),
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.white
            )
            Text(text = if(loginPage) " ${stringResource(R.string.register)}" else " ${stringResource(R.string.login)}",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                color = colorScheme.secondaryLight,
                modifier = Modifier.clickable(onClick = onLoginRegisterClick)
            )
        }
    }
}

@Composable
private fun SocialMediaBracket(
    iconId: Int,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(colorScheme.custom3D3D3D)
            .padding(20.dp)
    ) {
        Image(painter = painterResource(id = iconId), contentDescription = null,
            modifier = Modifier
                .size(30.dp)
        )

    }
}