package com.kotlinhva.gamebacklog52.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Game(
    var title: String,
    var platform: String,
    var releaseDate: Date,
    @PrimaryKey var id : Long? = null
) : Parcelable