package io.pc7.ninu.data.network.repository

import io.ktor.client.HttpClient
import io.pc7.ninu.data.network.error.DataError
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

    suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
    ): EmptyResult<DataError.Network> {
        delay(2000)
//        return ResultMy.Error(DataError.Network.NO_INTERNET).asEmptyDataResult()
        return ResultMy.Success(Unit).asEmptyDataResult()
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

    suspend fun updatePassword(password: String): EmptyResult<DataError.Network> {
        return ResultMy.Success(Unit).asEmptyDataResult()
    }


}

val createError: EmptyResult<DataError.Network> = ResultMy.Error(DataError.Network.NO_INTERNET).asEmptyDataResult()
