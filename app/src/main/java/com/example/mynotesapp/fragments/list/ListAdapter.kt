package com.example.mynotesapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.R
import com.example.mynotesapp.model.Note
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var noteList = emptyList<Note>()
    var onItemClick: ((item: Note) -> Unit)? = null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val idTextView: TextView = itemView.id_txt
        val titleTextView: TextView = itemView.Title_txt
        val subjectTextView: TextView = itemView.Subject_txt
        val noteImageView: ImageView = itemView.noteItemImageView

        fun bindView(currentItem: Note) {

            idTextView.text = "Id: ${currentItem.id}"
            titleTextView.text = currentItem.title
            subjectTextView.text = currentItem.subject
            Picasso.get().load(currentItem.imagePath.toUri()).fit().into(noteImageView)

            itemView.row_layout.setOnClickListener {
                onItemClick?.invoke(currentItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val contentView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.custom_row,
                parent,
                false
            )

        return MyViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.bindView(currentItem)
    }

    fun setData(notes: List<Note>) {
        this.noteList = notes
        notifyDataSetChanged()
    }
}