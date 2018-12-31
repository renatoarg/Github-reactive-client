package renatoarg.xapokotlin.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import renatoarg.xapokotlin.databinding.RowIssueBinding
import renatoarg.xapokotlin.ui.viewmodel.IssueViewModel


/**
 * Created by renato.rezende on 28/05/2017.
 */

class IssuesAdapter(var issues: List<IssueViewModel>) : RecyclerView.Adapter<IssuesAdapter.ViewHolder>(), AdapterContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowIssueBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(issues[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = issues!!.size

    inner class ViewHolder(val binding: RowIssueBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(issueViewModel: IssueViewModel) {
            binding.viewmodel = issueViewModel
            binding.executePendingBindings()
        }
    }

    override fun replaceItems(list: List<*>) {
        this.issues = issues
        notifyDataSetChanged()
    }

}