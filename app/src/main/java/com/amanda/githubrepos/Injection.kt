package com.amanda.githubrepos

import androidx.lifecycle.ViewModelProvider
import com.amanda.githubrepos.api.GithubServiceClient
import com.amanda.githubrepos.model.GithubDataRepository
import com.amanda.githubrepos.model.RepoListViewModelFactory

/***
 * Controls object creation, will be useful for testing/mocking
 * Ideally the repository and the viewmodel should only be accessible
 * through this injection class.
 */
object Injection {

    fun provideApiRepository(): GithubDataRepository {
        return GithubDataRepository(GithubServiceClient.create(BASE_URL))
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return RepoListViewModelFactory(provideApiRepository())
    }
}