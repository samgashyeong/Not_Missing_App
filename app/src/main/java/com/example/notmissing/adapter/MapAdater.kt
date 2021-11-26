package com.example.notmissing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notmissing.R
import com.example.notmissing.model.safespot.Spot

class MapAdater(val data : ArrayList<Spot>, val onClick : (position : Int, data : Spot) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.nameText)
        val teleText : TextView = itemView.findViewById(R.id.teleText)
        val addressText : TextView = itemView.findViewById(R.id.addressText)
        val kindOfMarkerText : TextView = itemView.findViewById(R.id.kindOfMarketText)
    }
    class LodingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_ITEM->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_map_item, parent, false)
                val holder = ViewHolder(v)
                v.setOnClickListener {
                    onClick(holder.adapterPosition, data[holder.adapterPosition])
                }
                holder
            }
            else->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_loding_item, parent, false)
                LodingViewHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            val result = data[position]
            holder.name.text = result.bsshNm
            holder.addressText.text = result.adres
            holder.teleText.text = result.telno
            holder.kindOfMarkerText.text = result.clNm
        }
    }

    override fun getItemCount(): Int = data.size


    override fun getItemViewType(position: Int): Int {
        return when(data[position].bsshNm){
            " "->{
                VIEW_TYPE_LOADING
            }
            else->{
                VIEW_TYPE_ITEM
            }
        }
    }
}