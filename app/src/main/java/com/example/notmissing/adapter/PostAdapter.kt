package com.example.notmissing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notmissing.R
import com.example.notmissing.model.reportpostdata.ReportPostDataModel
import com.example.notmissing.model.reportpostdata.ReportPostDataModelItem

class PostAdapter(val data : ReportPostDataModel, val onClick : (data : ReportPostDataModelItem, position : Int)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleText : TextView = itemView.findViewById(R.id.titleText)
        val contentText : TextView = itemView.findViewById(R.id.contentText)
        val writerText : TextView = itemView.findViewById(R.id.writerText)
        val timeText : TextView = itemView.findViewById(R.id.timeText)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_post_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = data[position]
        if(holder is ViewHolder){
            holder.titleText.text = result.title
            holder.contentText.text = result.content
            holder.writerText.text = result.writer
            holder.timeText.text = result.created_at
        }
    }

    override fun getItemCount(): Int = data.size
}