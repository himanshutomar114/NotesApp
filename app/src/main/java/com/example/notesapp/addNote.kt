package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        databaseReference = FirebaseDatabase.getInstance().reference
        setContentView(binding.root)
        binding.savebtn.setOnClickListener{
            val title = binding.ettitle.text.toString()
            val note =binding.etnote.text.toString()
            val noteKey = databaseReference.child("User").child("Notes").push().key
            val noteItem = NoteItem(title, note,noteKey ?: "")
            if (noteKey!=null){
                databaseReference.child("User").child("Notes").child(noteKey).setValue(noteItem)
                    .addOnCompleteListener{ task->
                        if (task.isSuccessful)
                            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                finish()

            }
                
        }
        binding.backbtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}