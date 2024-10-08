package com.example.bookshelf.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.BookDetailScreen
import com.example.bookshelf.ui.screens.BookListScreen
import com.example.bookshelf.ui.screens.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfApp() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            )
        }
    ) { innerPadding ->
        val bookViewModel: BookViewModel =
            viewModel(factory = BookViewModel.Factory)
        NavHost(
            navController = navController,
            startDestination = "book_list"
        ) {
            composable("book_list") {
                BookListScreen(
                    viewModel = bookViewModel,
                    bookshelfUiState = bookViewModel.bookshelfUiState,
                    onClick = { bookId ->
                        navController.navigate(route = "book_details/${bookId}")
                    },
                    contentPadding = innerPadding
                )
            }
            composable("book_details/{bookId}") { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId")
                BookDetailScreen(
                    bookId = bookId.toString(),
                    viewModel = bookViewModel,
                    paddingValues = innerPadding
                )
            }
        }
    }
}
