package com.amanda.githubrepos

import androidx.lifecycle.ViewModelProvider
import com.amanda.githubrepos.api.GithubServiceClient
import com.amanda.githubrepos.model.GithubDataRepository
import com.amanda.githubrepos.model.RepoListViewModelFactory

// controls object creation, will be useful for testing/mocking
object Injection {

    fun provideApiRepository(): GithubDataRepository {
        return GithubDataRepository(GithubServiceClient.create(BASE_URL))
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return RepoListViewModelFactory(provideApiRepository())
    }
}