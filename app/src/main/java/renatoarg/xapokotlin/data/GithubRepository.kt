package renatoarg.xapokotlin.data

import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.ui.viewmodel.ItemViewModel


class GithubRepository private constructor(private val githubDao: GithubDao) {

    fun getItems() = githubDao.getItems()

    fun getIssues() = githubDao.getIssues()

    fun getImage() = githubDao.getImage()

    fun setItem(item: Item) {
        githubDao.setItem(item)
    }

    fun getItem() = githubDao.getItem()

    fun getIssue() = githubDao.getIssue()

    companion object {
        @Volatile private var instance: GithubRepository? = null

        fun getInstance(githubDao: GithubDao) =
                instance ?: synchronized(this) {
                    instance ?: GithubRepository(githubDao).also { instance = it }
                }
    }
}