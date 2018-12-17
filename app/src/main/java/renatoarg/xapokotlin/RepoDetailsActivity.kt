package renatoarg.xapokotlin

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import renatoarg.xapokotlin.models.Issue
import renatoarg.xapokotlin.models.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.HashSet

import android.view.View.GONE
import kotlinx.android.synthetic.main.activity_repo_details.*

class RepoDetailsActivity : AppCompatActivity(), IssuesAdapter.IssuesAdapterInterface {

    private val protectedFromGarbageCollectorTargets = HashSet<Target>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        title = "Xapo test"

        // recovers the item from Intent
        val gson = Gson()
        val item = gson.fromJson(intent.getStringExtra("item"), Item::class.java)

        // loads the owner image
        loadOwnerImage(item)

        // populates the views with item´s data
        populateViews(item)

        // loads the issues of this specific item
        loadIssues(item.issues_url!!.replace("{/number}", ""))

        // creates alpha animations
        createAlphaAnimations()
    }

    // creates alpha animations
    private fun createAlphaAnimations() {
        Log.d(TAG, "createAlphaAnimations: ")
        val animation1 = AlphaAnimation(0.0f, 1.0f)
        animation1.duration = 500
        animation1.startOffset = 300
        animation1.fillAfter = true
        cardView.startAnimation(animation1)

        val animation2 = AlphaAnimation(0.0f, 1.0f)
        animation2.duration = 500
        animation2.startOffset = 600
        animation2.fillAfter = true
        cardView2.startAnimation(animation2)
    }

    // loads the issues for this specific repo
    private fun loadIssues(url: String) {
        Log.d(TAG, "loadIssues: ")
        val githubService = RetrofitProvider.retrofitInstance?.create(GithubService::class.java)
        val callIssues = githubService?.getIssues(url)
        callIssues?.enqueue(object : Callback<List<Issue>> {
            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>) {
                if (response.isSuccessful) {
                    createIssuesList(response)
                } else {
                    Log.i(TAG, "response code: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<Issue>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    private fun populateViews(item: Item) {
        Log.d(TAG, "populateViews: ")
        tv_full_name.text = item.full_name
        tv_desc.text = item.description
        tv_owner.text = item.owner!!.login
        tv_last_update.text = "last update: " + item.updated_at
        tv_url.text = item.homepage
        tv_forks.text = "Forks: " + item.forks_count
        tv_open_issues.text = "Issues list:"
    }

    // loads owner image
    private fun loadOwnerImage(item: Item) {
        Log.d(TAG, "loadOwnerImage: ")
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val iv_owner = findViewById<ImageView>(R.id.iv_owner)
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom) {
                protectedFromGarbageCollectorTargets.remove(this)
                iv_owner.setImageBitmap(bitmap)
                iv_owner.visibility = View.VISIBLE
                progressBar.visibility = GONE
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
                iv_owner.setImageDrawable(getDrawable(R.drawable.ic_not_found))
                iv_owner.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                iv_owner.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
        }
        protectedFromGarbageCollectorTargets.add(target)
        val url : String? = item.owner!!.avatar_url
        Picasso.get().load(url).into(target)
    }

    // once the issues are loaded display the data on the recyclerView
    private fun createIssuesList(issues: Response<List<Issue>>) {
        Log.d(TAG, "createIssuesList: ")
        val issuesAdapter = IssuesAdapter(this, issues)
        rv_issues.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_issues.adapter = issuesAdapter
        rv_issues.visibility = View.VISIBLE
        progress_bar.visibility = GONE
    }


    override fun onBindViewHolder(holder: IssuesAdapter.ViewHolder, position: Int, issues: List<Issue>?) {
        Log.d(TAG, "onBindViewHolder: ")
        holder.title.text = issues!![position].title
        holder.state.text = "State: " + issues[position].state!!
    }

    override fun onBackPressed() {
        rv_issues.visibility = GONE

        val animation1 = AlphaAnimation(1.0f, 0.0f)
        animation1.duration = 200
        animation1.startOffset = 50
        animation1.fillAfter = true
        cardView.startAnimation(animation1)

        val animation2 = AlphaAnimation(1.0f, 0.0f)
        animation2.duration = 200
        animation2.startOffset = 50
        animation2.fillAfter = true
        cardView2.startAnimation(animation2)

        super.onBackPressed()
    }

    companion object {
        private val TAG = "RepoDetailsActivity"
    }


}
