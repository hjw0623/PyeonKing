package com.hjw0623.core.domain.model.search

import com.hjw0623.core.constants.brand.Brand
import com.hjw0623.core.constants.promotion.PromotionConstants
import com.hjw0623.core.domain.model.product.Product

fun filterProducts(
    allProducts: List<Product>,
    filters: Map<FilterType, Boolean>
): List<Product> {
    return allProducts.filter { product ->
        val storeMatch = listOf(
            FilterType.CU to Brand.CU,
            FilterType.GS25 to Brand.GS25,
            FilterType.EMART24 to Brand.EMART24,
            FilterType.SEVEN to Brand.SEVEN
        ).filter { filters[it.first] == true }
            .map { it.second }
            .ifEmpty { null }

        val promotionMatch = listOf(
            FilterType.ONE_PLUS_ONE to PromotionConstants.ONE_PLUS_ONE,
            FilterType.TWO_PLUS_ONE to PromotionConstants.TWO_PLUS_ONE
        ).filter { filters[it.first] == true }
            .map { it.second }
            .ifEmpty { null }

        val storeMatches = storeMatch?.any { product.brand.contains(it) } ?: true
        val promotionMatches = promotionMatch?.any { product.promotion.contains(it) } ?: true

        storeMatches && promotionMatches
    }
}
