package renatoarg.xapokotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import renatoarg.xapokotlin.data.GithubRepository
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.data.models.Licence
import renatoarg.xapokotlin.data.models.Owner
import java.util.*

data class ItemViewModel(val full_name: String,
                         val description: String,
                         val stargazers_count: Int,
                         val language: String)