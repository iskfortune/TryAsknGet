package com.example.tryasknget.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tryasknget.R
import com.example.tryasknget.room.Recommendation

class RecommendationAdapter : RecyclerView.Adapter<RecommendationAdapter.RecViewHolder>() {

    private var recommendations: List<Recommendation> = emptyList()

    fun submitList(newList: List<Recommendation>) {
        recommendations = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendation, parent, false)
        return RecViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.bind(recommendations[position])
    }

    override fun getItemCount(): Int = recommendations.size

    class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPlace: TextView = itemView.findViewById(R.id.tvRecPlace)
        private val tvComment: TextView = itemView.findViewById(R.id.tvRecComment)

        fun bind(rec: Recommendation) {
            tvPlace.text = "Recommended: ${rec.recommendedPlaceOrBrand}"
            tvComment.text = rec.comment
        }
    }
}