package io.pc7.ninu.presentation.register.userInfo

import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update

class UserInfoInputViewModel(
    coroutineScope: CoroutineScope,
    repository: AuthRepository
): ViewModelBase<UserInfoInputState,UserInfoInputAction,UserInfoInputEvent>(
    coroutineScope,
    UserInfoInputState()
) {


    override fun action(action: UserInfoInputAction) {
        when(action){
            is UserInfoInputAction.OnDateOfBirthChange -> _state.update { it.copy(dateOfBirth = it.dateOfBirth.update(action.date)) }
            is UserInfoInputAction.OnUploadProfileImage -> {}//TODO
            is UserInfoInputAction.OnUsernameChange -> _state.update { it.copy(username = it.username.update(action.username)) }
            UserInfoInputAction.UploadData -> uploadDate()
        }
    }
    
    private fun uploadDate(){

    }
    
}