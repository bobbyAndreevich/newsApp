package com.example.newstestovoe.ui.newsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entities.News
import com.example.newstestovoe.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_filter_redactor.view.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val viewModel : NewsViewModel,
                  private val onItemClick : ((News) -> Unit),
                  private val context: Context?) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    private var newsList = viewModel.getNews() ?: emptyList()

    inner class NewsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(news: News, position: Int) {
            containerView.author_text.text = news.author
            containerView.title_text.text = news.title
            containerView.news_description_text.text = news.description
            containerView.published_text.text = news.publishedAt!!
                .replace('T', ' ').replace('Z', ' ')
            if (news.urlToImage == null)
                containerView.image_news.visibility = View.GONE
            else{
                containerView.image_news.visibility = View.VISIBLE
                Glide.with(context!!).load(news.urlToImage).centerCrop()
                    .into(containerView.image_news)
            }

            if (position == 0 || news.stringDate != newsList[position - 1].stringDate)
            {
                containerView.main_date_text.visibility = View.VISIBLE
                containerView.main_date_text.text = news.stringDate
            }
            else
                containerView.main_date_text.visibility = View.GONE

        }

        init {
            itemView.news_content.setOnClickListener {
                onItemClick.invoke(newsList[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return  NewsViewHolder(inflater.inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position], position)
    }

    override fun getItemCount(): Int = newsList.size


    fun refreshNews(newsList: List<News>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }
}