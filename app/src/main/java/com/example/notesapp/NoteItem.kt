package com.example.notesapp

data class NoteItem(val title :String , val note :String , val noteId :String){
    constructor():this("","","")
}
