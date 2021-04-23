package ru.aroize.cardapp.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewTypeAdapterDelegate : RecyclerView.Adapter<ViewTypeHolder<ViewTypeItem>>() {

    interface ViewTypeAdapter {
        fun isForViewType(item: ViewTypeItem): Boolean
        fun createViewHolder(parent: ViewGroup): ViewTypeHolder<ViewTypeItem>
    }

    private val adapters = arrayListOf<ViewTypeAdapter>()
    private val items = arrayListOf<ViewTypeItem>()

    fun addAdapter(adapter: ViewTypeAdapter) {
        adapters += adapter
    }

    fun addItem(item: ViewTypeItem) {
        items += item
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewTypeHolder<ViewTypeItem>, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewTypeHolder<ViewTypeItem> {
        val adapter = adapters[viewType]
        return adapter.createViewHolder(parent)
    }

    override fun getItemViewType(position: Int) = findViewType(position)

    private fun findViewType(position: Int): Int {
        val item = items[position]
        adapters.forEachIndexed { index, viewTypeAdapter ->
            if (viewTypeAdapter.isForViewType(item))
                return index
        }
        throw IllegalArgumentException("No delegate for this item")
    }
}