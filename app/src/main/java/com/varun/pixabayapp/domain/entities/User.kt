package com.varun.pixabayapp.domain.entities

import com.varun.pixabayapp.domain.vo.ID


class User(
    val id: ID,
    val name: String,
    val imageURL: String,
)