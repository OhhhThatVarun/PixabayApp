package com.varun.pixabayapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
class RemoteKeys(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "prev_page")
    val prevKey: Int?,

    @ColumnInfo(name = "next_page")
    val nextKey: Int?
)