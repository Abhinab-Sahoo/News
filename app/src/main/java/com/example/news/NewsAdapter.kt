package com.example.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(private val newsArrayList: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tVImage: ImageView = itemView.findViewById(R.id.newsImage)
        private val tVTitle: TextView = itemView.findViewById(R.id.newsTitle)
        private val tVAuthor: TextView = itemView.findViewById(R.id.newsAuthor)
        private val shareButton: ImageView = itemView.findViewById(R.id.shareButton)

        fun bind(article: Article) {
            Picasso.get().load(article.urlToImage).into(tVImage)
            tVTitle.text = article.title
            tVAuthor.text = article.author

            // Set click listener for opening the URL
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                itemView.context.startActivity(intent)
            }

            // Set click listener for sharing the URL
            shareButton.setOnClickListener {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_TEXT, article.url)
                itemView.context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newsArrayList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsArrayList[position]
        holder.bind(currentItem)
    }
}
