package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.databinding.ActivityUpdatepageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity(),NoteAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        databaseReference = FirebaseDatabase.getInstance().reference
        recyclerView = binding.recylerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)

        binding.addbtnpage.setOnClickListener{
            startActivity(Intent(this,addNote::class.java))
        }
        binding.settinbtn.setOnClickListener{
            startActivity(Intent(this,setting::class.java))
        }
        val notereference = databaseReference.child("User").child("Notes")
        notereference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val notelist = mutableListOf<NoteItem>()
                for (notesnapshot in snapshot.children){
                    val note = notesnapshot.getValue(NoteItem::class.java)
                    note?. let {
                        notelist.add(it)
                    }
                }
                notelist.reverse()
                val adapter = NoteAdapter(notelist,this@MainActivity)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        }

    override fun onDeleteClick(noteId: String) {
        val notereference = databaseReference.child("User").child("Notes")
        notereference.child(noteId).removeValue()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onUpdateClick(noteId: String, title: String, note: String) {
    val dialogbinding = ActivityUpdatepageBinding.inflate(LayoutInflater.from(this))
    val dialog = AlertDialog.Builder(this).setView(dialogbinding.root)
        .setTitle("Update Note")
        .setPositiveButton("Update"){dialog, _->
            val newtitle = dialogbinding.ettitle.text.toString()
            val newnote = dialogbinding.etnote.text.toString()
            updateNoteDatabase(noteId,newtitle,newnote)
            dialog.dismiss()
        }
        .setNegativeButton("Cancel"){dialog,_->
            dialog.dismiss()
        }
        .create()
        dialogbinding.etnote.setText(note)
        dialogbinding.ettitle.setText(title)
        dialog.show()

    }
    private fun updateNoteDatabase(noteId: String,newtitle :String,newnote :String){
        val notereference = databaseReference.child("User").child("Notes")
        val updateNote = NoteItem(newtitle,newnote,noteId)
        notereference.child(noteId).setValue(updateNote)
    }
}


