package renatoarg.xapokotlin.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import renatoarg.xapokotlin.data.models.Issue
import renatoarg.xapokotlin.data.models.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.HashSet

import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_github_main.*
import kotlinx.android.synthetic.main.activity_details.*
import renatoarg.xapokotlin.data.provider.GithubService
import renatoarg.xapokotlin.R
import renatoarg.xapokotlin.data.provider.GithubProvider
import renatoarg.xapokotlin.ui.adapters.IssuesAdapter
import renatoarg.xapokotlin.ui.viewmodel.ItemDetailsViewModel
import renatoarg.xapokotlin.utils.InjectorUtils

class DetailsActivity : AppCompatActivity(), IssuesAdapter.IssuesAdapterInterface {

    private val protectedFromGarbageCollectorTargets = HashSet<Target>()
    private lateinit var mainBinding : renatoarg.xapokotlin.databinding.ActivityDetailsBinding
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        title = "Xapo test"

        // recovers the item from Intent
        val gson = Gson()
        item = gson.fromJson(intent.getStringExtra("item"), Item::class.java)
        val factory = InjectorUtils.provideItemDetailsViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory).get(ItemDetailsViewModel::class.java)
        mainBinding.item = item

        // loads the issues of this specific item
        loadIssues(item.issues_url.replace("{/number}", ""))

        // creates alpha animations
        createAlphaAnimations()

    }

    // get repositories from github
    private fun initializeUi() {
        Log.d(TAG, "initializeUi: ")
//        setSupportActionBar(toolbar)
//        supportActionBar!!.title = "Xapo test kotlin"
//        toolbar.subtitle = "By Renato Goncalves"

        val factory = InjectorUtils.provideItemDetailsViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory).get(ItemDetailsViewModel::class.java)

//        rv_repos.layoutManager = LinearLayoutManager(
//            this,
//            RecyclerView.VERTICAL,
//            false
//        )

        viewModel.setImageUrl(item.owner.avatar_url)
//        viewModel.getImage().observe(this, Observer {
//            iv_owner.setImageBitmap(viewModel.getImage().value)
//        })

//        viewModel.getIssues().observe(this, Observer {
//            val reposAdapter = IssuesAdapter(this, viewModel.getIssues().value)
//            rv_repos.adapter = reposAdapter
//            rv_repos.adapter!!.notifyDataSetChanged()
//            rv_repos.visibility = View.VISIBLE
//            progress_bar_repo.visibility = GONE
//        })
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
        val githubService = GithubProvider.retrofitInstance?.create(GithubService::class.java)
        val callIssues = githubService?.getIssues(url)
        callIssues?.enqueue(object : Callback<List<Issue>> {
            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>) {
                if (response.isSuccessful) {
//                    createIssuesList(response.body())
                } else {
                    Log.i(TAG, "response code: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<Issue>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    // loads owner image
//    private fun loadOwnerImage(item: Item) {
//        Log.d(TAG, "loadOwnerImage: ")
//        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
//        val iv_owner = findViewById<ImageView>(R.id.iv_owner)
//        val target = object : Target {
//            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom) {
//                protectedFromGarbageCollectorTargets.remove(this)
//                iv_owner.setImageBitmap(bitmap)
//                iv_owner.visibility = View.VISIBLE
//                progressBar.visibility = GONE
//            }
//
//            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
//                iv_owner.setImageDrawable(getDrawable(R.drawable.ic_not_found))
//                iv_owner.visibility = View.VISIBLE
//                progressBar.visibility = View.GONE
//            }
//
//            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                iv_owner.visibility = View.INVISIBLE
//                progressBar.visibility = View.VISIBLE
//            }
//        }
//        protectedFromGarbageCollectorTargets.add(target)
//        val url : String? = item.owner!!.avatar_url
//        Picasso.get().load(url).into(target)
//    }

    // once the issues are loaded display the data on the recyclerView
    private fun createIssuesList(issues: List<Issue>) {
        Log.d(TAG, "createIssuesList: ")
        val issuesAdapter = IssuesAdapter(this, issues)
        rv_issues.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
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
        private val TAG = "DetailsActivity"
    }


}
