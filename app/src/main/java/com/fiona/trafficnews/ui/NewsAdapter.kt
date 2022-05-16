package com.fiona.trafficnews.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fiona.trafficnews.R
import com.fiona.trafficnews.data.NewsDataModel


class NewsAdapter(
    private val context: Activity,
    private var newsList: NewsDataModel,
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {

        return NewsViewHolder(context.layoutInflater.inflate(R.layout.item_news, parent, false))

    }

    override fun getItemCount(): Int {
        return newsList.News.size
    }

    fun updateList(newsList: NewsDataModel) {
        this.newsList = newsList
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.tvTitle.text = newsList.News.get(position).chtmessage
        holder.tvContent.text = newsList.News.get(position).content
        holder.tvTime.text = "起始:"+newsList.News.get(position).starttime+"\n結束:"+newsList.News.get(position).endtime

    }


    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvContent: TextView = view.findViewById(R.id.tv_content)
        val tvTime: TextView = view.findViewById(R.id.tv_time)

    }

}


