package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.utils.removeHtmlTags


@Composable
fun SearchBar(viewModel: BookViewModel = viewModel()) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search Books") },
        trailingIcon = {
            IconButton(onClick = {
                if (text.isNotBlank()) {
                    viewModel.searchBooks(text)
                }
            }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search // Display Search on keyboard
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (text.isNotBlank()) {
                    viewModel.searchBooks(text)
                }
            }
        )
    )
}

@Composable
fun BookDetailScreen(
    bookId: String,
    viewModel: BookViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    LaunchedEffect(bookId) {
        viewModel.getBookDetails(bookId)
    }
    when (val state = viewModel.bookDetailUiState) {
        is BookDetailUiState.Loading -> LoadingIndicator()
        is BookDetailUiState.Success -> BookDetailContent(
            book = state.book,
            paddingValues = paddingValues
        )

        else -> ErrorMessage()
    }
}

@Composable
private fun BookDetailContent(book: Book?,paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues
    ) {
        item {
            book?.volumeInfo?.imageLinks?.thumbnail?.let { url ->
                AsyncImage(
                    model = url.replace("http:", "https:"),
                    contentDescription = "Book cover",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = book?.volumeInfo?.title.toString(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            book?.volumeInfo?.authors?.let { authors ->
                Text(
                    text = "by ${authors.joinToString(", ")}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = book?.volumeInfo?.description?.removeHtmlTags() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BookGrid(
    viewModel: BookViewModel,
    books: List<Book>?,
    onClick: (String) -> Unit
) {
    SearchBar(viewModel = viewModel)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp)
    ) {
        try {
            if (books != null) {
                items(books) { book ->
                    BookItem(book = book, onClick = onClick)
                }
            }
        } catch (e: Exception) {
            Log.d("Book Error", e.message.toString())
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onClick(book.id)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        Column {
            book.volumeInfo.imageLinks?.thumbnail?.let { url ->
                AsyncImage(
                    model = url.replace("http:", "https:"),
                    contentDescription = "Book cover for ${book.volumeInfo.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
            Text(
                text = book.volumeInfo.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge
            )
            book.volumeInfo.authors?.let { authors ->
                Text(
                    text = "by ${authors.joinToString(", ")}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}