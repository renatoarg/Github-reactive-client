package renatoarg.xapokotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import renatoarg.xapokotlin.data.GithubRepository
import renatoarg.xapokotlin.data.models.Item

class ItemDetailsViewModel(private val githubRepository: GithubRepository)
    : ViewModel(){

    fun setItem(item: Item) {
        githubRepository.setItem(item)
    }

    fun getItem() = githubRepository.getItem()

    fun getIssues() = githubRepository.getIssues()

    fun getImage() = githubRepository.getImage()

}