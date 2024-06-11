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
import com.example.projectakhirbudaya.room.BudayaSavedEntity
import com.example.projectakhirbudaya.viewmodel.BudayaSavedViewModel
import com.example.projectakhirbudaya.viewmodel.BudayaViewModel

import com.google.android.material.imageview.ShapeableImageView

class BudayaAdapterForSaved(private var dataList: List<BudayaSavedEntity>, private val budayaViewModel: BudayaSavedViewModel) :
    RecyclerView.Adapter<BudayaAdapterForSaved.BudayaSavedViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onDeleteClicked(data: BudayaSavedEntity, position: Int)
    }

    class BudayaSavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataTitle: TextView = itemView.findViewById(R.id.txt_title_saved)
        val dataLokasi: TextView = itemView.findViewById(R.id.txt_lokasi_saved)
        val dataDesc: TextView = itemView.findViewById(R.id.txt_desc_saved)
        val dataImg: ShapeableImageView = itemView.findViewById(R.id.img_budaya_saved)

        //btn
        val btnDelete: ImageView = itemView.findViewById(R.id.btn_delete_saved)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BudayaSavedViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_budaya_saved, parent, false)
        return BudayaSavedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudayaSavedViewHolder, position: Int) {
        val data = dataList[position]

        holder.dataTitle.text = data.name
        holder.dataLokasi.text = "lokasi : ${data.location}"
        holder.dataDesc.text = data.description.shorten(30)

        val uri = Uri.fromFile(data.image)
        holder.dataImg.setImageURI(uri)

        holder.btnDelete.setOnClickListener {
            onItemClickCallback.onDeleteClicked(dataList[holder.absoluteAdapterPosition], holder.absoluteAdapterPosition)
        }

 }

    override fun getItemCount(): Int = dataList.size

    private fun String.shorten(maxLength: Int): String {
        return if (this.length <= maxLength) this else "${this.substring(0, maxLength)}..."
    }

}
