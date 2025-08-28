package com.hjw0623.presentation.util

import android.content.Context
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.size.Size
import com.hjw0623.core.R
import com.hjw0623.core.core_ui.ui.getFullImageUrl
import timber.log.Timber

const val COIL_CALL_TAG = "COIL_TAG"

class CoilImageRequest {
    companion object {
        fun getImageRequest(context: Context, sourceImage: String?) =
            ImageRequest.Builder(context)
                .data(data = getFullImageUrl(sourceImage))
                .size(Size.ORIGINAL)
                .error(R.drawable.no_image)
                .crossfade(true)
                .build()

        fun coilCallbackLog(imageSource: String, coilState: AsyncImagePainter.State) {
            when (coilState) {
                is AsyncImagePainter.State.Success -> {
                    Timber.tag(COIL_CALL_TAG).e(" $imageSource, Success")
                }

                is AsyncImagePainter.State.Error -> {
                    Timber.tag(COIL_CALL_TAG)
                        .e("$imageSource , ${coilState.result.throwable.message}")
                }
                is AsyncImagePainter.State.Empty -> {
                    Timber.tag(COIL_CALL_TAG).e("$imageSource , $coilState")
                }
                is AsyncImagePainter.State.Loading -> {
                    Timber.tag(COIL_CALL_TAG).e("$imageSource , $coilState")
                }
                else -> {
                    Timber.tag(COIL_CALL_TAG).e("$imageSource , 알 수 없는 Coil Error : $coilState")
                }
            }
        }
    }
}
