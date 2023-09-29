package com.proyecto.android.savemymoney.modelo

import android.os.Parcel
import android.os.Parcelable

data class Gasto(var tipo: String?, var descripcion: String?, var precio: Double, var fecha: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tipo)
        parcel.writeString(descripcion)
        parcel.writeDouble(precio)
        parcel.writeString(fecha)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Gasto> {
        override fun createFromParcel(parcel: Parcel): Gasto {
            return Gasto(parcel)
        }

        override fun newArray(size: Int): Array<Gasto?> {
            return arrayOfNulls(size)
        }
    }
}
