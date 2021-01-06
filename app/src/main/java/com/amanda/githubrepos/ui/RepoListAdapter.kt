package com.amanda.githubrepos.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amanda.githubrepos.data.UserReposItem

class RepoListAdapter(items: List<UserReposItem>): RecyclerView.Adapter<RepoViewHolder>() {

    private val repos: MutableList<UserReposItem> = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder.create(parent)

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        holder.bind(repos[position])


    override fun getItemCount(): Int = repos.size


    fun updateRepoList(newRepos: List<UserReposItem>) = repos.addAll(newRepos)

}