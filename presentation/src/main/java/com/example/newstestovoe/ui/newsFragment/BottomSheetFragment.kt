package com.example.newstestovoe.ui.newsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entities.Filter
import com.example.newstestovoe.R
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*


class BottomSheetFragment() : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private var filtersList = mutableListOf<String>("Все")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireParentFragment()).get(NewsViewModel::class.java)
        observeViewModel()
        addFiltersNews()
    }


    private fun observeViewModel(){
        viewModel.filtersData.observe(viewLifecycleOwner, Observer {
                it.forEach{filter: Filter -> filtersList.add(filter.name) }
        })
    }


    private fun addFiltersNews() {
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_item, filtersList
        )
        filters_spinner.adapter = spinnerArrayAdapter
        filters_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
                viewModel.filter.filter(filtersList[position])
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }
}