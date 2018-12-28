package renatoarg.xapokotlin.data

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import renatoarg.xapokotlin.data.models.GithubResponse
import renatoarg.xapokotlin.data.models.Issue
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.data.provider.GithubService
import renatoarg.xapokotlin.data.provider.GithubProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubDao {
    private val TAG: String = "GithubDao"

    private val itemsList = mutableListOf<Item>()
    private val items = MutableLiveData<List<Item>>()
    private val image = MutableLiveData<Bitmap>()
    private val issues = MutableLiveData<List<Issue>>()
    private val githubService = GithubProvider.retrofitInstance?.create(GithubService::class.java)

    init {
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

    fun getIssuesList(url: String) {
        val callGetItemIssues = githubService?.getIssues(url)
        callGetItemIssues?.enqueue(object : Callback<List<Issue>> {
            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>) {
                if (response.isSuccessful) {
                    issues.value = response as List<Issue>
                } else {
                    Log.w(TAG, "response code: " + response.code())
                }
            }
            override fun onFailure(call: Call<List<Issue>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    fun loadImage(url: String) {
        object: com.squareup.picasso.Target {
            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image.value = bitmap
            }
        }
    }

    fun getImage() = image as LiveData<Bitmap>

    fun getItems() = items as LiveData<List<Item>>

    fun getIssues() = issues as LiveData<List<Issue>>

    fun setImageUrl(url: String) {
        loadImage(url)
    }
}