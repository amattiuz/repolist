package com.amanda.githubrepos

import android.app.Application
import androidx.lifecycle.ViewModelProvider

const val BASE_URL = "https://api.github.com/"
@OpenForTesting
class GithubReposApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //Stetho.initializeWithDefaults(this)
    }
}


//TODO check if this is really needed for instrumented tests
interface GithubReposInterface {
    fun provideViewModelFactory() : ViewModelProvider.Factory
}

//TODO add the configuration for this to the gradle file (dependencies?)
@OpenForTesting
annotation class OpenForTesting