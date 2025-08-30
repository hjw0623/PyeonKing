package com.hjw0623.core.constants.error

object ErrorMessage {
    // 공통 에러
    const val ERROR_UNKNOWN = "알 수 없는 오류가 발생했습니다. \n 잠시 후 다시 시도해 주세요."

    // 닉네임 관련
    const val ERROR_NICKNAME_DUPLICATED = "이미 사용 중인 닉네임입니다."
    const val ERROR_NICKNAME_UNCHANGED = "현재 닉네임과 동일합니다."

    // 입력 관련
    const val ERROR_INPUT_BLANK = "한글 자 이상 입력해주세요."
    const val ERROR_INPUT_SEARCH_TOO_SHORT = "검색어는 2글자 이상 입력해주세요."
    const val ERROR_INPUT_SEARCH_EMPTY = "검색어를 입력해주세요."

    // 리뷰 / 이미지
    const val ERROR_REVIEW_NONE = "작성된 리뷰가 없습니다."
    const val ERROR_IMAGE_NONE = "검색할 이미지가 없습니다."
    const val ERROR_IMAGE_SAVE_FAILED = "사진 저장 실패"

    // 위치
    const val ERROR_LOCATION_NONE = "현재 위치가 없습니다."
    const val ERROR_LOCATION_FAILED = "현재 위치를 가져올 수 없어요."

    // 검색
    const val ERROR_SEARCH_FAILED = "검색에 실패했어요."
    const val ERROR_SEARCH_RESULT_NONE = "검색 결과가 없습니다."
    const val ERROR_SEARCH_HISTORY_DELETE_FAILED = "검색 기록 삭제에 실패했습니다."
    const val ERROR_SEARCH_HISTORY_SAVE_FAILED = "검색 기록 저장에 실패했습니다."
}