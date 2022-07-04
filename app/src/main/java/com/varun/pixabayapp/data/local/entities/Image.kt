package com.varun.pixabayapp.data.local.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.varun.pixabayapp.domain.entities.Image
import com.varun.pixabayapp.domain.entities.User
import com.varun.pixabayapp.domain.vo.*


@Entity(tableName = "image_table")
class Image(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "comments")
    val comments: Int,

    @ColumnInfo(name = "downloads")
    val downloads: Int,

    @ColumnInfo(name = "full_hd_url")
    val fullHDURL: String?,

    @ColumnInfo(name = "image_height")
    val imageHeight: Int,

    @ColumnInfo(name = "image_size")
    val imageSize: Int,

    @ColumnInfo(name = "image_url")
    val imageURL: String?,

    @ColumnInfo(name = "image_width")
    val imageWidth: Int,

    @ColumnInfo(name = "large_image_url")
    val largeImageURL: String,

    @ColumnInfo(name = "likes")
    val likes: Int,

    @ColumnInfo(name = "page_url")
    val pageURL: String,

    @ColumnInfo(name = "preview_height")
    val previewHeight: Int,

    @ColumnInfo(name = "preview_url")
    val previewURL: String,

    @ColumnInfo(name = "preview_width")
    val previewWidth: Int,

    @ColumnInfo(name = "tags")
    val tags: String,

    @ColumnInfo(name = "user")
    val user: String,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "user_image_url")
    val userImageURL: String,

    @ColumnInfo(name = "views")
    val views: Int,

    @ColumnInfo(name = "web_format_height")
    val webFormatHeight: Int,

    @ColumnInfo(name = "web_format_url")
    val webFormatURL: String,

    @ColumnInfo(name = "web_format_width")
    val webFormatWidth: Int
) {

    fun toDomainEntity(): Image {
        return Image(
            ID(id),
            Socials(comments, downloads, views, likes),
            User(ID(userId), user, userImageURL),
            PreviewProperties(previewHeight, previewURL, previewWidth),
            ImageProperties(imageHeight, imageSize, imageWidth),
            WebProperties(webFormatHeight, webFormatURL, webFormatWidth),
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

fun Image.toDbEntity(): com.varun.pixabayapp.data.local.entities.Image {
    val tagsString: String = tags.map { it.name }.joinToString()
    return Image(
        id.value,
        socials.comments,
        socials.downloads,
        fullHdUrl,
        imageProperties.imageHeight,
        imageProperties.imageSize,
        imageURL,
        imageProperties.imageWidth,
        largeImageUrl,
        socials.likes,
        pageUrl,
        previewProperties.previewHeight,
        previewProperties.previewURL,
        previewProperties.previewWidth,
        tagsString,
        user.name,
        user.id.value,
        user.imageURL,
        socials.views,
        webProperties.webFormatHeight,
        webProperties.webFormatURL,
        webProperties.webFormatWidth
    )
}