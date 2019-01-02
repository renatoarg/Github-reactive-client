package renatoarg.xapokotlin.ui

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_github_main.*
import kotlinx.android.synthetic.main.row_repo.*
import renatoarg.xapokotlin.R
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.ui.adapters.ItemsAdapter
import renatoarg.xapokotlin.ui.viewmodel.GithubMainViewModel
import renatoarg.xapokotlin.utils.AppUtils
import renatoarg.xapokotlin.utils.InjectorUtils
import android.util.Pair as UtilPair

class GithubMainActivity : AppCompatActivity() {

    var mItems = mutableListOf<Item>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_main)

        // setups toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Xapo test kotlin"
        toolbar.subtitle = "By Renato Goncalves"

        // checks Internet connection
        if(!AppUtils.isNetworkAvailable(this)) {
            progress_bar.visibility = View.GONE
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_LONG).show()
            val handler = Handler()
            handler.postDelayed({ finish() }, 3000)
            return
        }

        // setups viewModel
        val factory = InjectorUtils.provideGithubViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory).get(GithubMainViewModel::class.java)

        // Rx observer
        viewModel.getItems()!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {list ->
                rv_repos.adapter.let {
                    if (it is ItemsAdapter) {
                        it.replaceItems(list)
                        progress_bar.visibility = if (list.isNullOrEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }


        // setups recyclerView
        val adapter = ItemsAdapter(this@GithubMainActivity, mItems)
        rv_repos.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rv_repos.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        rv_repos.adapter = adapter
        adapter.setOnItemClickListener(object : ItemsAdapter.OnItemClickListener {
            override fun onClick(view: View, item: Item) {
                val i = Intent(this@GithubMainActivity, DetailsActivity::class.java)
                val gson = Gson()
                i.putExtra("item", gson.toJson(item))
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                val fullNamePair = UtilPair.create(view.findViewById(tv_repo_row_full_name.id) as View, "tFullName")
                val descPair = UtilPair.create(view.findViewById(tv_repo_row_desc.id) as View, "tDesc")
                val starsPair = UtilPair.create(view.findViewById(ll_repo_stars.id) as View, "tStars")
                val options = ActivityOptions.makeSceneTransitionAnimation(this@GithubMainActivity, fullNamePair, descPair, starsPair)
                startActivity(i, options.toBundle())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}
