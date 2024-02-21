package com.sudo_pacman.bookapp.presenter.book

import com.sudo_pacman.bookapp.data.BookData
import kotlinx.coroutines.flow.MutableSharedFlow

interface BookViewModel {
    val success: MutableSharedFlow<List<BookData>>
    val error: MutableSharedFlow<String>

    fun getBooksData()
}