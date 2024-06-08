package com.example.projectakhirbudaya.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity
data class BudayaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "nama")
    val name: String,

    @ColumnInfo(name = "desc")
    val description: String,

    @ColumnInfo(name = "image")
    val image: File,

    @ColumnInfo(name = "location")
    val location: String,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        File(parcel.readString()!!),
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(image.path)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<BudayaEntity> {
        override fun createFromParcel(parcel: Parcel): BudayaEntity {
            return BudayaEntity(parcel)
        }

        override fun newArray(size: Int): Array<BudayaEntity?> {
            return arrayOfNulls(size)
        }
    }
}