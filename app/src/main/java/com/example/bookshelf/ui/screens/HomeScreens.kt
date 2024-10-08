package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookListScreen(
    viewModel: BookViewModel,
    onClick: (String) -> Unit,
    bookshelfUiState: BookshelfUiState,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(modifier = Modifier.padding(contentPadding)) {
        when (bookshelfUiState) {
            is BookshelfUiState.Loading -> LoadingIndicator()
            is BookshelfUiState.Success -> {
                if (bookshelfUiState.books?.isEmpty() == true) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No books found", color = Color.Gray)
                    }
                } else {
                    BookGrid(
                        viewModel = viewModel,
                        books = bookshelfUiState.books,
                        onClick = onClick
                    )
                }
            }

            is BookshelfUiState.Error -> ErrorMessage()
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error Loading", color = Color.Red)
    }
}

