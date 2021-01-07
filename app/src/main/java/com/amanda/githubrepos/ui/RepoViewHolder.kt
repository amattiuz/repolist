package com.amanda.githubrepos.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.amanda.githubrepos.R
import com.amanda.githubrepos.data.UserReposItem

@Suppress("DEPRECATION")
class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.repo_name)
    private val description: TextView = itemView.findViewById(R.id.repo_description)

    //This is used for binding info to the bottom sheet dialog
    private var repo: UserReposItem? = null

    init {
        itemView.setOnClickListener {
            repo?.let {
                if(itemView.context is FragmentActivity) {
                    val fragment = RepoDetailsBottomSheetDialogFragment(it)
                    val manager: FragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
                    fragment.show(manager, fragment.tag)
                }

           }
        }
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_item_layout, parent, false)
            return RepoViewHolder(view)
        }
    }

    fun bind(item: UserReposItem?) {
        repo = item
       name.text = item?.name ?: ""
        description.text = item?.description ?: ""
    }

}