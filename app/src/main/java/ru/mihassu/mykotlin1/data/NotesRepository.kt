package ru.mihassu.mykotlin1.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.mihassu.mykotlin1.data.db.FireStoreDataProvider
import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.data.provider.RemoteDataProvider
import java.util.*

object NotesRepository {

    private val dbProvider : RemoteDataProvider = FireStoreDataProvider()

    fun getNotes() = dbProvider.subscribeToAllNotes()
    fun saveNote(note: MyNote) = dbProvider.saveNote(note)
    fun getNoteById(id: String) = dbProvider.getNoteById(id)

//    private val notesLiveData = MutableLiveData<List<MyNote>>()

    val notes: MutableList<MyNote> = mutableListOf(

        MyNote(
            id = UUID.randomUUID().toString(),
            title = "Заметка 1",
            note = "Текст заметки",
            color = MyNote.Color.BLUE
        ),

        MyNote(
            id = UUID.randomUUID().toString(),
            title = "Заметка 2",
            note = "Текст заметки",
            color = MyNote.Color.GREEN
        ),

        MyNote(
            id = UUID.randomUUID().toString(),
            title = "Заметка 3",
            note = "Текст заметки",
            color = MyNote.Color.YELLOW
        ),

        MyNote(
            id = UUID.randomUUID().toString(),
            title = "Заметка 4",
            note = "Текст заметки",
            color = MyNote.Color.VIOLET
        ),

        MyNote(
            id = UUID.randomUUID().toString(),
            title = "Заметка 5",
            note = "Текст заметки",
            color = MyNote.Color.RED
        )
    )

//    init {
//        notesLiveData.value = notes
//    }

//    fun getNotesLiveData(): LiveData<List<MyNote>> {
//        return notesLiveData
//    }

//    fun saveNote(note: MyNote) {
//        addOrReplace(note)
//        notesLiveData.value = notes
//    }

    private fun addOrReplace(note: MyNote) {

        for (i in 0 until notes.size) {

            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }


//    //private
//    val notes: List<MyNote> =
//
//        listOf(
//            MyNote("Заметка 1","Текст заметки", 0xfff06292.toInt()),
//            MyNote("Заметка 2","Текст заметки", 0xff9575cd.toInt()),
//            MyNote("Заметка 3", "Текст заметки", 0xff64b5f6.toInt())
//        )


    //если с init, то notes д.б. private
//    init {
//        notes = listOf(
//            MyNote("Заметка 1", "Текст заметки", 0xfff06292.toInt()),
//            MyNote("Заметка 2", "Текст заметки", 0xff9575cd.toInt()),
//            MyNote("Заметка 3", "Текст заметки", 0xff64b5f6.toInt())
//        )
//    }

}