package com.amanda.githubrepos.data

data class UserReposItem(
    val description: String,
    val fork: Boolean,
    val name: String,
    val stargazers_count: Int,
    val updated_at: String
)