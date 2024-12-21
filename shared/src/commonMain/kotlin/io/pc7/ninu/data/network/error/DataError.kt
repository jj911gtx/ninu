package io.pc7.ninu.data.network.error




@Suppress("ClassName")
sealed class DataError: Error() {
    sealed class Network : DataError() {
        object REQUEST_TIMEOUT : Network()
        object UNAUTHORIZED : Network()
        object CONFLICT : Network()
        object TOO_MANY_REQUESTS : Network()
        object NO_INTERNET : Network()
        object PAYLOAD_TOO_LARGE : Network()
        object SERVER_ERROR : Network()
        object SERIALIZATION : Network()
        object UNKNOWN : Network()
        object FORBIDDEN : Network()
        object NOT_FOUND : Network()
        object METHOD_NOT_ALLOWED : Network()
        object UNPROCESSABLE_ENTITY : Network()
        object INTERNAL_SERVER_ERROR : Network()
        object SERVICE_UNAVAILABLE : Network()
        object BAD_REQUEST : Network()

    }

    sealed class Local : DataError() {
        object DISK_FULL : Local()
    }


    data object UnknownError: DataError()


}