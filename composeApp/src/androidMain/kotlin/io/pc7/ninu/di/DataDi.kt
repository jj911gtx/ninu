package io.pc7.ninu.di

import io.ktor.client.HttpClient
import io.pc7.ninu.data.database.DatabaseBuilder
import io.pc7.ninu.data.network.HttpClientFactory
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.data.network.repository.GeneralRepository
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.database.Dao
import io.pc7.ninu.database.PerfumeDatabase
import io.pc7.ninu.database.PerfumeDatabaseConstructor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dataDi = module {
    single<HttpClient>{
        HttpClientFactory(/*get()*/).build()
    }

    single<Dao>{ DatabaseBuilder(androidApplication().applicationContext).build().getDao() }


    singleOf(::AuthRepository)
    singleOf(::PerfumeRepository)
    singleOf(::GeneralRepository)

}