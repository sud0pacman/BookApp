package com.sudo_pacman.bookapp.presenter.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.bookapp.R
import com.sudo_pacman.bookapp.databinding.ScreenBooksBinding
import com.sudo_pacman.bookapp.presenter.adapter.BookAdapter
import com.sudo_pacman.bookapp.utils.toToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BookScreen : Fragment(R.layout.screen_books) {
    private val binding by viewBinding(ScreenBooksBinding::bind)
    private val adapter = BookAdapter()
    private val viewModel: BookViewModel by viewModels<BookViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvBooks.adapter = adapter


        adapter.setOnDownloadClick { bookData ->
            findNavController().navigate(BookScreenDirections.actionBookScreenToReadScreen(bookData))
        }

        viewModel.getBooksData()

        viewModel.success.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)

        viewModel.error.onEach {
            requireContext().toToast("books screen error:  $it")
        }.launchIn(lifecycleScope)
    }
}