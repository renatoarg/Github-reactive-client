package renatoarg.xapokotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import renatoarg.xapokotlin.data.models.Item
import renatoarg.xapokotlin.databinding.RowRepoBinding
import renatoarg.xapokotlin.ui.GithubMainActivity

class ItemsAdapter(private val context: Context, private var items: List<Item>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>(),
    AdapterContract {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        setOnItemClickListener(listener)
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowRepoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.executePendingBindings()
        holder.binding.clRepoRowWrapper.setOnClickListener {
            listener.onClick(it, items[position])
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    inner class ViewHolder(val binding: RowRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.viewmodel = item
            binding.executePendingBindings()
        }
    }

    override fun replaceItems(list: List<*>) {
        this.items = list as List<Item>
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: Item)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}