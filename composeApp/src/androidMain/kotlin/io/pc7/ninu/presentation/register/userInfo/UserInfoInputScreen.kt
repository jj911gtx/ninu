package io.pc7.ninu.presentation.register.userInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.data.mapper.toTextString
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.components.main.input.datePicker.CalendarDatePicker
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.main.input.text.NINUinputFieldNoText
import io.pc7.ninu.presentation.components.other.CountriesSelectModalBottomSheet
import io.pc7.ninu.presentation.components.other.NINUModalBottomSheetItem
import io.pc7.ninu.presentation.components.other.NINUModalSheet
import io.pc7.ninu.presentation.components.other.TakeChosePhoto
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.theme.NINUTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun UserInfoInputScreen(
    next: () -> Unit,
    back: () -> Unit,
    viewModel: UserInfoInputViewModel = koinViewModel<UserInfoInputViewModelAndroid>().viewModel
) {

    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            UserInfoInputEvent.Success -> next()
        }
    }
    UserInfoInputScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        next = next,
        back = back
    )

}



@Composable
fun UserInfoInputScreen(
    state: UserInfoInputState,
    action: (UserInfoInputAction) -> Unit,
    next: () -> Unit,
    back: () -> Unit,
) {



    ScrollableColumn(
        modifier = Modifier

            .fillMaxSize()
            .imePadding()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        ButtonTopLeftBack(
            onClick = back,
            text = stringResource(R.string.create_your_account),
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            NINUTextField(
                value = state.username,
                onUpdate = {action(UserInfoInputAction.OnUsernameChange(it))},
                placeholderText = stringResource(R.string.username),
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_verified_user),
                        contentDescription = null
                    )
                }
            )


            val showCalendar = remember { mutableStateOf(false) }
            CalendarDatePicker(
                onDateChanged = {action(UserInfoInputAction.OnDateOfBirthChange(it))},
                showDialog = showCalendar,
            )
            NINUinputFieldNoText(
                value = MyInput(state.dateOfBirth.value?.toTextString() ?: ""),
                placeholderText = stringResource(R.string.date_of_birth),
                suffix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_image_plus),
                        contentDescription = null
                    )
                },
                onClick = {
                    showCalendar.value = true
                }
            )
            AddProfileImage(
                profileImage = state.profileImage,
                onUpdate = {
                    action(UserInfoInputAction.OnUploadProfileImage(it))
                }

            )



            var displayCountries by remember { mutableStateOf(false) }
            NINUinputFieldNoText(
                value = MyInput(state.selectedCountry.value?.name ?: ""),
                placeholderText = stringResource(R.string.country),
                suffix = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.icon_image_plus),
//                        contentDescription = null
//                    )
                },
                onClick = {
                    displayCountries = true
                }
            )
            if(displayCountries){
                CountriesSelectModalBottomSheet(
                    onDismiss = {displayCountries = false},
                    selectedCountry = state.selectedCountry.value,
                    countries = state.countries,
                    onSelectCountry = {
                        action(UserInfoInputAction.OnUpdateCountry(it))
                    }

                )
            }



        }

        Spacer(modifier = Modifier.weight(2f))

        DefaultButtonText(
            onClick = {
                if(inputsFilled(state)){
                    action(UserInfoInputAction.UploadData)
                }else{
                    next()
                }
            },
            text = if(inputsFilled(state)) stringResource(R.string.done) else stringResource(R.string.do_this_later),
            displayDisabled = !inputsFilled(state),
        )



    }



}

@Composable
fun AddProfileImage(
    profileImage: MyInput<Unit?>,
    onUpdate: (ByteArray) -> Unit,


) {
    val photoSelectOptionBottomSheetDisplay = remember { mutableStateOf(false) }

    val isProfileImage = profileImage.value != null
    NINUinputFieldNoText(
        value = MyInput(if(isProfileImage) stringResource(R.string.uploaded) else ""),
        placeholderText = stringResource(R.string.add_profile_image),
        suffix = {
            Icon(
                painter = painterResource(id = if(isProfileImage) R.drawable.icon_check else R.drawable.icon_image_plus),
                contentDescription = null,
                tint = if(isProfileImage) colorScheme.white else colorScheme.primary
            )
        },
        onClick = { photoSelectOptionBottomSheetDisplay.value = true }

    )

    TakeChosePhoto(
        photoSelectOptionBottomSheetDisplay = photoSelectOptionBottomSheetDisplay,
        onUpdate = onUpdate
    )



}

private fun inputsFilled(state: UserInfoInputState): Boolean{
    return state.username.value.isNotEmpty()
            && state.dateOfBirth.value != null
            && state.profileImage.value == Unit
}



@Preview
@Composable
private fun UserInfoInputScreenPreview() {
    NINUTheme {
        UserInfoInputScreen(
            state = UserInfoInputState(),
            action = {}, next = {}, back = {}

        )
    }
}


