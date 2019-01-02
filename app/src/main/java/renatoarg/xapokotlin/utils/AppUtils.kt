package renatoarg.xapokotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import renatoarg.xapokotlin.ui.adapters.IssuesAdapter
import renatoarg.xapokotlin.ui.adapters.ItemsAdapter
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    @JvmStatic
    fun formatUpdatedAt(date: Date) : String {
        val format = SimpleDateFormat("yyyy, MMM dd")
        return "last update: " + format.format(date)
    }

    @JvmStatic
    fun formatForks(count: Int) : String {
        return "forks: $count"
    }

    @JvmStatic
    fun formatStargazers(count: Int) : String {
        return String.format("%.1fk", count.toFloat()  / 1000)
    }

    @JvmStatic
    fun formatLanguage(language: String?) : String {
        language?.let { return "language: $language" }
        return "language not defined"
    }

    @JvmStatic
    fun formatState(state: String?) : String {
        state?.let { return "state: $state" }
        return "state not available"
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url:String) {
        Picasso.get().load(url).into(this)
    }

    @JvmStatic
    fun isNetworkAvailable(activity: AppCompatActivity):Boolean{
        val connectivityManager= activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo= connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    @BindingAdapter("issues")
    @JvmStatic
    fun setIssues(recyclerView: RecyclerView, list: List<Any>) {
        recyclerView.adapter.let {
            if (it is IssuesAdapter) {
                it.replaceItems(list)
            }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, list: List<Any>) {
        recyclerView.adapter.let {
            if (it is ItemsAdapter) {
                it.replaceItems(list)
            }
        }
    }

    @JvmStatic
    fun createFadeIn(duration: Long, startOffset: Long, fillAfter: Boolean): Animation {
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = duration
        animation.startOffset = startOffset
        animation.fillAfter = fillAfter
        return  animation
    }

    @JvmStatic
    fun createFadeOut(duration: Long, startOffset: Long, fillAfter: Boolean): Animation {
        val animation = AlphaAnimation(1.0f, 0.0f)
        animation.duration = duration
        animation.startOffset = startOffset
        animation.fillAfter = fillAfter
        return  animation
    }
}