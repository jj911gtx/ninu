package io.pc7.ninu.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun SettingsListScreen(
    navController: NavController,
    navBack: () -> Unit,
) {



    ScrollableColumn {
        ButtonTopLeftBack(onClick = navBack, text = "My Settings")

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SettingsItem(
                icon = R.drawable.icon_profile_checked,
                text = "About me",
                onClick = { navController.navigate(SettingsNavigationRoutes.AboutMe) }
            )
            SettingsItem(
                icon = R.drawable.icon_mouse_point,
                text = "Follow NINU",
                onClick = { navController.navigate(SettingsNavigationRoutes.FollowNINU) }
            )
            SettingsItem(
                icon = R.drawable.icon_mouse_point,
                text = "Language",
                onClick = { navController.navigate(SettingsNavigationRoutes.Language) }
            )
//            SettingsItem(
//                icon = R.drawable.icon_share,
//                text = "Share NINU with friends",
//                onClick = { navController.navigate(SettingsNavigationRoutes.Share) }
//            )
//            SettingsItem(
//                icon = R.drawable.icon_settings,
//                text = "How to apply?",
//                onClick = { navController.navigate(SettingsNavRoutes.HOW_TO_APPLY) }
//            )
            SettingsItem(
                icon = R.drawable.icon_lock,
                text = "Change password",
                onClick = { navController.navigate(SettingsNavigationRoutes.ChangePassword) }
            )
//            SettingsItem(
//                icon = R.drawable.icon_file,
//                text = "Legal & Regulatory",
//                onClick = { navController.navigate(SettingsNavigationRoutes.LegalRegulatory) }
//            )

//            SettingsItem(
//                icon = R.drawable.icon_log_out,
//                text = "Log out",
//                onClick = {
//                    TODO()
//                },
//                color = colorScheme.secondaryLight
//            )


        }
    }
}


@Composable
private fun SettingsItem(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    color: Color = colorScheme.white
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .padding(5.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(30.dp)
                .padding(end = 10.dp)
        )

        Text(text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
            color = color,
            modifier = Modifier.weight(1f)
        )
        
        Icon(painter = painterResource(id = R.drawable.arrow_right), contentDescription = null,
            tint = color,

        )


    }


}



@Preview
@Composable
private fun SettingsScreenPreview() {
    val navController = rememberNavController()
    NINUTheme {
        SettingsListScreen(
            navController = navController,
            navBack = {}
        )
    }

}