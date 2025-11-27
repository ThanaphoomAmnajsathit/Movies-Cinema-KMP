package com.acet.shared_mobile.data.di

import com.acet.shared_mobile.client_configuration.DefaultHttpClient
import com.acet.shared_mobile.data.remote.MovieService
import com.acet.shared_mobile.data.remote.MovieServiceImpl
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single<HttpClient> { DefaultHttpClient().install() }

    singleOf(::MovieServiceImpl) { bind<MovieService>() }
}