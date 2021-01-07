package com.amanda.githubrepos.model

import com.amanda.githubrepos.api.GithubService

/**
 * Following the repository pattern even though we only have one data source (API)
 * This could be easily extended to use a second API or a local DB by using this pattern.
 */
class GithubDataRepository(private val service: GithubService) {
    suspend fun user(user: String?) = service.user(user)
    suspend fun repos(user: String?) = service.repos(user)
}