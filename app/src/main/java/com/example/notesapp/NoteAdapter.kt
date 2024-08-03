package com.example.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.RecycleritemBinding

class NoteAdapter(private val notes: List<NoteItem>, private val itemClickListener: MainActivity) :RecyclerView.Adapter<NoteAdapter.noteViewHolder>(){

    interface OnItemClickListener {
        fun onDeleteClick(noteId : String)
        fun onUpdateClick(noteId : String , title : String, note : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
       val binding = RecycleritemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return noteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
       val note = notes[position]
        holder.bind(note)
        holder.binding.deletebtn.setOnClickListener{
            itemClickListener.onDeleteClick(note.noteId)
        }
        holder.binding.updatebtn.setOnClickListener{
            itemClickListener.onUpdateClick(note.noteId,note.title,note.note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class noteViewHolder(var binding: RecycleritemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {
            binding.titletv.text = note.title
            binding.nottv.text = note.note
        }

    }
}