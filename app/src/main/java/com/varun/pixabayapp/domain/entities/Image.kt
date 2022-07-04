package com.varun.pixabayapp.domain.entities

import com.varun.pixabayapp.domain.vo.*
import java.io.Serializable


data class Image(
    val id: ID,
    val socials: Socials,
    val user: User,
    val previewProperties: PreviewProperties,
    val imageProperties: ImageProperties,
    val webProperties: WebProperties,
    val tags: List<Tag>,
    val imageURL: String?,
    val fullHdUrl: String?,
    val largeImageUrl: String,
    val pageUrl: String,
) : Serializable {

    fun getTagsAsHashTags(): String {
        return tags.joinToString(" ") { "#${it.name}" }
    }
}