package io.pc7.ninu.data.network.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.pc7.ninu.data.network.Network
import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.model.auth.RegisterRequest
import io.pc7.ninu.data.network.model.auth.RegisterRespond
import io.pc7.ninu.data.network.model.auth.UpdatePasswordRequest
import io.pc7.ninu.data.network.model.auth.UpdateUserRequest
import io.pc7.ninu.data.network.post
import io.pc7.ninu.domain.model.util.EmptyResult
import io.pc7.ninu.domain.model.util.ResultMy
import io.pc7.ninu.domain.model.util.asEmptyDataResult
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate

class AuthRepository(
    private val httpClient: HttpClient,

) {

    suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        return ResultMy.Success(Unit).asEmptyDataResult()
    }

    suspend fun logout(): EmptyResult<DataError.Network> {
        return ResultMy.Success(Unit).asEmptyDataResult()
    }

    suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
    ): ResultMy<RegisterRespond, DataError.Network> {
        delay(1000)
//        return httpClient.post(
//            route = "register",
//            body = RegisterRequest(
//                name = name, surname = surname, email = email, password = password
//            )
//        )
        return ResultMy.Success(RegisterRespond(token = "dfdf"))
    }

    suspend fun otherData(
        username: String,
        dateOfBirth: LocalDate,
    ): EmptyResult<DataError.Network> {
        return ResultMy.Success(Unit).asEmptyDataResult()
    }

    suspend fun sendVerificationCode(email: String): EmptyResult<DataError.Network> {
        delay(1000)
        return ResultMy.Success(Unit).asEmptyDataResult()
    }

    suspend fun checkCode(code: String): EmptyResult<DataError.Network> {
        delay(1000)
        return ResultMy.Success(Unit).asEmptyDataResult()
    }

    suspend fun updatePassword(
        password: String, newPassword: String
    ): EmptyResult<DataError.Network> {
//        httpClient.post<UpdatePasswordRequest, Unit>(
//            route = "",
//            body = UpdatePasswordRequest(
//                password = password, newPassword = newPassword
//            )
//        ).asEmptyDataResult()
        return ResultMy.Success(Unit).asEmptyDataResult()
    }

    suspend fun updateUser(
        updateUserRequest: UpdateUserRequest
    ): EmptyResult<DataError.Network>{
//        val respond = httpClient.post<UpdateUserRequest, Unit>(
//            route = "register",
//            body = updateUserRequest
//        )
        return ResultMy.Success(Unit).asEmptyDataResult()
    }

}

val createError: EmptyResult<DataError.Network> = ResultMy.Error(DataError.Network.NO_INTERNET).asEmptyDataResult()
