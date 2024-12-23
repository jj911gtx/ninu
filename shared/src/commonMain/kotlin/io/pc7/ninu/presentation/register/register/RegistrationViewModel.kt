package io.pc7.ninu.presentation.register.register

import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.data.util.checkEmailPattern
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.model.util.ResultMy
import io.pc7.ninu.domain.model.util.handle
import io.pc7.ninu.domain.model.input.isInputEmpty
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    coroutineScope: CoroutineScope,
    private val repository: AuthRepository,

): ViewModelBase<RegistrationState, RegistrationAction, RegistrationEvent>(
    coroutineScope,
    RegistrationState()
) {
    override fun action(action: RegistrationAction) {

        when(action){
            is RegistrationAction.OnEmailUpdate -> onEmailUpdate(email = action.email)
            RegistrationAction.OnEmailRemoveFocus -> {
                _state.update { it.copy(email = it.email.setDisplayErrors()) }
                emailErrors(_state.value.email.value)
            }

            is RegistrationAction.OnNameUpdate -> onNameUpdate(action.name)
            RegistrationAction.OnNameRemoveFocus -> _state.update { it.copy(name = it.name.setDisplayErrors()) }

            is RegistrationAction.OnSurnameUpdate -> onSurnameUpdate(action.surname)
            RegistrationAction.OnSurnameRemoveFocus -> _state.update { it.copy(surname = it.surname.setDisplayErrors()) }

            is RegistrationAction.OnPasswordUpdate -> onUpdatePassword(action.password)
            RegistrationAction.OnPasswordRemoveFocus -> _state.update { it.copy(password = it.password.setDisplayErrors()) }


            RegistrationAction.OnSignup -> signUp()
        }
    }

    fun checkInputs(){
        emailErrors(_state.value.email.value)
        passwordErrors(_state.value.password.value)
        nameErrors(_state.value.name.value)
        surnameErrors(_state.value.surname.value)
    }

//region name surname
    private fun onSurnameUpdate(surname: String){
        _state.update { it.copy(surname = it.surname.update(surname)) }
        surnameErrors(surname)
    }

    private fun surnameErrors(surname: String): Boolean{
        val errors = checkNameSurnameInputs(surname)
        _state.update { it.copy(
            surname = it.surname.setErrors(errors)
        ) }
        return errors.isNotEmpty()
    }


    private fun onNameUpdate(name: String){
        _state.update { it.copy(name = it.email.update(name)) }
        nameErrors(name)
    }

    private fun nameErrors(name: String): Boolean{
        val errors = checkNameSurnameInputs(name)
        _state.update { it.copy(
            name = it.name.setErrors(errors)
        ) }
        return errors.isNotEmpty()
    }

    private fun checkNameSurnameInputs(input: String): MutableList<String>{
        val errors = mutableListOf<String>()
        !input.isInputEmpty(errors)

        return errors
    }

//endregion

//region email
    private fun onEmailUpdate(email: String){
        _state.update { it.copy(email = it.email.update(email)) }
        emailErrors(email)
    }

    /**
     * true -> has errors
     * false -> no errors
     * */
    private fun emailErrors(email: String): Boolean{
        val errors = mutableListOf<String>()
        if(!email.isInputEmpty(errors)){
            if(!checkEmailPattern(email)){
                errors.add("Invalid email")
            }
        }

        _state.update { it.copy(
            email = it.email.setErrors(errors)
        ) }
        return errors.isNotEmpty()
    }

//endregion

//region password
    private fun onUpdatePassword(password: String){
        _state.update { it.copy(password = it.password.update(password)) }
        passwordErrors(password)
    }

    private fun passwordErrors(
        password: String
    ): Boolean{
        val errors = mutableListOf<String>()
        if(!password.isInputEmpty(errors)){
            if(password.length < 8){
                errors.add("password must be at least 8 char long")
            }
        }

        _state.update { it.copy(password = it.password.setErrors(errors)) }
        return errors.isNotEmpty()
    }

//endregion



    private fun displayAllErrors(){
        _state.update {
            it.copy(
                password = it.password.setDisplayErrors(),
                email = it.email.setDisplayErrors(),
                name = it.name.setDisplayErrors(),
                surname = it.surname.setDisplayErrors(),
            )
        }
    }

    private fun checkAllInputs(
        password: String,
        email: String,
        name: String,
        surname: String
    ): Boolean{

        var errorFree = true
        if(passwordErrors(password)){
            errorFree = false
        }
        if(emailErrors(email)){
            errorFree = false
        }
        if(nameErrors(name)){
            errorFree = false
        }
        if(surnameErrors(surname)){
            errorFree = false
        }
        return errorFree
    }


    private fun signUp(){
        viewModelScope.launch {
            _state.update { it.copy(loginRespond = Resource.Loading) }
            val password = _state.value.password.value
            val email = _state.value.email.value
            val name = _state.value.name.value
            val surname = _state.value.surname.value
            displayAllErrors()
            if(checkAllInputs(password = password, email = email, name = name, surname = surname)){
                repository.register(
                    name = name,
                    surname = surname,
                    email = email,
                    password = password
                ).handle(
                    onSuccess = {
                        _state.update {
                            it.copy(
                                loginRespond = Resource.Result(ResultMy.Success(Unit))
                            )
                        }
                        eventChannel.send(RegistrationEvent.Success)
                    },
                    onError ={ error ->
                        _state.update {
                            it.copy(
                                loginRespond = Resource.Result(ResultMy.Error(error))
                            )
                        }
                    }
                )
            }
        }
    }




}