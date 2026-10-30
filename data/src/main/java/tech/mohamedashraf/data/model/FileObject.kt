package tech.mohamedashraf.data.model

import com.google.gson.annotations.SerializedName

data class FileObject(
    @SerializedName("id") val id: String,
    @SerializedName("bytes") val bytes: Long? = null,
    @SerializedName("filename") val filename: String? = null,
)