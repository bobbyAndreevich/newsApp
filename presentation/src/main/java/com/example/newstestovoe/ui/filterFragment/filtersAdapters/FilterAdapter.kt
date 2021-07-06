package com.example.newstestovoe.ui.filterFragment.filtersAdapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.Filter
import com.example.newstestovoe.ui.filterFragment.FiltersViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newstestovoe.R
import com.example.newstestovoe.ui.filterFragment.filtersAdapters.FilterAdapter.*

import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.filters_item.view.*

class FilterAdapter(
    private val viewModel : FiltersViewModel,
    private val onItemClick : ((Filter) -> Unit),
    private val context: Context?
) : RecyclerView.Adapter<FilterViewHolder>(), ITouchHelperAdapter {

    private var filters = viewModel.getFilters() ?: emptyList()

    inner class FilterViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(filter: Filter) {
            containerView.filter_name.text = filter.name
        }

        init {
            itemView.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                else
                    onItemClick.invoke(filters[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  FilterViewHolder(inflater.inflate(R.layout.filters_item, parent, false))
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(filters[position])
    }

    override fun getItemCount(): Int = filters.size

    override fun deleteItem(position: Int) {
        viewModel.filterDeleted(filters[position])
        notifyItemRemoved(position)
    }

    fun refreshFilters(filtersList: List<Filter>) {
        filters = filtersList
        notifyDataSetChanged()
    }
}