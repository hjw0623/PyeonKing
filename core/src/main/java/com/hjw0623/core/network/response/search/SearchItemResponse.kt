package com.hjw0623.core.network.response.search

import com.hjw0623.core.network.response.Item

data class SearchItemResponse(
    val searchItems: List<Item>
)