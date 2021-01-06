package com.amanda.githubrepos.model

import com.amanda.githubrepos.api.GithubService


class GithubDataRepository(private val service: GithubService) {
    suspend fun user(user: String?) = service.user(user)
    suspend fun repos(user: String?) = service.repos(user)
}