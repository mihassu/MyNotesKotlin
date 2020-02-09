package ru.mihassu.mykotlin1.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.data.model.NoteResult
import ru.mihassu.mykotlin1.data.provider.RemoteDataProvider
import java.lang.Exception


class FireStoreDataProvider : RemoteDataProvider{

    companion object {
        private const val MY_NOTES_COLLECTION = "my_notes"
    }

    private val TAG = "${FireStoreDataProvider::class.java.simpleName} :"

    //получить экземпляр базы данных
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    //создать новую коллекцию
    private val notesDb by lazy {
        db.collection(MY_NOTES_COLLECTION)
    }


    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesDb
            /*добавить к коллекции слущатель, который будет вызывать метод onEvent при
                * первом добавлении и при каждом изменении коллекции. В метод onEvent
                * передается снимок обновленной коллекции*/
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                if (p1 != null) {
                    result.value = NoteResult.Error(p1)
                } else if (p0 != null ) {
                    val notes = mutableListOf<MyNote>()
                    //преобразовать документы из снимка
                    for (note: QueryDocumentSnapshot in p0) {
                        notes.add(note.toObject(MyNote::class.java))
                        /*для преобразования данных в классе MyNote д.б. пустой конструктор
                        * или заданы параметры по умолчанию*/
                    }
                    result.value = NoteResult.Success(notes)
                }
            }
        })
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesDb
            .document(id)
            .get() //получаем снимок документа (экземпляр DocumentSnapshot)
            .addOnSuccessListener(OnSuccessListener {
                result.value = NoteResult.Success(it.toObject(MyNote::class.java)) //преобразуем снимок в нужный объект
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(p0: Exception) {
                    result.value = NoteResult.Error(p0)
                }
            })
        return result
    }

    override fun saveNote(note: MyNote): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesDb
            .document(note.id) //получить ссылку на документ
            .set(note) //сохранить новый документ или заменить существующий
            .addOnSuccessListener {
                OnSuccessListener<Void> {
                    Log.d(TAG, "Note $note is saved")
                    result.value = NoteResult.Success(note)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Error, $note is not saved. ${it.message}")
                result.value = NoteResult.Error(it)
            }

        return result
    }
}