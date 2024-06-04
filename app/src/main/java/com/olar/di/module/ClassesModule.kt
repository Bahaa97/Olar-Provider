package com.olar.di.module

import com.olar.data.remote.reporsitory.MainRepository
import com.olar.Utils.AppManger
import org.koin.dsl.module

val classesModule = module {
    single { MainRepository(get()) }
    single { AppManger(get()) }
}