package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookApplication
import com.example.bookshelf.data.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set
    var bookDetailUiState: BookDetailUiState by mutableStateOf(BookDetailUiState.Loading)
        private set

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            try {
                val books = bookRepository.searchBook(query)
                bookshelfUiState = BookshelfUiState.Success(books)
            } catch (e: Exception) {
                Log.d("Book Searching: ", e.message.toString())
            }
        }
    }

    fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            bookDetailUiState = BookDetailUiState.Loading
            try {
                val book = bookRepository.getBookDetails(bookId)
                bookDetailUiState = BookDetailUiState.Success(book)
            } catch (e: Exception) {
                Log.d("Book Details: ", e.message.toString())
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookApplication)
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }

    fun retry(query: String) {
        searchBooks(query)
    }
}