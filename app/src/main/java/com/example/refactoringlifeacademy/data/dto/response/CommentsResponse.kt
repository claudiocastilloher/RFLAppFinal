package com.example.refactoringlifeacademy.data.dto.response

import com.example.refactoringlifeacademy.data.dto.model.Comment
import com.google.gson.annotations.SerializedName

data class CommentsResponse(
    @SerializedName("comments") val comments: List<Comment>?
)
