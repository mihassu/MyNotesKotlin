package ru.mihassu.mykotlin1.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import ru.mihassu.mykotlin1.R
import ru.mihassu.mykotlin1.data.model.MyNote

//class NotesRVAdapter(val onItemClick : ((Note) -> Unit)? = null) - kotlin_3
class MainAdapter : RecyclerView.Adapter<MainAdapter.NotesViewHolder>() {

    var dataList: List<MyNote> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onNoteClickListener: OnNoteClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size


    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

//        private val noteTitle = view.findViewById<TextView>(R.id.item_title)
//        private val noteText = view.findViewById<TextView>(R.id.item_text)

        fun bind(currentNote: MyNote) = with(itemView) {
//            noteTitle.text = currentNote.title
//            noteText.text = currentNote.note
            item_title.text = currentNote.title
            item_text.text = currentNote.note

            val color = when (currentNote.color) {
                MyNote.Color.WHITE -> R.color.white
                MyNote.Color.YELLOW -> R.color.yellow
                MyNote.Color.GREEN -> R.color.green
                MyNote.Color.BLUE -> R.color.blue
                MyNote.Color.RED -> R.color.red
                MyNote.Color.VIOLET -> R.color.violet
                MyNote.Color.PINK -> R.color.pink
            }
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
//            itemView.setBackgroundColor(itemView.context.resources.getColor(color))

            itemView.setOnClickListener{onNoteClickListener?.onNoteClick(currentNote)}
        }
    }

    interface OnNoteClickListener{
        fun onNoteClick(note: MyNote)
    }
}