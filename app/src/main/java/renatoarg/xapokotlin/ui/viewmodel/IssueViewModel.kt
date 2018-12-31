package renatoarg.xapokotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import renatoarg.xapokotlin.data.GithubRepository
import renatoarg.xapokotlin.data.models.Item

data class IssueViewModel(val title: String, val state: String)