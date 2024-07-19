package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("idComment") val idComment: Int?,
    @SerializedName("comment") val comment: String?,
    @SerializedName("commentBy") val commentBy: String?
)
