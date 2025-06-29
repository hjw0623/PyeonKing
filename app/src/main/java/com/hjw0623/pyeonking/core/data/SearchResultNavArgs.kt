package com.hjw0623.pyeonking.core.data

import android.os.Parcelable
import com.hjw0623.pyeonking.search_result.data.SearchResultSource
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SearchResultNavArgs(
    val source: SearchResultSource,
    val passedQuery: String?,
    val passedImagePath: String?
): Parcelable
