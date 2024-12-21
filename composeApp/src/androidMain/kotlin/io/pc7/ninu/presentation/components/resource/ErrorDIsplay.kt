package io.pc7.ninu.presentation.components.resource

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.error.Error


@Composable
fun Error.Display(
    modifier: Modifier = Modifier
) {

    Text(
        modifier = Modifier.padding(10.dp),
        text = when(this@Display){
            is DataError -> {
                when(this@Display){
                    is DataError.Network -> {
                        when(this@Display) {
                            DataError.Network.REQUEST_TIMEOUT -> "request_timeout"
                            DataError.Network.UNAUTHORIZED -> "unauthorized"
                            DataError.Network.CONFLICT -> "conflict"
                            DataError.Network.TOO_MANY_REQUESTS -> "too_many_requests"
                            DataError.Network.NO_INTERNET -> "no_internet"
                            DataError.Network.PAYLOAD_TOO_LARGE -> "payload_too_large"
                            DataError.Network.SERVER_ERROR -> "server_error"
                            DataError.Network.SERIALIZATION -> "serialization"
                            DataError.Network.UNKNOWN -> "unknown"
                            DataError.Network.FORBIDDEN -> "forbidden"
                            DataError.Network.NOT_FOUND -> "not_found"
                            DataError.Network.METHOD_NOT_ALLOWED -> "method_not_allowed"
                            DataError.Network.UNPROCESSABLE_ENTITY -> "unprocessable_entity"
                            DataError.Network.INTERNAL_SERVER_ERROR -> "internal_server_error"
                            DataError.Network.SERVICE_UNAVAILABLE -> "service_unavailable"
                            DataError.Network.BAD_REQUEST -> "bad_request"
                        }

                    }
                    DataError.Local.DISK_FULL -> "Disk full"
                    DataError.UnknownError -> "Unknown"
                }
            }


            else -> "Error"
        }
    )
    


}