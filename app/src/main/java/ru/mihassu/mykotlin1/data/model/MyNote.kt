package ru.mihassu.mykotlin1.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MyNote(val id: String = "",
                  val title: String = "",
                  val note: String = "",
                  val color: Color = Color.WHITE,
                  val lastChange: Date = Date()) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if(this === other) return true //сравниваем ссылки
        if (javaClass != other?.javaClass) return false

        other as MyNote

        if (other.id != this.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()

    enum class Color{
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK
    }
}

