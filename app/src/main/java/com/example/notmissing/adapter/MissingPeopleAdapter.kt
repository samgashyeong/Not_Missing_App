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


class MissingPeopleAdapter(val data : ArrayList<People>) : RecyclerView.Adapter<MissingPeopleAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val peopleImage : ImageView = itemView.findViewById(R.id.peopleImage)
        val peopleAge : TextView = itemView.findViewById(R.id.peopleAge)
        val peopleNowAge : TextView = itemView.findViewById(R.id.peopleNowAge)
        val occurSpot : TextView = itemView.findViewById(R.id.occurSpot)
        val peopleName : TextView = itemView.findViewById(R.id.peopleName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_missing_people_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]
        holder.peopleImage.setImageBitmap(StringToBitMap(result.tknphotoFile))
        holder.peopleAge.text = result.age.toString()
        holder.peopleNowAge.text = result.ageNow
        holder.occurSpot.text = result.occrAdres
        holder.peopleName.text = result.nm
    }

    override fun getItemCount(): Int {
        return data.size
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
}