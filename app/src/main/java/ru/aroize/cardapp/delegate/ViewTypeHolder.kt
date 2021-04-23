package ru.aroize.cardapp.delegate

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ViewTypeHolder<T: ViewTypeItem>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: T)
}