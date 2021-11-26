package com.example.notmissing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notmissing.R
import com.example.notmissing.model.missingpeople.People
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Base64
import java.lang.Exception


class MissingPeopleAdapter(val data : ArrayList<People>, val onClick : (position : Int, data : People) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val peopleImage : ImageView = itemView.findViewById(R.id.peopleImage)
        val peopleAge : TextView = itemView.findViewById(R.id.peopleAge)
        val peopleNowAge : TextView = itemView.findViewById(R.id.peopleNowAge)
        val occurSpot : TextView = itemView.findViewById(R.id.occurSpot)
        val peopleName : TextView = itemView.findViewById(R.id.peopleName)
    }

    inner class LodingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }


    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            val result = data[position]
            holder.peopleImage.setImageBitmap(StringToBitMap(result.tknphotoFile))
            holder.peopleAge.text = "당시 나이 : ${result.age}세"
            holder.peopleNowAge.text = "현재 나이 : ${result.ageNow}세"
            holder.occurSpot.text = "발생장소\n${result.occrAdres}"
            holder.peopleName.text = result.nm
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_missing_people_item, parent, false)
                val holder = ViewHolder(v)
                v.setOnClickListener {
                    onClick(holder.adapterPosition, data[holder.adapterPosition])
                }
                holder
            }
            else -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_loding_item, parent, false)
                LodingViewHolder(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position]!!.ageNow){
            " "->{
                VIEW_TYPE_LOADING
            }
            else->{
                VIEW_TYPE_ITEM
            }
        }

    }
}