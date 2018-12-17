package renatoarg.xapokotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import renatoarg.xapokotlin.models.Issue
import retrofit2.Response


/**
 * Created by renato.rezende on 28/05/2017.
 */

class IssuesAdapter(private val context: Context, private val issues: Response<List<Issue>>) :
    RecyclerView.Adapter<IssuesAdapter.ViewHolder>() {
    private val issuesAdapterInterface: IssuesAdapterInterface

    internal interface IssuesAdapterInterface {
        fun onBindViewHolder(holder: IssuesAdapter.ViewHolder, position: Int, issues: List<Issue>?)
    }

    init {
        issuesAdapterInterface = context as IssuesAdapterInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_issue, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: IssuesAdapter.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder()")
        issuesAdapterInterface.onBindViewHolder(holder, position, issues.body())
    }

    override fun getItemCount(): Int {
        return issues.body()!!.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var title: TextView
        internal var state: TextView

        init {
            title = itemView.findViewById(R.id.tv_issue)
            state = itemView.findViewById(R.id.tv_state)
        }
    }

    companion object {

        private val TAG = "IssuesAdapter"
    }

}