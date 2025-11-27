package com.acet.moviescinemawithkmp.di

import com.acet.moviescinemawithkmp.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
}