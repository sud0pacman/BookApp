package com.sudo_pacman.bookapp.presenter.read

import com.sudo_pacman.bookapp.data.BookData
import kotlinx.coroutines.flow.MutableSharedFlow

interface ReadViewModel {
    val success: MutableSharedFlow<BookData>
    val error: MutableSharedFlow<String>
}