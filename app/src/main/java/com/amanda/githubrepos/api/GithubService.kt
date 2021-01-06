package com.amanda.githubrepos.api

import com.amanda.githubrepos.data.User
import com.amanda.githubrepos.data.UserReposItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


//Github REST API communication setup via Retrofit.
interface GithubService {

    @GET("users/{user}")
    suspend fun user(@Path("user") user: String?): User

    @GET("users/{user}/repos")
    suspend fun repos(@Path("user") user: String?): List<UserReposItem>
}

//make the retrofit client singleton to avoid recreating it on config changes
object GithubServiceClient {
    private const val BASE_URL = "https://api.github.com/"

    fun create(baseUrl: String = BASE_URL): GithubService {
        val logger = HttpLoggingInterceptor()
        logger.level = Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubService::class.java)
    }
}