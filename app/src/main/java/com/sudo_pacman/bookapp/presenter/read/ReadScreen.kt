package com.sudo_pacman.bookapp.presenter.read

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.bookapp.R
import com.sudo_pacman.bookapp.databinding.ScreenReadBinding
import com.sudo_pacman.bookapp.domain.AppRepository
import com.sudo_pacman.bookapp.utils.myLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

@AndroidEntryPoint
class ReadScreen : Fragment(R.layout.screen_read) {

    private val binding by viewBinding(ScreenReadBinding::bind)
    private val navArgs: ReadScreenArgs by navArgs()
    private val repository = AppRepository()
    private val viewModel: ReadViewModel by viewModels<ReadViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        downloadFile()

    }

    private fun downloadFile() {
        val bookData = navArgs.book

        val document = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        "document name ${document.name}".myLog()

        var isHaveBook = false

        val files = document.listFiles()

        if (files?.isNotEmpty() == true) {
            for (index in files.indices) {
                if (files[index].name.startsWith(bookData.orderName)) {
                    isHaveBook = true

                    Toast.makeText(requireContext(), files[index].name, Toast.LENGTH_SHORT).show()

                    "topildi ${files[index].name}".myLog()

                    binding.pdfView.recycle()
                    binding.pdfView
                        .fromFile(files[index])
                        .enableSwipe(false)
                        .swipeHorizontal(true)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load()

                    break
                }
            }
        }

        if (!isHaveBook) {
            val temp = File.createTempFile(bookData.orderName, ".pdf", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))

            repository.downloadFile(temp, bookData.orderName).onEach { it ->
                it.onSuccess {

                    binding.pdfView
                        .fromFile(it)
                        .enableSwipe(false)
                        .swipeHorizontal(true)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load()

                    Toast.makeText(requireContext(), it.absolutePath, Toast.LENGTH_SHORT).show()
                }

                it.onFailure {
                    Log.d("TTT", "download error: ${it.message.toString()}")
                }
            }.launchIn(lifecycleScope)
        }


    }
}
