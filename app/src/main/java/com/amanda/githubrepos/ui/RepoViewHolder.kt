package com.amanda.githubrepos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amanda.githubrepos.R
import com.amanda.githubrepos.data.UserReposItem

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.repo_name)
    private val description: TextView = itemView.findViewById(R.id.repo_description)

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_item_layout, parent, false)
            return RepoViewHolder(view)
        }
    }

    fun bind(item: UserReposItem?) {
       name.text = item?.name ?: ""
        description.text = item?.description ?: ""
    }

}