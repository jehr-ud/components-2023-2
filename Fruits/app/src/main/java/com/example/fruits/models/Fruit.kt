package com.example.fruits.models
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Fruit(
    val name: String,
    val id: Int,
    val family: String,
    val order: String,
    val genus: String,
    val nutritions: Nutritions
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(Nutritions::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(family)
        parcel.writeString(order)
        parcel.writeString(genus)
        parcel.writeParcelable(nutritions, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fruit> {
        override fun createFromParcel(parcel: Parcel): Fruit {
            return Fruit(parcel)
        }

        override fun newArray(size: Int): Array<Fruit?> {
            return arrayOfNulls(size)
        }
    }
}