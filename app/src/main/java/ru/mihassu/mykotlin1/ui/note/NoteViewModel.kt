package ru.mihassu.mykotlin1.ui.note

import androidx.lifecycle.Observer
import ru.mihassu.mykotlin1.data.NotesRepository
import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.data.model.NoteResult
import ru.mihassu.mykotlin1.ui.base.BaseViewModel

class NoteViewModel(private val repository: NotesRepository = NotesRepository) :
    BaseViewModel<MyNote?, NoteViewState>() {

    private var tempNote: MyNote? = null

    fun saveChanges(note: MyNote) {
        tempNote = note
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId)
            .observeForever { Observer<NoteResult> {
                if (it == null) return@Observer

                when(it) {
                    is NoteResult.Success<*> ->
                        viewStateLiveData.value = NoteViewState(note = it.data as? MyNote)

                    is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
                }
            } }
    }

    override fun onCleared() {
        if (tempNote != null) {
            repository.saveNote(tempNote!!)
        }
        super.onCleared()
    }
}