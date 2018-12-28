package renatoarg.xapokotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import renatoarg.xapokotlin.data.GithubRepository

class GithubViewModelFactory(private val githubRepository: GithubRepository)
    :ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GithubMainViewModel(githubRepository) as T
    }
}