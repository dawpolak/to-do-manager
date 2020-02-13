package com.example.todomanager

import android.os.Parcel
import android.os.Parcelable

data class task(var id: Int, var title: String?, var description:String?, var priority: Int, var status: String?, var date: String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(priority)
        parcel.writeString(status)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<task> {
        override fun createFromParcel(parcel: Parcel): task {
            return task(parcel)
        }

        override fun newArray(size: Int): Array<task?> {
            return arrayOfNulls(size)
        }
    }
}