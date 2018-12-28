package renatoarg.xapokotlin.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_repo.view.*
import renatoarg.xapokotlin.R
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.utils.AppUtils

class ItemsAdapter(private val context: Context, private val items: List<Item>?) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private val TAG: String = "ActivatedOffersAdapter"
    private val reposAdapterInterface: ReposAdapterInterface

    internal interface ReposAdapterInterface {
        fun onClick(item: Item, holder: ViewHolder)
    }

    init {
        reposAdapterInterface = context as ReposAdapterInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_repo, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder()")
        holder.tv_full_name.text = items!![position].full_name
        holder.tv_desc.text = items[position].description
        holder.tv_stargazers_count.text = AppUtils.formatStargazers(items[position].stargazers_count)
        holder.tv_language.text = "Language: " + items[position].language
        holder.cl_wrapper.setOnClickListener {
            reposAdapterInterface.onClick(items[position], holder)
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_full_name = itemView.tv_repo_row_full_name
        val tv_desc = itemView.tv_repo_row_desc
        val tv_stargazers_count = itemView.tv_repo_row_stargazers_count
        val tv_language = itemView.tv_repo_row_language
        val cl_wrapper = itemView.cl_repo_row_wrapper
    }

}