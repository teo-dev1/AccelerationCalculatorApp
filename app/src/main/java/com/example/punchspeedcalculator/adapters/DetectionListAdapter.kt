package com.example.punchspeedcalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.punchspeedcalculator.R

import com.example.punchspeedcalculator.models.Detection
import kotlinx.android.synthetic.main.list_item_template.view.*
import java.text.SimpleDateFormat
import java.util.*

class DetectionListAdapter(
     var detections: List<Detection>
) : RecyclerView.Adapter<DetectionListAdapter.DetectionViewHolder>() {

    inner class DetectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_template, parent, false)
        return DetectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetectionViewHolder, position: Int) {
        holder.itemView.tv_accelerationValue.text="${detections[position].peakValue.toString()}          m/s²"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(detections[position].date)
        holder.itemView.tv_DateRecorded.text=dateFormat.format(date)

    }

    override fun getItemCount() = detections.size

}
