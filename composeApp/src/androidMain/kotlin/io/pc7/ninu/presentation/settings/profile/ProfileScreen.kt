package io.pc7.ninu.presentation.settings.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.ProfileImage
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.theme.NINUTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProfileScreen(
    navBack: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModelAndroid>().viewModel
) {

    ProfileScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack
    )

}

@Composable
private fun ProfileScreen(
    state: ProfileState,
    action: (ProfileAction) -> Unit,
    navBack: () -> Unit,
) {


    Column {
        ButtonTopLeftBack(onClick = navBack, text = "My profile", modifier = Modifier.align(Alignment.Start))
        ScrollableColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            ProfileImage(
                size = 200.dp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )


            Text(text = state.name)

            Text(text = "Change profile photo",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.secondaryLight2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )



            ProfileColumnItem(
                title = "Email",
                value = state.email,
                onClick = {}
            )
            ProfileColumnItem(
                title = "Name",
                value = state.name,
                onClick = {}
            )
            ProfileColumnItem(
                title = "Username",
                value = state.username,
                onClick = {}
            )
            ProfileColumnItem(
                title = "Date of birth",
                value = state.dateOfBirth.toString(),
                onClick = {}
            )
        }
    }

}


@Composable
fun ProfileColumnItem(
    title: String,
    value: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clickable(onClick = onClick)
    ) {
        Text(text = title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
        Icon(painter = painterResource(id = io.pc7.ninu.R.drawable.arrow_right), contentDescription = null,
            modifier = Modifier

        )

    }

    
}


@Preview
@Composable
private fun Preview() {
    NINUTheme {
        ProfileScreen(
            state = ProfileState(),
            action = {},
            navBack = {}
        )
    }

}