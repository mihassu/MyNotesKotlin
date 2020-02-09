package ru.mihassu.mykotlin1.ui.note

import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.ui.base.BaseViewState

class NoteViewState(note: MyNote? = null, error: Throwable? = null) :
    BaseViewState<MyNote?>(note, error){
}