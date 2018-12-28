package renatoarg.xapokotlin.data.models

data class GithubResponse(val total_count: Long, val incomplete_results: Boolean, val items: List<Item>)