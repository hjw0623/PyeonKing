package com.hjw0623.data.model.mapper

import com.hjw0623.core.business_logic.model.response.PoiInfo
import com.hjw0623.data.model.Document

fun Document.toPoiInfoModel(): PoiInfo {
    return PoiInfo(
        id = this.id?:"id 없음",
        placeName = this.placeName?: "이름 없음",
        categoryName = this.categoryName?: "카테고리 없음",
        phone = this.phone ?: "전화 번호 없음",
        addressName = this.addressName?:"주소 없음",
        roadAddressName = this.roadAddressName?:"도로명 없음",
        latitude = this.y?.toDoubleOrNull() ?: 0.0,
        longitude = this.x?.toDoubleOrNull() ?: 0.0
    )
}

fun List<Document>.toPoiInfoModelList(): List<PoiInfo> {
    return this.map { it.toPoiInfoModel() }
}