package com.example.bookshelf.data

import com.example.bookshelf.network.GetBooksApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val bookRepository: BookRepository
}

class DefaultConTainer : AppContainer {
    private val apiKey = "AIzaSyDWXLaN_hwveG_v1li9t-ly7gB8Bwd84XM"

    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        // Add apikey to url
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        // Create request with url api
        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()

        .addInterceptor(apiKeyInterceptor)
        .build()
    private val contentType = "application/json".toMediaType()
    private val jsonConverter = Json { ignoreUnknownKeys = true }
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .client(okHttpClient)
        .addConverterFactory(jsonConverter.asConverterFactory(contentType))
        .build()
    private val retrofitService: GetBooksApiService by lazy {
        retrofit.create(GetBooksApiService::class.java)
    }
    override val bookRepository: BookRepository by lazy {
        BookRepositoryImpl(retrofitService)
    }
}