package com.amanda.githubrepos.model

import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amanda.githubrepos.data.User
import com.amanda.githubrepos.data.UserReposItem
import com.amanda.githubrepos.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepoListViewModel(private val repository: GithubDataRepository) : ViewModel() {

    fun user(user: String): Flow<Resource<User>> = flow {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(repository.user(user)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message))
            }
        }


    fun repos(user: String): Flow<Resource<List<UserReposItem>>> = flow {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(repository.repos(user)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message))
            }
        }

}

// Using our own model view factory instead of the default one from Android
// This makes it easier to use params to build the viewmodel
class RepoListViewModelFactory(private val repository: GithubDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RepoListViewModel::class.java)) {
            return RepoListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}