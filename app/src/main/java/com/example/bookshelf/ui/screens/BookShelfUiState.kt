package com.example.bookshelf.ui.screens

import com.example.bookshelf.model.Book

sealed class BookshelfUiState {
    object Loading : BookshelfUiState()
    data class Success(val books: List<Book>?) : BookshelfUiState()
    object Error : BookshelfUiState()
}
sealed class BookDetailUiState {
    object Loading : BookDetailUiState()
    data class Success(val book: Book) : BookDetailUiState()
    object Error : BookDetailUiState()
}