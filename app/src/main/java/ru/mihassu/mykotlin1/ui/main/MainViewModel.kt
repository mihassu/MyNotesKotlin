package ru.mihassu.mykotlin1.ui.main

import android.util.Log
import androidx.lifecycle.Observer
import ru.mihassu.mykotlin1.data.NotesRepository
import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.data.model.NoteResult
import ru.mihassu.mykotlin1.ui.base.BaseViewModel


class MainViewModel(private val repository: NotesRepository = NotesRepository) :
    BaseViewModel<List<MyNote>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: let { return@Observer }

        when(result) {
            is NoteResult.Success<*> ->
                viewStateLiveData.value = MainViewState(result.data as? List<MyNote>)

            is NoteResult.Error ->
                viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)

//        viewStateLiveData.value = MainViewState(NotesRepository.notes)
//        repository.getNotesLiveData().observeForever { notes ->
//            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = notes!!) ?:
//                    MainViewState(notes!!)
//        }
    }

//    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()
    //геттер
//    fun viewStateLiveData(): LiveData<MainViewState> = viewStateLiveData

    override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
//        repository.getNotesLiveData().removeObservers()
    }
}