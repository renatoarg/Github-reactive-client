package renatoarg.xapokotlin.data.models

import renatoarg.xapokotlin.ui.viewmodel.ItemViewModel

data class GithubResponse(val total_count: Long, val incomplete_results: Boolean, val items: List<ItemViewModel>)