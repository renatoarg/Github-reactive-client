package renatoarg.xapokotlin.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_github_main.*
import renatoarg.xapokotlin.*
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.ui.adapters.ItemsAdapter
import renatoarg.xapokotlin.ui.viewmodel.GithubMainViewModel
import renatoarg.xapokotlin.utils.InjectorUtils

class GithubMainActivity : AppCompatActivity(), ItemsAdapter.ReposAdapterInterface {

    private val TAG = "GithubMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_main)
        if(!isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show()
            val handler = Handler()
            handler.postDelayed({ finish() }, 1000)
            return
        }
        initializeUi()
    }


    // get repositories from github
    private fun initializeUi() {
        Log.d(TAG, "initializeUi: ")
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Xapo test kotlin"
        toolbar.subtitle = "By Renato Goncalves"

        val factory = InjectorUtils.provideGithubViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory).get(GithubMainViewModel::class.java)

        rv_repos.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        viewModel.getItems().observe(this, Observer {
            val reposAdapter = ItemsAdapter(this, viewModel.getItems().value)
            rv_repos.adapter = reposAdapter
            rv_repos.adapter!!.notifyDataSetChanged()
            rv_repos.visibility = View.VISIBLE
            progress_bar.visibility = View.GONE
        })
    }

    private fun isNetworkAvailable(activity:AppCompatActivity):Boolean{
        val connectivityManager= activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo= connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    override fun onClick(item: Item, holder: ItemsAdapter.ViewHolder) {
        val i = Intent(this@GithubMainActivity, DetailsActivity::class.java)
        val gson = Gson()
        val mItem = gson.toJson(item)
        i.putExtra("item", mItem)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val fullNamePair = Pair.create(holder.tv_full_name as View, "tFullName")
        val descPair = Pair.create(holder.tv_desc as View, "tDesc")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@GithubMainActivity, fullNamePair, descPair)
        ActivityCompat.startActivity(this@GithubMainActivity, i, options.toBundle())
    }

}
