package com.example.newstestovoe.ui.redactorFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.entities.Filter
import com.example.newstestovoe.App
import com.example.newstestovoe.R
import com.example.newstestovoe.databinding.FragmentFilterRedactorBinding
import kotlinx.android.synthetic.main.fragment_filter_redactor.*
import javax.inject.Inject

class FilterRedactorFragment : Fragment() {

  companion object {
    const val FILTER_KEY = "filter"
    const val ADD_FILTER_KEY = 3
    const val CHANGE_FILTER_KEY = 2
    const val REQUEST_CODE = "requestCode"
  }

  @Inject
  lateinit var viewModel: FilterRedactorViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    (requireActivity().application as App).createViewModelRedactorComponent(this)
    (requireActivity().application as App).redactorViewModelComponent.injectRedactorFragment(this)
    val binding = DataBindingUtil.inflate<FragmentFilterRedactorBinding>(inflater,
      R.layout.fragment_filter_redactor, container, false)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    when (arguments?.getInt(REQUEST_CODE)){
      ADD_FILTER_KEY -> save_fab.setOnClickListener {
        if (viewModel.validation()){
          viewModel.saveNewFilter()
          findNavController().navigate(R.id.action_filterRedactorFragment2_to_nav_filters)
        }
      }
      CHANGE_FILTER_KEY -> {
        changeTitle()
        val filter = requireArguments().getSerializable(FILTER_KEY)
        save_fab.setOnClickListener {
          if (viewModel.validation()){
            viewModel.saveChangedFilter(filter as Filter)
            findNavController().navigate(R.id.action_filterRedactorFragment2_to_nav_filters)
          }
        }
        viewModel.updateFilterData(filter as Filter)
      }
    }

    sendToViewModel()
  }

  private fun sendToViewModel(){
    edit_filter_name.doOnTextChanged { text, _, _, _ ->
      viewModel.name.value = text.toString()
    }
    edit_text_description.doOnTextChanged{ text, _, _, _ ->
      viewModel.description.value = text.toString()
    }
  }

  private fun changeTitle(){
    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.change_filter_title)
  }


}