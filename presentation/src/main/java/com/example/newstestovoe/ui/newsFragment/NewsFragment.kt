package com.example.newstestovoe.ui.newsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.News
import com.example.newstestovoe.App
import com.example.newstestovoe.R
import com.example.newstestovoe.databinding.FragmentFilterRedactorBinding
import com.example.newstestovoe.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.fragment_filters.*
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject


class NewsFragment : Fragment() {

  @Inject
  lateinit var viewModel: NewsViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    (requireActivity().application as App).createViewModelNewsComponent(this)
    (requireActivity().application as App).newsViewModelComponent.injectNewsFragment(this)
    val binding = DataBindingUtil.inflate<FragmentNewsBinding>(inflater,
      R.layout.fragment_news, container, false)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    addAdapter()
    addBottomSheet()
    observeViewModel()
  }

  private fun observeViewModel() {
    viewModel.newsData.observe(viewLifecycleOwner, Observer {
      it.let {
        (news_recycler.adapter as NewsAdapter).refreshNews(it)
      }
    })
  }

  private fun addBottomSheet(){
    val bottomSheet = BottomSheetFragment()
    childFragmentManager.beginTransaction()
      .replace(R.id.bottom_sheet_cont, bottomSheet)
      .commit()
  }

  private fun addAdapter() {
      news_recycler.apply {
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.stackFromEnd = true
        layoutManager = mLayoutManager
        adapter = NewsAdapter(viewModel, {
            news -> startWebView(news)
        }, this@NewsFragment.context)
        adapter!!.notifyDataSetChanged()
      }
  }

  private fun startWebView(news: News) {
      val bundle = Bundle()
      bundle.putString(WebViewFragment.URL, news.url)
      findNavController().navigate(R.id.action_nav_news_to_webViewFragment, bundle)
  }

}