package com.varun.pixabayapp.data.remote.dtos


import com.google.gson.annotations.SerializedName
import com.varun.pixabayapp.domain.entities.Image
import com.varun.pixabayapp.domain.entities.User
import com.varun.pixabayapp.domain.vo.*


class Image(

    @SerializedName("id")
    val id: Int,

    @SerializedName("comments")
    val comments: Int,

    @SerializedName("downloads")
    val downloads: Int,

    @SerializedName("fullHDURL")
    val fullHDURL: String?,

    @SerializedName("imageHeight")
    val imageHeight: Int,

    @SerializedName("imageSize")
    val imageSize: Int,

    @SerializedName("imageURL")
    val imageURL: String?,

    @SerializedName("imageWidth")
    val imageWidth: Int,

    @SerializedName("largeImageURL")
    val largeImageURL: String,

    @SerializedName("likes")
    val likes: Int,

    @SerializedName("pageURL")
    val pageURL: String,

    @SerializedName("previewHeight")
    val previewHeight: Int,

    @SerializedName("previewURL")
    val previewURL: String,

    @SerializedName("previewWidth")
    val previewWidth: Int,

    @SerializedName("tags")
    val tags: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("user")
    val user: String,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("userImageURL")
    val userImageURL: String,

    @SerializedName("views")
    val views: Int,

    @SerializedName("webformatHeight")
    val webformatHeight: Int,

    @SerializedName("webformatURL")
    val webformatURL: String,

    @SerializedName("webformatWidth")
    val webformatWidth: Int
) {

    fun toDomainEntity(): Image {
        return Image(
            ID(id),
            Socials(comments, downloads, views, likes),
            User(ID(userId), user, userImageURL),
            PreviewProperties(previewHeight, previewURL, previewWidth),
            ImageProperties(imageHeight, imageSize, imageWidth),
            WebProperties(webformatHeight, webformatURL, webformatWidth),
            getTagList(),
            imageURL,
            fullHDURL,
            largeImageURL,
            pageURL,
        )
    }

    private fun getTagList(): List<Tag> {
        return tags.split(",").map { Tag(it.trim()) }
    }
}