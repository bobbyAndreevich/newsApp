package com.example.newstestovoe.ui.newsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newstestovoe.R
import kotlinx.android.synthetic.main.web_view_fragment.*

class WebViewFragment : Fragment() {

    companion object {
        const val URL = "url"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.web_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(URL)
        wev_view.settings.javaScriptEnabled = true
        wev_view.loadUrl(url!!)
    }
}