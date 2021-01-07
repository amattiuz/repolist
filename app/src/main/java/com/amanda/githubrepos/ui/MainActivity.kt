package com.amanda.githubrepos.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.amanda.githubrepos.Injection
import com.amanda.githubrepos.R
import com.amanda.githubrepos.data.User
import com.amanda.githubrepos.data.UserReposItem
import com.amanda.githubrepos.databinding.ActivityMainBinding
import com.amanda.githubrepos.model.RepoListViewModel
import com.amanda.githubrepos.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity(), CoroutineScope by MainScope() {

    private lateinit var viewModel: RepoListViewModel
    private val adapter = RepoListAdapter(emptyList())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setup databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this

        //setup search button listener
        binding.userSearchButton.setOnClickListener { showUserInfo() }

        //setup view model
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(RepoListViewModel::class.java)

        //setup recyclerview stuff (via binding)
        binding.list.layoutManager =  LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.list.itemAnimator = DefaultItemAnimator()
        binding.list.adapter = adapter

        //init UI elements
        initUI()
    }

    private fun initUI() {
        showLoadingMessage(false)
        binding.avatarImg.visibility = View.INVISIBLE
        binding.userName.visibility = View.INVISIBLE
    }

    private fun showUserInfo() {
        //call api
        // observe the flow, make changes
        // do no block main thread
        launch {
            viewModel.user(usernameEditText.text.toString())
                .distinctUntilChanged()
                .collectLatest {
                    when (it.status) {
                        Status.SUCCESS -> updateUserInfo(it.data)
                        Status.LOADING -> showLoadingMessage(true)
                        Status.ERROR -> showErrorInfo(it.message ?: "")
                    }
                }
        }
    }

    private fun showUserRepos() {
        //call api
        // observe the flow, send data to adapter
        // do no block main thread
        launch {
            viewModel.repos(usernameEditText.text.toString())
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

    private fun updateUserInfo(data: User?) {
        showLoadingMessage(false)
        if (data != null) {
            adapter.clearRepoList()
            adapter.notifyDataSetChanged()
            setImageSource(data.avatar_url)
            binding.avatarImg.visibility = View.VISIBLE
            with(binding.userName) {
                this.text = data.name
                this.visibility = View.VISIBLE
            }
            showUserRepos()
        }
    }

    private fun setImageSource(url: String) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.avatar_placeholder)
            .apply(RequestOptions().override(120, 120))
            .into(binding.avatarImg)
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