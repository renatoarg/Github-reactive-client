package renatoarg.xapokotlin.data

import renatoarg.xapokotlin.data.models.Item


class GithubRepository private constructor(private val githubDao: GithubDao) {

    fun getItems() = githubDao.loadItems()

    fun getIssues() = githubDao.getIssues()

    fun getImage() = githubDao.getImage()

    fun setItem(item: Item) {
        githubDao.setItem(item)
    }

    fun getItem() = githubDao.getItem()

    companion object {
        @Volatile private var instance: GithubRepository? = null

        fun getInstance(githubDao: GithubDao) =
                instance ?: synchronized(this) {
                    instance ?: GithubRepository(githubDao).also { instance = it }
                }
    }
}