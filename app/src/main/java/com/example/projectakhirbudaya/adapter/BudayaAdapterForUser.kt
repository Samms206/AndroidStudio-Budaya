package com.example.projectakhirbudaya.adapter

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectakhirbudaya.R
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel

import com.google.android.material.imageview.ShapeableImageView

class BudayaAdapterForUser(private var dataList: List<BudayaEntity>, private val budayaViewModel: BudayaViewModel) :
    RecyclerView.Adapter<BudayaAdapterForUser.BudayaViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onMoreClicked(data: BudayaEntity, position: Int)
    }

    class BudayaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataTitle: TextView = itemView.findViewById(R.id.txt_title)
        val dataDesc: TextView = itemView.findViewById(R.id.txt_desc)
        val dataImg: ShapeableImageView = itemView.findViewById(R.id.img_budaya)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BudayaViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_budaya, parent, false)
        return BudayaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudayaViewHolder, position: Int) {
        val data = dataList[position]

        holder.dataTitle.text = data.name
        holder.dataDesc.text = data.description.shorten(500)

        val uri = Uri.fromFile(data.image)
        holder.dataImg.setImageURI(uri)

 }

    override fun getItemCount(): Int = dataList.size

    private fun String.shorten(maxLength: Int): String {
        return if (this.length <= maxLength) this else "${this.substring(0, maxLength)}..."
    }

}
