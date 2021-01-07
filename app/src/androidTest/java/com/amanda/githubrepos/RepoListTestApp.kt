package com.amanda.githubrepos

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class RepoListTestApp : GithubReposInterface, Application() {

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        TODO("Not yet implemented")
    }

}