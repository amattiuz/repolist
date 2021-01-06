package com.amanda.githubrepos.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanda.githubrepos.Injection
import com.amanda.githubrepos.R
import com.amanda.githubrepos.data.UserReposItem
import com.amanda.githubrepos.databinding.ActivityMainBinding
import com.amanda.githubrepos.model.RepoListViewModel
import com.amanda.githubrepos.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var viewModel: RepoListViewModel
    private val adapter = RepoListAdapter(emptyList())
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this

        //setup view model
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(RepoListViewModel::class.java)


        //setup recyclerview stuff
        binding.list.layoutManager =  LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.list.itemAnimator = DefaultItemAnimator()

        binding.list.adapter = adapter

       showUserRepos()
    }

    private fun showUserRepos() {
        //call api
        // observe the flow, send data to adapter
        // do no block main thread
        launch {
            viewModel.repos("amattiuz")
                .distinctUntilChanged()
                .collectLatest {
                    when (it.status) {
                        Status.SUCCESS -> updateRepoList(it.data)
                        Status.LOADING -> showLoadingMessage(true)
                        Status.ERROR -> showErrorInfo(it.message ?: "")
                    }
                }
        }
    }

    //send data received from server to the adapter
    private fun updateRepoList(data: List<UserReposItem>?) {
        showLoadingMessage(false)
        if(data != null) {
            adapter.updateRepoList(data)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showLoadingMessage(show: Boolean) {
        if (show) {
            binding.showProgress.visibility = View.VISIBLE
        } else {
            binding.showProgress.visibility = View.GONE
        }
    }

    private fun showErrorInfo(message: String) {
        showLoadingMessage(false)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}