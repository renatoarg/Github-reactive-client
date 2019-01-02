package renatoarg.xapokotlin.utils

import renatoarg.xapokotlin.data.GithubDatabase
import renatoarg.xapokotlin.data.GithubRepository
import renatoarg.xapokotlin.ui.viewmodel.GithubViewModelFactory
import renatoarg.xapokotlin.ui.viewmodel.ItemDetailsViewModelFactory

object InjectorUtils {

    fun provideGithubViewModelFactory(): GithubViewModelFactory {
        val githubRepository = GithubRepository.getInstance(GithubDatabase.getInstance().githubDao)
        return GithubViewModelFactory(githubRepository)
    }
    fun provideItemDetailsViewModelFactory(): ItemDetailsViewModelFactory {
        val githubRepository = GithubRepository.getInstance(GithubDatabase.getInstance().githubDao)
        return ItemDetailsViewModelFactory(githubRepository)
    }
}