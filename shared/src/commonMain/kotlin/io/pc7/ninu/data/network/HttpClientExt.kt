package io.pc7.ninu.data.network
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.domain.model.util.ResultMy
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): ResultMy<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response: Any> HttpClient.post(
    route: String,
    body: Request
): ResultMy<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}


suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): ResultMy<T, DataError.Network> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        e.printStackTrace()
        return ResultMy.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return ResultMy.Error(DataError.Network.SERIALIZATION)
    } catch(e: Exception) {
        if(e is CancellationException) throw e
        e.printStackTrace()
        return ResultMy.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): ResultMy<T, DataError.Network> {
    return when(response.status.value) {
        in 200..299 -> ResultMy.Success(response.body<T>())
        401 -> ResultMy.Error(DataError.Network.UNAUTHORIZED)
        408 -> ResultMy.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> ResultMy.Error(DataError.Network.CONFLICT)
        413 -> ResultMy.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> ResultMy.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> ResultMy.Error(DataError.Network.SERVER_ERROR)
        else -> ResultMy.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(Network.BASE_URL) -> route
        route.startsWith("/") -> Network.BASE_URL + route
        else -> Network.BASE_URL + "/$route"
    }
}



