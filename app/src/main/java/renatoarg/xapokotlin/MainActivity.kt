package renatoarg.xapokotlin

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import renatoarg.xapokotlin.models.GithubRepos
import renatoarg.xapokotlin.models.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ReposAdapter.ReposAdapterInterface {
    override fun onBindViewHolder(holder: ReposAdapter.ViewHolder, position: Int, items: List<Item>?) {
        Log.d(TAG, "onBindViewHolder: ")
        holder.tv_full_name.text = items!![position].full_name
        holder.tv_desc.text = items[position].description
        holder.tv_stargazers_count.text = String.format("%.1fk", items[position].stargazers_count.toFloat()  / 1000)
        holder.tv_language.text = "Language: " + items[position].language
        holder.cl_wrapper.setOnClickListener { launchRepoDetailsActivity(items, position, holder) }
    }


    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show()
            val handler = Handler()
            handler.postDelayed({ finish() }, 1000)
            return
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Xapo test kotlin"
        toolbar.subtitle = "By Renato Goncalves"
        getRepos()
    }


    // get repositories from github
    private fun getRepos() {
        Log.d(TAG, "getRepos: ")
        // requests the android repositories ordered by number of stars
        val githubService = RetrofitProvider.retrofitInstance?.create(GithubService::class.java)
        val callRepos = githubService?.getGithubRepos("android", "stars")
        callRepos?.enqueue(object : Callback<GithubRepos> {
            override fun onResponse(call: Call<GithubRepos>, response: Response<GithubRepos>) {
                if (response.isSuccessful) {
                    createReposList(response.body()!!)
                } else {
                    Log.w(TAG, "response code: " + response.code())
                }
            }

            override fun onFailure(call: Call<GithubRepos>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    // once the repositores are loaded creates a recyclerView to display them
    private fun createReposList(repos: GithubRepos) {
        Log.d(TAG, "createReposList: ")
        val reposAdapter = ReposAdapter(this, repos.items)
        rv_repos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_repos.adapter = reposAdapter
        rv_repos.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    private fun isNetworkAvailable(activity:AppCompatActivity):Boolean{
        val connectivityManager= activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo= connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    // launches repo details activity
    private fun launchRepoDetailsActivity(items: List<Item>, position: Int, holder: ReposAdapter.ViewHolder) {
        Log.d(TAG, "launchRepoDetailsActivity: ")
        val i = Intent(this@MainActivity, RepoDetailsActivity::class.java)
        val gson = Gson()
        val mItem = gson.toJson(items[position])
        i.putExtra("item", mItem)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val fullNamePair = Pair.create(holder.tv_full_name as View, "tFullName")
        val descPair = Pair.create(holder.tv_desc as View, "tDesc")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, fullNamePair, descPair)
        ActivityCompat.startActivity(this@MainActivity, i, options.toBundle())
    }
}
