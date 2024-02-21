package com.sudo_pacman.bookapp.presenter.read

import androidx.lifecycle.ViewModel
import com.sudo_pacman.bookapp.data.BookData
import com.sudo_pacman.bookapp.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class ReadViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), ReadViewModel{

    val so = ""
    override val success = MutableSharedFlow<BookData>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val error: MutableSharedFlow<String> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

}