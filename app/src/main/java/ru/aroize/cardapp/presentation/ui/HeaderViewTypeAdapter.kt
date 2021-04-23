package ru.aroize.cardapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.aroize.cardapp.R
import ru.aroize.cardapp.delegate.ViewTypeAdapterDelegate
import ru.aroize.cardapp.delegate.ViewTypeHolder
import ru.aroize.cardapp.delegate.ViewTypeItem


@Suppress("UNCHECKED_CAST")
class HeaderViewTypeAdapter : ViewTypeAdapterDelegate.ViewTypeAdapter {
    override fun createViewHolder(parent: ViewGroup): ViewTypeHolder<ViewTypeItem> {
        return HeaderViewHolder(parent) as ViewTypeHolder<ViewTypeItem>
    }

    override fun isForViewType(item: ViewTypeItem): Boolean {
        return item is HeaderItem
    }

    class HeaderViewHolder(parent: ViewGroup) : ViewTypeHolder<HeaderItem>(
        LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
    ) {
        override fun bind(model: HeaderItem) = Unit
    }

    class HeaderItem: ViewTypeItem
}