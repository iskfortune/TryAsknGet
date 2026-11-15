package com.example.tryasknget.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tryasknget.R
import com.example.tryasknget.room.Inquiry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InquiryAdapter(
    private val onItemClick: (Inquiry) -> Unit
) : RecyclerView.Adapter<InquiryAdapter.InquiryViewHolder>() {

    private var inquiries: List<Inquiry> = emptyList()

    fun submitList(newList: List<Inquiry>) {
        inquiries = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InquiryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inquiry, parent, false)
        return InquiryViewHolder(view)
    }

    override fun onBindViewHolder(holder: InquiryViewHolder, position: Int) {
        holder.bind(inquiries[position])
    }

    override fun getItemCount(): Int = inquiries.size

    inner class InquiryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvItemInquiryTitle)
        private val tvDesc: TextView = itemView.findViewById(R.id.tvItemInquiryDesc)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvItemInquiryStatus)
        private val tvDate: TextView = itemView.findViewById(R.id.tvItemInquiryDate)

        fun bind(inquiry: Inquiry) {
            tvTitle.text = inquiry.title
            tvDesc.text = inquiry.description
            tvStatus.text = inquiry.status

            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            tvDate.text = sdf.format(Date(inquiry.timestamp))

            itemView.setOnClickListener { onItemClick(inquiry) }
        }
    }
}