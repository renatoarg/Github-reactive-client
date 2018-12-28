package renatoarg.xapokotlin.data


class GithubRepository private constructor(private val githubDao: GithubDao) {

    fun getItems() = githubDao.getItems()

    fun getIssues() = githubDao.getIssues()

    fun getImage() = githubDao.getImage()

    fun setImageUrl(url: String) {
        githubDao.setImageUrl(url)
    }

    companion object {
        @Volatile private var instance: GithubRepository? = null

        fun getInstance(githubDao: GithubDao) =
                instance ?: synchronized(this) {
                    instance ?: GithubRepository(githubDao).also { instance = it }
                }
    }
}