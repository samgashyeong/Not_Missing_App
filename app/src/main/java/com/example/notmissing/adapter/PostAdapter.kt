package com.example.notmissing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notmissing.R
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem

class PostAdapter(val data : ArrayList<ReportPostDataModelItem>, val onClick : (data : ReportPostDataModelItem, position : Int)->Unit) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleText : TextView = itemView.findViewById(R.id.titleText)
        val contentText : TextView = itemView.findViewById(R.id.contentText)
        val writerText : TextView = itemView.findViewById(R.id.writerText)
        val timeText : TextView = itemView.findViewById(R.id.timeText)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_post_item, parent, false)

        val holder = ViewHolder(v)
        v.setOnClickListener {
            onClick(data[holder.adapterPosition], holder.adapterPosition)
        }
        return holder
    }


    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]
        holder.titleText.text = result.title
        holder.contentText.text = result.content
        holder.writerText.text = result.writer
        holder.timeText.text = result.created_at
    }
}