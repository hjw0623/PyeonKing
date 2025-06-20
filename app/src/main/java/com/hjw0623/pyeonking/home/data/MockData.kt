package com.hjw0623.pyeonking.home.data

import com.hjw0623.pyeonking.home.presentation.HomeScreenState
import java.util.UUID

val mockRecommendItem1 = RecommendItem(
    uuid = UUID.randomUUID().toString(),
    imgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801094083205_003.jpg",
    name = "코카콜라제로캔350ML",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "CU"
)

val mockRecommendItem2 = RecommendItem(
    uuid = UUID.randomUUID().toString(),
    imgUrl = "https://msave.emart24.co.kr/cmsbo/upload/nHq/plu_image/500x500/8809617945146.JPG",
    name = "바프)카라멜솔티드아몬드앤프레첼40g",
    price = "2500",
    priceForEach = "1467",
    promotion = "2+1",
    brand = "EMART24"
)
val mockRecommendItemList = listOf(
    mockRecommendItem1,
    mockRecommendItem2,
    mockRecommendItem1.copy(uuid = UUID.randomUUID().toString()),
    mockRecommendItem1.copy(uuid = UUID.randomUUID().toString()),
    mockRecommendItem1.copy(uuid = UUID.randomUUID().toString()),
    mockRecommendItem1.copy(uuid = UUID.randomUUID().toString())
)

val mockHomeScreenState = HomeScreenState(
    isLoading = false,
    recommendList = mockRecommendItemList
)