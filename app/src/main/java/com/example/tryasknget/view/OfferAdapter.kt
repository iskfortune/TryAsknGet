package com.example.tryasknget.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tryasknget.R
import com.example.tryasknget.room.Offer
import java.util.Locale

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    private var offers: List<Offer> = emptyList()

    fun submitList(newList: List<Offer>) {
        offers = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    override fun getItemCount(): Int = offers.size

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStore: TextView = itemView.findViewById(R.id.tvOfferStore)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvOfferPrice)
        private val tvDetails: TextView = itemView.findViewById(R.id.tvOfferDetails)

        fun bind(offer: Offer) {
            tvStore.text = offer.storeOrProviderName
            tvPrice.text = String.format(Locale.getDefault(), "KES %.2f", offer.price)
            tvDetails.text = offer.offerDescription
        }
    }
}