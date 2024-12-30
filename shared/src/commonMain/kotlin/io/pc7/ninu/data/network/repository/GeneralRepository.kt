package io.pc7.ninu.data.network.repository

import io.ktor.client.HttpClient
import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.model.DeviceMacRespond
import io.pc7.ninu.domain.model.util.EmptyResult
import io.pc7.ninu.domain.model.util.ResultMy
import io.pc7.ninu.domain.model.util.asEmptyDataResult
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate

class GeneralRepository (
    private val httpClient: HttpClient,

) {

    suspend fun checkDeviceSerialNumber(sn: String): ResultMy<DeviceMacRespond, DataError.Network> {
        return ResultMy.Success(DeviceMacRespond("A4:75:B9:5C:8D:D3"))//TODO
    }



}
