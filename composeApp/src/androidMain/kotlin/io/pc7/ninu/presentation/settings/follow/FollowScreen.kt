package io.pc7.ninu.presentation.settings.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun FollowScreen(
    navBack: () -> Unit,
) {

    Column {
        ButtonTopLeftBack(onClick = navBack, text = "Follow NINU")

        ScrollableColumn {

            FollowItem(
                icon = R.drawable.icon_customer_service,
                text = "Customer Service",
                onClick = {}
            )
            FollowItem(
                icon = R.drawable.icon_whats_app,
                text = "WhatsApp",
                onClick = {}
            )
            FollowItem(
                icon = R.drawable.icon_website,
                text = "Customer Service",
                onClick = {}
            )
            FollowItem(
                icon = R.drawable.icon_facebook,
                text = "Customer Service",
                onClick = {}
            )
            FollowItem(
                icon = R.drawable.icon_x,
                text = "Customer Service",
                onClick = {}
            )
            FollowItem(
                icon = R.drawable.icon_instagram,
                text = "Customer Service",
                onClick = {}
            )
        }
    }

}


@Composable
private fun FollowItem(
    icon: Int,
    text: String,
    onClick: () -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .background(colorScheme.primary)
            .padding(20.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = null,
            tint = colorScheme.secondaryDark
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }


}


@Preview
@Composable
private fun FollowScreenPreview() {
    NINUTheme {
        FollowScreen(
            navBack = {}
        )
    }

}