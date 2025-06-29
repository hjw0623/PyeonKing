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
val mockProduct2 = Product(
    uuid = UUID.randomUUID().toString(),
    imgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801056150013_008.jpg",
    name = "롯데)펩시콜라캔355ML",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "GS25"
)
val mockProduct3 = Product(
    uuid = UUID.randomUUID().toString(),
    imgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801094063283_002.jpg",
    name = "코카콜라제로레몬캔350ML",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "EMART24"
)
val mockProduct4= Product(
    uuid = UUID.randomUUID().toString(),
    imgUrl = "https://msave.emart24.co.kr/cmsbo/upload/nHq/plu_image/500x500/8801056193010.JPG",
    name = "롯데)펩시콜라펫600ml",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "SEVENELEVEN"
)



val mockProductList = listOf(
    mockProduct,
    mockProduct.copy(uuid = UUID.randomUUID().toString(), promotion = "2+1"),
    mockProduct2,
    mockProduct2.copy(uuid = UUID.randomUUID().toString(), promotion = "2+1"),
    mockProduct3,
    mockProduct3.copy(uuid = UUID.randomUUID().toString(), promotion = "2+1"),
    mockProduct4,
    mockProduct4.copy(uuid = UUID.randomUUID().toString(), promotion = "2+1")
)

