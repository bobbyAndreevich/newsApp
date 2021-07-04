package com.example.newstestovoe.ui.newsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entities.News
import com.example.newstestovoe.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_filter_redactor.view.*
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val viewModel : NewsViewModel,
                  private val onItemClick : ((News) -> Unit),
                  private val context: Context?) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    private var news = viewModel.getNews() ?: emptyList()

    inner class NewsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(news: News) {
            containerView.author_text.text = news.author
            containerView.title_text.text = news.title
            containerView.news_description_text.text = news.description
            containerView.published_text.text = news.publishedAt
            if (news.urlToImage == null)
                containerView.image_news.visibility = View.GONE
            else{
                containerView.image_news.visibility = View.VISIBLE
                Glide.with(context!!).load(news.urlToImage).centerCrop()
                    .into(containerView.image_news)
            }

        }

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(news[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  NewsViewHolder(inflater.inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int = news.size


    fun refreshNews(newsList: List<News>) {
        news = newsList
        notifyDataSetChanged()
    }
}