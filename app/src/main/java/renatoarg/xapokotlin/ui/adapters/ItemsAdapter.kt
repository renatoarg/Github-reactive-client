package renatoarg.xapokotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import renatoarg.xapokotlin.databinding.RowRepoBinding
import renatoarg.xapokotlin.ui.GithubMainActivity
import renatoarg.xapokotlin.ui.viewmodel.ItemViewModel

class ItemsAdapter(private val context: Context, private var items: List<ItemViewModel>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>(),
    AdapterContract {

    private lateinit var itemsAdapterContract : ItemsAdapterContract

    interface ItemsAdapterContract {
        fun onClick(item: ItemViewModel, holder: ViewHolder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowRepoBinding.inflate(inflater, parent, false)
        itemsAdapterContract = context as GithubMainActivity
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.executePendingBindings()
        itemsAdapterContract.onClick(items[position], holder)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    inner class ViewHolder(val binding: RowRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemViewModel: ItemViewModel) {
            binding.viewmodel = itemViewModel
            binding.executePendingBindings()
        }
    }

    override fun replaceItems(list: List<*>) {
        this.items = list as List<ItemViewModel>
        notifyDataSetChanged()
    }
}