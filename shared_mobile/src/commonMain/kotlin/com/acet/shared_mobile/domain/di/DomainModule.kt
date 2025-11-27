package com.acet.shared_mobile.domain.di

import com.acet.shared_mobile.domain.usecase.GetMovieUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetMovieUseCase)
}
