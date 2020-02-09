package ru.mihassu.mykotlin1.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import ru.mihassu.mykotlin1.R

import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_note.*
import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.extensions.DATE_FORMAT
import ru.mihassu.mykotlin1.extensions.SAVE_DELAY
import ru.mihassu.mykotlin1.ui.base.BaseActivity
import ru.mihassu.mykotlin1.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<MyNote?, NoteViewState>() {


    //Статичный блок, который создает Intent с самим собой
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.Note"

        fun getStartIntent(context: Context, noteId: String?) : Intent {

            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            return intent
        }
    }

    //
    private var note: MyNote? = null

    //ViewModel
    override val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(NoteViewModel::class.java)
    }

    //Макет
    override val layoutRes: Int = R.layout.activity_note

    //слушатель на текстовые поля
    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        noteId?.let { viewModel.loadNote(it) }

        initToolbar(noteId)
        note_text.addTextChangedListener(textChangeListener)
//        initFab()
//        initView()
    }

    override fun renderData(data: MyNote?) {
        this.note = data
        initView()
        Log.d("NoteActivity", "renderData")
    }

    //    private fun initFab() {
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//    }

    private fun initToolbar(noteId: String?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //установка заголовка
        if (noteId == null) supportActionBar?.title =
            getString(R.string.new_note_title) //Новая заметка

//        supportActionBar?.title = if (noteId != null) {
//            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note!!.lastChange)
//        } else {
//            getString(R.string.new_note_title) //Новая заметка
//        }
    }

    private fun initView() {
        note_title.setText(note?.title ?: "")
        note_text.setText(note?.note ?: "")
        if (note != null) {
            val color = when (note!!.color) {
                MyNote.Color.WHITE -> R.color.white
                MyNote.Color.YELLOW -> R.color.yellow
                MyNote.Color.GREEN -> R.color.green
                MyNote.Color.BLUE -> R.color.blue
                MyNote.Color.RED -> R.color.red
                MyNote.Color.VIOLET -> R.color.violet
                MyNote.Color.PINK -> R.color.pink
            }
            toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
        }
    }

    private fun saveNote() {
        if (note_title.text.isNullOrBlank() || note_title.text.length < 3) return

        //Для выполнения с задержкой (SAVE_DELAY)
        Handler().postDelayed({
            note = note?.copy(
                title = note_title.text.toString(),
                note = note_text.text.toString(),
                lastChange = Date()
            //если note == null (в том случае когда нажимаем на main_fab), то создается новая заметка
            ) ?: createNewNote()

            note?.let { viewModel.saveChanges(note!!) }
        }, SAVE_DELAY)
    }

    private fun createNewNote() : MyNote = MyNote(
        UUID.randomUUID().toString(),
        title = note_title.text.toString(),
        note = note_text.text.toString()
    )

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
