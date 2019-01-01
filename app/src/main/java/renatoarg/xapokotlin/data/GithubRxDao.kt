package renatoarg.xapokotlin.data

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import renatoarg.xapokotlin.data.models.GithubResponse
import renatoarg.xapokotlin.data.models.Issue
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.data.provider.GithubProvider
import renatoarg.xapokotlin.data.provider.GithubService
import renatoarg.xapokotlin.ui.viewmodel.IssueViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRxDao {
    private val TAG: String = "GithubDao"

    private val itemsList = mutableListOf<Item>()
    private val issuesList = mutableListOf<IssueViewModel>()
    private val items = MutableLiveData<List<Item>>()
    private val issues = MutableLiveData<List<IssueViewModel>>()
    private val item = MutableLiveData<Item>()
    private val issue = MutableLiveData<Issue>()
    private val image = MutableLiveData<Bitmap>()

    private val githubService = GithubProvider.retrofitInstance?.create(GithubService::class.java)

    init {
        issues.apply { value = emptyList() }
        items.apply { value = emptyList() }

        val callGithubItems = githubService?.getGithubItems("android", "stars")
        callGithubItems?.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    itemsList.clear()
                    itemsList.addAll(response.body()!!.items)
                    items.value = itemsList
                } else {
                    Log.w(TAG, "response code: " + response.code())
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }


    private fun getIssuesList(url: String) {
        val callGetItemIssues = githubService?.getIssues(url)
        callGetItemIssues?.enqueue(object : Callback<List<IssueViewModel>> {
            override fun onResponse(call: Call<List<IssueViewModel>>, response: Response<List<IssueViewModel>>) {
                if (response.isSuccessful) {
                    issuesList.clear()
                    issuesList.addAll(response.body() as List<IssueViewModel>)
                    issues.value = issuesList
                } else {
                    Log.w(TAG, "response code: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<IssueViewModel>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    fun getImage() = image as LiveData<Bitmap>

    fun getItems() = items as LiveData<List<Item>>

    fun getItem() = item as LiveData<Item>

    fun getIssues() = issues as LiveData<List<IssueViewModel>>

    fun setItem(item: Item) {
        this.item.value = item
        getIssuesList(item.issues_url.replace("{/number}", ""))
    }

    fun getIssue() = issue as LiveData<Issue>
}