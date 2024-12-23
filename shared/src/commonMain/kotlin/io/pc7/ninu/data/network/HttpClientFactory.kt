package io.pc7.ninu.data.network


import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
//    private val sessionStorage: SessionStorage
) {

    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        encodeDefaults = true
                        ignoreUnknownKeys = true
                    }
                )
            }
//            install(Auth) {
//                bearer {
//                    loadTokens {
//                        val info = sessionStorage.get()
//                        BearerTokens(
//                            accessToken = info?.accessToken ?: "",
//                            refreshToken = info?.refreshToken ?: ""
//                        )
//                    }
//                    refreshTokens {
//                        val info = sessionStorage.get()
//                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
//                            route = "/accessToken",
//                            body = AccessTokenRequest(
//                                refreshToken = info?.refreshToken ?: "",
//                                userId = info?.userId ?: ""
//                            )
//                        )
//
//                        if(response is ResultMy.Success) {
//                            val newAuthInfo = AuthInfo(
//                                accessToken = response.data.accessToken,
//                                refreshToken = info?.refreshToken ?: "",
//                                userId = info?.userId ?: ""
//                            )
//                            sessionStorage.set(newAuthInfo)
//
//                            BearerTokens(
//                                accessToken = newAuthInfo.accessToken,
//                                refreshToken = newAuthInfo.refreshToken
//                            )
//                        } else {
//                            BearerTokens(
//                                accessToken = "",
//                                refreshToken = ""
//                            )
//                        }
//                    }
//                }
//            }

            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        println(message)
//                    }
//                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)

            }
        }
    }


}






