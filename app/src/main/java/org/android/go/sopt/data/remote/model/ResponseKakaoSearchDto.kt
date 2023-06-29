package org.android.go.sopt.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResponseKakaoSearchDto(
    @SerialName("meta")
    val meta: Meta,
    @SerialName("documents")
    val documents: List<Document>,
) {
    @Serializable
    data class Meta(
        @SerialName("total_count")
        val totalCount: Int,
        @SerialName("pageable_count")
        val pageableCount: Int,
        @SerialName("is_end")
        val is_end: Boolean,
    )

    @Serializable
    data class Document(
        @SerialName("datetime")
        val datetime: String,
        @SerialName("contents")
        val contents: String,
        @SerialName("title")
        val title: String,
        @SerialName("url")
        val url: String,
    )
}