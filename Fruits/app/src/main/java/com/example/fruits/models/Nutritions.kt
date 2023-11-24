package com.example.fruits.models

import android.os.Parcel
import android.os.Parcelable

data class Nutritions(
    val calories: Double,
    val fat: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val protein: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(calories)
        parcel.writeDouble(fat)
        parcel.writeDouble(sugar)
        parcel.writeDouble(carbohydrates)
        parcel.writeDouble(protein)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Nutritions> {
        override fun createFromParcel(parcel: Parcel): Nutritions {
            return Nutritions(parcel)
        }

        override fun newArray(size: Int): Array<Nutritions?> {
            return arrayOfNulls(size)
        }
    }
}