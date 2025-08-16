package com.hjw0623.core.constants

import com.hjw0623.core.BuildConfig

object TopBarTitle {
    const val TITLE_REVIEW_EDIT = "리뷰 수정"
    const val TITLE_REVIEW_WRITE = "리뷰 작성"
    const val TITLE_SEARCH_RESULT = "검색 결과"
}

object ScreenRoutes {
    const val REVIEW_EDIT = "ReviewEdit"
    const val REVIEW_WRITE = "ReviewWrite"

    const val REVIEW_HISTORY = "ReviewHistory"
    const val SEARCH_RESULT = "SearchResult"
    const val PRODUCT_DETAIL = "ProductDetail"
    const val HOME = "Home"
    const val CAMERA_SEARCH = "Camera"
    const val TEXT_SEARCH = "TextSearch"
    const val MY_PAGE = "MyPage"
    const val REGISTER_SUCCESS = "RegisterSuccess"

    const val LOGIN = "Login"
    const val REGISTER = "Register"
    const val REGISTER_SUCCESS_ROUTE = "RegisterSuccess"
}

object EmptyString {
    const val EMPTY_STRING = ""
}

object MainScreenTitle {
    const val HOME = "홈"
    const val CAMERA_SEARCH = "카메라 검색"
    const val TEXT_SEARCH = "검색"
    const val MYPAGE = "마이페이지"
}

object Brand {
    const val CU = "CU"
    const val GS25 = "GS25"
    const val EMART24 = "EMART24"
    const val SEVEN = "SEVENELEVEN"

    const val CU_KR = "cu"
    const val GS25_KR = "gs25"
    const val EMART24_KR = "이마트24"
    const val SEVEN_KR = "세븐일레븐"
}

object Promotion {
    const val ONE_PLUS_ONE = "1+1"
    const val TWO_PLUS_ONE = "2+1"

    const val ONE_PLUS_ONE_RESPONSE = "ONE_PLUS_ONE"

    const val TWO_PLUS_ONE_RESPONSE = "TWO_PLUS_ONE"
}

object Api {
    const val BASE_URL = BuildConfig.BASE_URL
    const val IMAGE_URL = BuildConfig.IMAGE_URL

    const val KAKAO_BASE_URL = "https://dapi.kakao.com/"
}

object Error {
    const val UNKNOWN_ERROR = "알 수 없는 오류가 발생했습니다. \n 잠시 후 다시 시도해 주세요."

    const val DUPLICATED_NICKNAME = "이미 사용 중인 닉네임입니다."

    const val BLANK_INPUT = "한글 자 이상 입력해주세요."

    const val TOO_SHORT_SEARCH_QUERY_INPUT = "검색어는 2글자 이상 입력해주세요"

    const val UNCHANGED_NICKNAME = "현재 닉네임과 동일합니다."

    const val SAVE_PICTURE_ERROR = "사진 저장 실패"

    const val NO_IMAGE = "검색할 이미지가 없습니다."

    const val NO_SEARCH_RESULT = "검색 결과가 없습니다."

    const val NO_REVIEW = "작성된 리뷰가 없습니다."

    const val NO_CURRENT_LOCATION = "현재 위치가 없습니다."

    const val FAILED_TO_GET_LOCATION = "현재 위치를 가져올 수 없어요."

    const val SEARCH_FAILED = "검색에 실패했어요."

    const val SEARCH_HISTORY_DELETE_FAILED = "검색 기록 삭제에 실패했습니다."

    const val SEARCH_HISTORY_SAVE_FAILED = "검색 기록 저장에 실패했습니다."

    const val SEARCH_QUERY_EMPTY = "검색어를 입력해주세요."
}

object UiText {
    const val TITLE_TEXT_SEARCH = "검색 결과"

    const val TITLE_CAMERA_SEARCH = "사진으로 검색한 결과"
}