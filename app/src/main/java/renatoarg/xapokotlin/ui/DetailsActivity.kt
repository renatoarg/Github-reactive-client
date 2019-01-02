package renatoarg.xapokotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_details.*
import renatoarg.xapokotlin.R
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.ui.adapters.IssuesAdapter
import renatoarg.xapokotlin.ui.viewmodel.ItemDetailsViewModel
import renatoarg.xapokotlin.utils.AppUtils
import renatoarg.xapokotlin.utils.InjectorUtils

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // recovers the item from Intent
        val itemDetailsFactory = InjectorUtils.provideItemDetailsViewModelFactory()
        val itemDetailsViewModel = ViewModelProviders.of(this, itemDetailsFactory).get(ItemDetailsViewModel::class.java)

        // binds the views
        var mainBinding : renatoarg.xapokotlin.databinding.ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mainBinding.setLifecycleOwner(this@DetailsActivity)

        // sets the clicked item in viewModel
        val gson = Gson()
        itemDetailsViewModel.setItem(gson.fromJson(intent.getStringExtra("item"), Item::class.java))
        mainBinding.viewmodel = itemDetailsViewModel

        // observes the list of issues from the item
        itemDetailsViewModel.getIssues().observe(this, Observer {
            rv_issues.setHasFixedSize(true)
            rv_issues.layoutManager = LinearLayoutManager(this)
            rv_issues.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
            rv_issues.adapter = IssuesAdapter(itemDetailsViewModel.getIssues().value!!)
        })

        mainBinding.executePendingBindings()

        cardView.startAnimation(AppUtils.createFadeIn(300, 500, true))
        cardView2.startAnimation(AppUtils.createFadeIn(500, 700, true))
    }

    override fun onBackPressed() {
        rv_issues.visibility = GONE
        cardView.startAnimation(AppUtils.createFadeOut(200, 50, true))
        cardView2.startAnimation(AppUtils.createFadeOut(200, 50, true))
        super.onBackPressed()
    }
}
