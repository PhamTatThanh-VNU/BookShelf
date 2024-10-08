package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.GoogleBooksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetBooksApiService {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): GoogleBooksResponse

    @GET("volumes/{id}")
    suspend fun getBookDetails(@Path("id") bookId: String): Book
}