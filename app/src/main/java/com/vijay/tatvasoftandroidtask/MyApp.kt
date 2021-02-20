package com.vijay.tatvasoftandroidtask

import android.app.Application
import com.vijay.tatvasoftandroidtask.api.data.HomeRepo
import com.vijay.tatvasoftandroidtask.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = module {
            single { HomeRepo() }

            viewModel {
                HomeViewModel(get())
            }
        }
        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@MyApp)
            // use modules
            modules(appModules)
        }
    }
}