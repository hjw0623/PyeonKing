package com.hjw0623.pyeonking.core.data

import java.util.UUID

val mockProduct = Product(
    uuid = UUID.randomUUID().toString(),
    imgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801094083205_003.jpg",
    name = "코카콜라제로캔350ML",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "CU"
)

val mockProductList = listOf(
    mockProduct,
    mockProduct.copy(uuid = UUID.randomUUID().toString()),
    mockProduct.copy(uuid = UUID.randomUUID().toString()),
    mockProduct.copy(uuid = UUID.randomUUID().toString()),
    mockProduct.copy(uuid = UUID.randomUUID().toString())
)