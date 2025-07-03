package com.hjw0623.core.domain.search.text_search

import com.hjw0623.core.domain.product.Product


fun filterProducts(
    allProducts: List<Product>,
    filters: Map<FilterType, Boolean>
): List<Product> {
    return allProducts.filter { product ->
        val storeMatch = listOf(
            FilterType.CU to "CU",
            FilterType.GS25 to "GS25",
            FilterType.EMART24 to "EMART24",
            FilterType.SEVEN to "SEVENELEVEN"
        ).filter { filters[it.first] == true }
            .map { it.second }
            .ifEmpty { null }

        val promoMatch = listOf(
            FilterType.ONE_PLUS_ONE to "1+1",
            FilterType.TWO_PLUS_ONE to "2+1"
        ).filter { filters[it.first] == true }
            .map { it.second }
            .ifEmpty { null }

        val storeMatches = storeMatch?.any { product.brand.contains(it) } ?: true
        val promoMatches = promoMatch?.any { product.promotion.contains(it) } ?: true

        storeMatches && promoMatches
    }
}
