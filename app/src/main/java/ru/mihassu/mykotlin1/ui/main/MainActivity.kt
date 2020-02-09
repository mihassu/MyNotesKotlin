//@file:Suppress("DEPRECATION")

package ru.mihassu.mykotlin1.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note.*
import ru.mihassu.mykotlin1.R
import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.ui.base.BaseActivity
import ru.mihassu.mykotlin1.ui.note.NoteActivity


class MainActivity : BaseActivity<List<MyNote>?, MainViewState>() {

    //    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainAdapter

    /*инициализация ViewModel-и
    lazy - при первом обращении к свойству оно будет проинициализировано, а при последующих
    запросах будет возвращаться один и тот же объект*/
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
    }

    //инициализация макета
    override val layoutRes: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initFab()
        initRecyclerView()
    }

    private fun initRecyclerView() {
//        var rv = findViewById<RecyclerView>(R.id.notesRecyclerview)
        adapter = MainAdapter()
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        notesRecyclerview.layoutManager = linearLayout
        adapter.onNoteClickListener = object : MainAdapter.OnNoteClickListener {
            override fun onNoteClick(note: MyNote) {
                startNoteActivity(note)
            }
        }
        notesRecyclerview.adapter = adapter
    }

    override fun renderData(data: List<MyNote>?) {
        Toast.makeText(this, "renderData", Toast.LENGTH_SHORT).show()
        data?.let { adapter.dataList = it }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initFab() {
        main_fab.setOnClickListener { startNoteActivity(null) }
    }

    private fun startNoteActivity(note: MyNote?) {
        val intent: Intent = NoteActivity.getStartIntent(this, note?.id)
        startActivity(intent)
    }
}
