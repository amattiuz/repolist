package com.amanda.githubrepos

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class RepoListTestApp : GithubReposInterface, Application() {

    /***
     *  Having the modelview provided by the Application was a way
     *  to make mocking easier, but I'm not sure anymore this is
     *  a good idea.
     */
    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        TODO("Not yet implemented")
    }

}