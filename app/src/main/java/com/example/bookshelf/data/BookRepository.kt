package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.GetBooksApiService

interface BookRepository {
    suspend fun searchBook(query: String): List<Book>?
    suspend fun getBookDetails(bookId: String): Book
}

class BookRepositoryImpl(private val getBooksApiService: GetBooksApiService) : BookRepository {
    override suspend fun searchBook(query: String): List<Book>? {
        val respond = getBooksApiService.searchBooks(query)
        return respond.items
    }

    override suspend fun getBookDetails(bookId: String): Book {
        return getBooksApiService.getBookDetails(bookId)
    }
}