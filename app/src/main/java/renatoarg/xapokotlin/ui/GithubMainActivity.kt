package renatoarg.xapokotlin.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_github_main.*
import renatoarg.xapokotlin.R
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.ui.adapters.ItemsAdapter
import renatoarg.xapokotlin.ui.viewmodel.GithubMainViewModel
import renatoarg.xapokotlin.ui.viewmodel.ItemViewModel
import renatoarg.xapokotlin.utils.AppUtils
import renatoarg.xapokotlin.utils.InjectorUtils
import android.util.Pair as UtilPair

class GithubMainActivity : AppCompatActivity(), ItemsAdapter.ItemsAdapterContract {

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
        viewModel.getItems().observe(this, Observer<List<ItemViewModel>> { list ->
            rv_repos.adapter.let {
                if (it is ItemsAdapter) {
                    it.replaceItems(list)
                    progress_bar.visibility = if (list.isNullOrEmpty())  View.VISIBLE else  View.GONE
                }
            }
        })

        var mainBinding : renatoarg.xapokotlin.databinding.ActivityGithubMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_github_main)
        mainBinding.setLifecycleOwner(this@GithubMainActivity)

        // setups recyclerView
        rv_repos.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rv_repos.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        rv_repos.adapter = ItemsAdapter(this@GithubMainActivity, viewModel.getItems().value!!)
    }


    override fun onClick(itemViewModel: ItemViewModel, holder: ItemsAdapter.ViewHolder) {
//        val i = Intent(this@GithubMainActivity, DetailsActivity::class.java)
//        val gson = Gson()
//        val item: Item = Item()
//        i.putExtra("item", gson.toJson(item))
//        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val fullNamePair = UtilPair.create(holder.binding.tvRepoRowFullName as View, "tFullName")
//        val descPair = UtilPair.create(holder.binding.tvRepoRowDesc as View, "tDesc")
//        val options = ActivityOptions.makeSceneTransitionAnimation(this@GithubMainActivity, fullNamePair, descPair)
//        startActivity(i, options.toBundle())
    }
}
