package renatoarg.xapokotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import renatoarg.xapokotlin.data.GithubRepository

class GithubMainViewModel(private val githubRepository: GithubRepository)
    : ViewModel(){

    fun getItems() = githubRepository.getItems()
}