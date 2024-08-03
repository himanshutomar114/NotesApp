package com.example.notesapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ActivityUpdatepageBinding
import com.example.notesapp.databinding.RecycleritemBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Updatepage : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatepageBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdatepageBinding.inflate(layoutInflater)
        databaseReference = FirebaseDatabase.getInstance().reference
        setContentView(binding.root)


    }
}