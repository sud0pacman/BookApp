package com.sudo_pacman.bookapp.presenter.book

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.bookapp.data.BookData
import com.sudo_pacman.bookapp.domain.AppRepository
import com.sudo_pacman.bookapp.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookViewModelImpl @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel(), BookViewModel {


    override val success = MutableSharedFlow<List<BookData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val error: MutableSharedFlow<String> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)


    override fun getBooksData() {
        appRepository.getImages()
            .onEach { it ->
                it.onSuccess {
                    success.tryEmit(it)
                }

                it.onFailure {
                    error.tryEmit(it.message.toString())
                    "model fail: ${it.message.toString()}".myLog()
                }
            }
            .launchIn(viewModelScope)

    }

}