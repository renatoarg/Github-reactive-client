package renatoarg.xapokotlin.data

class GithubDatabase private constructor() {

    var githubDao = GithubDao()
    private set

    companion object {
        @Volatile private var instance: GithubDatabase? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: GithubDatabase().also { instance = it }
                }
    }
}