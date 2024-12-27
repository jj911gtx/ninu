package io.pc7.ninu.presentation.register.userInfo

import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInfoInputViewModel(
    coroutineScope: CoroutineScope,
    repository: AuthRepository
): ViewModelBase<UserInfoInputState,UserInfoInputAction,UserInfoInputEvent>(
    coroutineScope,
    UserInfoInputState()
) {

    private var profileImageByteArray: ByteArray? = null

    override fun action(action: UserInfoInputAction) {
        when(action){
            is UserInfoInputAction.OnDateOfBirthChange -> _state.update { it.copy(dateOfBirth = it.dateOfBirth.update(action.date)) }
            is UserInfoInputAction.OnUploadProfileImage -> {
                profileImageByteArray = action.value
                _state.update { it.copy(profileImage = it.profileImage.update(Unit)) }
            }
            is UserInfoInputAction.OnUsernameChange -> _state.update { it.copy(username = it.username.update(action.username)) }
            UserInfoInputAction.UploadData -> uploadDate()
        }
    }
    
    private fun uploadDate(){
        viewModelScope.launch {
            eventChannel.send(UserInfoInputEvent.Success)
        }
    }
    
}