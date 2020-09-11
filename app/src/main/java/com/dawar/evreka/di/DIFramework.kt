package com.dawar.evreka.di

import android.content.Context
import com.dawar.evreka.preference.PrefManager
import com.dawar.evreka.repository.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * The dependency injection framework used by the app.
 * uses Koin for DI.
 * @author Malik Dawar
 */
object DIFramework {

    fun init(context: Context) {

        val repoModule = module {
            single { PrefManager.getInstance() }
            single { Repository.getInstance() }
        }

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(context)
            // declare modules
            modules(repoModule)
        }
    }
}