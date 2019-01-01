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
import renatoarg.xapokotlin.utils.InjectorUtils

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // recovers the item from Intent
        val itemDetailsFactory = InjectorUtils.provideItemDetailsViewModelFactory()
        val itemDetailsViewModel = ViewModelProviders.of(this, itemDetailsFactory).get(ItemDetailsViewModel::class.java)

        var mainBinding : renatoarg.xapokotlin.databinding.ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mainBinding.setLifecycleOwner(this@DetailsActivity)

        val gson = Gson()
        itemDetailsViewModel.setItem(gson.fromJson(intent.getStringExtra("item"), Item::class.java))
        mainBinding.viewmodel = itemDetailsViewModel

        itemDetailsViewModel.getIssues().observe(this, Observer {
            rv_issues.setHasFixedSize(true)
            rv_issues.layoutManager = LinearLayoutManager(this)
            rv_issues.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
            rv_issues.adapter = IssuesAdapter(itemDetailsViewModel.getIssues().value!!)
        })

        mainBinding.executePendingBindings()

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
