package renatoarg.xapokotlin.data.provider

import renatoarg.xapokotlin.data.models.GithubResponse
import renatoarg.xapokotlin.data.models.Issue
import renatoarg.xapokotlin.ui.viewmodel.IssueViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubService {

    @GET("search/repositories")
    fun getGithubItems(@Query("q") query: String, @Query("sort") sort: String): Call<GithubResponse>

    @GET
    fun getIssues(@Url url: String): Call<List<IssueViewModel>>
}