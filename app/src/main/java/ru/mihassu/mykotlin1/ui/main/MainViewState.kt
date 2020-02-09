package ru.mihassu.mykotlin1.ui.main

import ru.mihassu.mykotlin1.data.model.MyNote
import ru.mihassu.mykotlin1.ui.base.BaseViewState

class MainViewState(val notes: List<MyNote>? = null, error: Throwable? = null)
    : BaseViewState<List<MyNote>?>(notes, error)