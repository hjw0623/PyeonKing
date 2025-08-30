package com.hjw0623.core.domain.model.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchResultNavArgs(
    val source: SearchResultSource,
    val passedQuery: String?,
    val passedImagePath: String?
): Parcelable