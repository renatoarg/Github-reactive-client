package renatoarg.xapokotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import renatoarg.xapokotlin.data.GithubRepository
import renatoarg.xapokotlin.data.models.Item

class ItemDetailsViewModel(private val githubRepository: GithubRepository)
    : ViewModel(){

    var item: Item? = null

    fun getIssues() = githubRepository.getIssues()

    fun getImage() = githubRepository.getImage()

    fun setImageUrl(url: String) {
        githubRepository.setImageUrl(url)
    }
}