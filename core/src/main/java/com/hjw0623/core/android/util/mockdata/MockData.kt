package com.hjw0623.core.android.util.mockdata

import com.hjw0623.core.domain.model.user.User
import com.hjw0623.core.domain.model.product.Product
import com.hjw0623.core.domain.model.product.ReviewItem
import com.hjw0623.core.domain.model.review.ReviewInfo
import java.util.UUID

val mockProduct = Product(
    id = UUID.randomUUID().toString(),
    imgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801094083205_003.jpg",
    name = "코카콜라제로캔350ML",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "CU"
)
val mockProduct2 = Product(
    id = UUID.randomUUID().toString(),
    imgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801056150013_008.jpg",
    name = "롯데)펩시콜라캔355ML",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "GS25"
)
val mockProduct3 = Product(
    id = UUID.randomUUID().toString(),
    imgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801094063283_002.jpg",
    name = "코카콜라제로레몬캔350ML",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "EMART24"
)
val mockProduct4= Product(
    id = UUID.randomUUID().toString(),
    imgUrl = "https://msave.emart24.co.kr/cmsbo/upload/nHq/plu_image/500x500/8801056193010.JPG",
    name = "롯데)펩시콜라펫600ml",
    price = "2100",
    priceForEach = "1050",
    promotion = "1+1",
    brand = "SEVENELEVEN"
)



val mockProductList = listOf(
    mockProduct,
    mockProduct.copy(id = UUID.randomUUID().toString(), promotion = "2+1"),
    mockProduct2,
    mockProduct2.copy(id = UUID.randomUUID().toString(), promotion = "2+1"),
    mockProduct3,
    mockProduct3.copy(id = UUID.randomUUID().toString(), promotion = "2+1"),
    mockProduct4,
    mockProduct4.copy(id = UUID.randomUUID().toString(), promotion = "2+1")
)


val mockReviewList = listOf(
    ReviewItem(
        reviewId = "1",
        rating = 5f,
        content = "맛있어요",
        userName = "fwf",
        createdAt = "2023-06-23"
    ),
    ReviewItem(
        reviewId = "2",
        rating = 4f,
        content = "맛있어요",
        userName = "fdf",
        createdAt = "2023-06-23"
    ),
    ReviewItem(
        reviewId = "3",
        rating = 3f,
        content = "맛있어요",
        userName = "fdfd",
        createdAt = "2023-06-23"
    )
)

val mockRatingList  = listOf(
    Pair(5, 10),
    Pair(4, 8),
    Pair(3, 5),
    Pair(2, 4),
    Pair(1, 2),
)

val mockAvgRating = calculateAverageRating(mockRatingList)

val mockReviewInfo = ReviewInfo(
    reviewId = 2,
    content = "상쾌하고 좋네요! 여름에 딱이에요.",
    starRating = 5,
    createdAt = "2025-06-27 02:16:06.000000",
    productName = "코카콜라제로캔350ML",
    productImgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801094083205_003.jpg"
)

val mockReviewHistoryList = listOf(
    mockReviewInfo.copy(reviewId = 1),
    mockReviewInfo.copy(reviewId = 2),
    mockReviewInfo.copy(reviewId = 3),
    mockReviewInfo.copy(reviewId = 4),
    mockReviewInfo.copy(reviewId = 5),
)

val mockTakenNicknames = setOf("닉네임1", "닉네임2", "닉네임3", "닉네임4", "닉네임5")

val mockUser = User(
    email = "newuser@test.com",
    nickname = "신규회원",
    accessToken = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhY2Nlc3MiLCJ1c2VySUQiOjEwMSwiZXhwIjoxNzU4NDI4NTA5LCJpYXQiOjE3NTI0Mjg1MDksImlzcyI6InRlc3QifQ.RywnIQ_m2KbuXUZbxYJ36q1FS__GUKw3Dxo4giiSCMA",
    refreshToken = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhY2Nlc3MiLCJ1c2VySUQiOjEwMSwiZXhwIjoxNzcwNDI4NTA5LCJpYXQiOjE3NTI0Mjg1MDksImlzcyI6InRlc3QifQ.AWi_qMm9DGACW9CXQQ1e3hDmZjn3vs8UoSCAc-9qccs"
)