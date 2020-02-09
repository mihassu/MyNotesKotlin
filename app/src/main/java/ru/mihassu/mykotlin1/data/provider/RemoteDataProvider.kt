package ru.mihassu.mykotlin1.data.provider

import androidx.lifecycle.LiveData
import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.data.model.NoteResult

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: MyNote): LiveData<NoteResult>
}