package io.pc7.ninu.di

import io.ktor.client.HttpClient
import io.pc7.ninu.data.network.HttpClientFactory
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.data.network.repository.PerfumeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dataDi = module {
    single<HttpClient>{
        HttpClientFactory(/*get()*/).build()
    }



    singleOf(::AuthRepository)
    singleOf(::PerfumeRepository)

}