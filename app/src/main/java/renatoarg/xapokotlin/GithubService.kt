package renatoarg.xapokotlin

import renatoarg.xapokotlin.models.GithubRepos
import renatoarg.xapokotlin.models.Issue
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubService {

    @GET("search/repositories")
    fun getGithubRepos(@Query("q") query: String, @Query("sort") sort: String): Call<GithubRepos>

    @GET
    fun getIssues(@Url url: String): Call<List<Issue>>
}