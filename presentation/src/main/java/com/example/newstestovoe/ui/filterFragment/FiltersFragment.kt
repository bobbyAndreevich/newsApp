package com.example.newstestovoe.ui.filterFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.Filter
import com.example.newstestovoe.App
import com.example.newstestovoe.R
import com.example.newstestovoe.ui.filterFragment.filtersAdapters.FilterAdapter
import com.example.newstestovoe.ui.filterFragment.filtersAdapters.MyItemTouchHelper
import com.example.newstestovoe.ui.redactorFragment.FilterRedactorFragment
import kotlinx.android.synthetic.main.fragment_filter_redactor.*
import kotlinx.android.synthetic.main.fragment_filters.*
import javax.inject.Inject

class FiltersFragment : Fragment() {


  @Inject
  lateinit var viewModel: FiltersViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    (requireActivity().application as App).createViewModelFiltersComponent(this)
    (requireActivity().application as App).filtersViewModelComponent.injectFiltersFragment(this)
    return inflater.inflate(R.layout.fragment_filters, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    add_fab.setOnClickListener {
      createFilter()
    }
    addAdapter()
    observeViewModels()
  }

  private fun createFilter() {
    val bundle = Bundle()
    bundle.putInt(FilterRedactorFragment.REQUEST_CODE, FilterRedactorFragment.ADD_FILTER_KEY)
    findNavController().navigate(R.id.action_nav_filters_to_filterRedactorFragment2, bundle)
  }

  private fun changeFilter(filter: Filter) {
    val bundle = Bundle()
    bundle.putInt(FilterRedactorFragment.REQUEST_CODE, FilterRedactorFragment.CHANGE_FILTER_KEY)
    bundle.putSerializable(FilterRedactorFragment.FILTER_KEY, filter)
    findNavController().navigate(R.id.action_nav_filters_to_filterRedactorFragment2, bundle)
  }

  private fun addAdapter() {
    filters_recycler.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = FilterAdapter(viewModel, {
          filter -> changeFilter(filter) }, this@FiltersFragment.context)
    }
    filters_recycler.adapter!!.notifyDataSetChanged()
    val habitAdapter = filters_recycler.adapter as FilterAdapter
    val callback: ItemTouchHelper.Callback = MyItemTouchHelper(habitAdapter)
    val myItemTouchHelper = ItemTouchHelper(callback)
    myItemTouchHelper.attachToRecyclerView(filters_recycler)
  }

  private fun observeViewModels() {
    viewModel.filtersData.observe(viewLifecycleOwner, Observer {
      it.let {
        (filters_recycler.adapter as FilterAdapter).refreshFilters(it)
      }
    })
  }

}