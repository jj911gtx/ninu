package io.pc7.ninu.data.network.auth

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)