package io.pc7.ninu.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.pc7.ninu.presentation.login.LoginViewModelAndroid
import io.pc7.ninu.presentation.login.NewPasswordViewModel
import io.pc7.ninu.presentation.login.VerificationViewModel
import io.pc7.ninu.presentation.login.newPassword.NewPasswordViewModelAndroid
import io.pc7.ninu.presentation.login.verification.VerificationViewModelAndroid
import io.pc7.ninu.presentation.register.registration.RegistrationViewModelAndroid
import io.pc7.ninu.presentation.register.userInfo.UserInfoInputViewModelAndroid
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val loginDi = module {
    viewModelOf(::LoginViewModelAndroid)
    viewModel{ (email: String) -> VerificationViewModelAndroid(email = email, get()) }
    viewModelOf(::NewPasswordViewModelAndroid)

    viewModelOf(::RegistrationViewModelAndroid)
    viewModelOf(::UserInfoInputViewModelAndroid)


    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(),
            "auth_pref",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}