package com.fraggeil.ticketator.presentation.screens.post_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(
//    private val postRepository: PostRepository
): ViewModel() {
    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    fun onAction(action: PostAction){
        when(action){
            PostAction.OnBackClicked -> {}
            is PostAction.OnSelectedPostChange -> {
                _state.update {
                    it.copy(post = action.post, isLoading = false)
                } // todo
//                fetchPostById(action.post.id)
            }
        }
    }

    private fun fetchPostById(id: Int){
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
//            postRepository.getPostById(id)
//                .onSuccess { post ->
//                    _state.update {
//                        it.copy(post = post, isLoading = false)
//                    }
//                }
//                .onError {
//                    _state.update {
//                        it.copy(isLoading = false, error = "Error fetching post")
//                    }
//                }
        }
    }
}