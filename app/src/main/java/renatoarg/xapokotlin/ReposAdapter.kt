package renatoarg.xapokotlin

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import renatoarg.xapokotlin.models.Item

class ReposAdapter(private val context: Context, private val items: List<Item>?) :
    RecyclerView.Adapter<ReposAdapter.ViewHolder>() {
    private val reposAdapterInterface: ReposAdapterInterface

    internal interface ReposAdapterInterface {
        fun onBindViewHolder(holder: ReposAdapter.ViewHolder, position: Int, items: List<Item>?)
    }

    init {
        reposAdapterInterface = context as ReposAdapterInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_repo, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ReposAdapter.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder()")
        reposAdapterInterface.onBindViewHolder(holder, position, items)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tv_full_name: TextView = itemView.findViewById(R.id.tv_full_name)
        internal var tv_desc: TextView = itemView.findViewById(R.id.tv_desc)
        internal var tv_stargazers_count: TextView = itemView.findViewById(R.id.tv_stargazers_count)
        internal var tv_language: TextView = itemView.findViewById(R.id.tv_language)
        internal var cl_wrapper: ConstraintLayout = itemView.findViewById(R.id.cl_wrapper)

    }

    companion object {
        private val TAG = "ActivatedOffersAdapter"
    }

}