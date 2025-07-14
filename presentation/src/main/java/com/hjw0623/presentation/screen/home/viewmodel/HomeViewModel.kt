package com.hjw0623.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.search.search_result.SearchResultSource
import com.hjw0623.core.util.mockdata.mockProductList
import com.hjw0623.presentation.screen.home.ui.HomeScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val MIN_SEARCH_QUERY_LENGTH = 2

class HomeViewModel(
    //private val productRepository: ProductRepository,
    //private val searchRepository: SearchRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _recommendList = MutableStateFlow<List<Product>>(emptyList())
    val recommendList = _recommendList.asStateFlow()

    private val _event = MutableSharedFlow<HomeScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchRecommendList()
    }

    private fun fetchRecommendList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _recommendList.value = mockProductList
            } catch (e: Exception) {
                _event.emit(HomeScreenEvent.Error("추천 상품을 불러오는 데 실패했습니다."))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSearchQueryChangeDebounced(query: String) {
        //추후 기능 추가
    }

    fun onProductCardClick(product: Product) {
        viewModelScope.launch {
            _event.emit(HomeScreenEvent.NavigateToProductDetail(product))
        }
    }

    fun onSearchClick() {
        val query = searchQuery.value
        if (query.length < MIN_SEARCH_QUERY_LENGTH) {
            viewModelScope.launch {
                _event.emit(HomeScreenEvent.Error("검색어는 ${MIN_SEARCH_QUERY_LENGTH}자 이상 입력해주세요."))
            }
            return
        }

        viewModelScope.launch {
            try {
                delay(500)
                val navArgs = SearchResultNavArgs(
                    source = SearchResultSource.TEXT,
                    passedQuery = query,
                    passedImagePath = null
                )
                _event.emit(HomeScreenEvent.NavigateToSearchResult(navArgs))
            } catch (e: Exception) {
                _event.emit(HomeScreenEvent.Error("검색결과가 없습니다.package com.hjw0623.presentation.screen.product\n" +
                        "\n" +
                        "import androidx.compose.foundation.layout.Column\n" +
                        "import androidx.compose.foundation.layout.PaddingValues\n" +
                        "import androidx.compose.foundation.layout.Row\n" +
                        "import androidx.compose.foundation.layout.Spacer\n" +
                        "import androidx.compose.foundation.layout.fillMaxSize\n" +
                        "import androidx.compose.foundation.layout.fillMaxWidth\n" +
                        "import androidx.compose.foundation.layout.height\n" +
                        "import androidx.compose.foundation.layout.padding\n" +
                        "import androidx.compose.foundation.lazy.LazyColumn\n" +
                        "import androidx.compose.material3.MaterialTheme\n" +
                        "import androidx.compose.material3.Text\n" +
                        "import androidx.compose.runtime.Composable\n" +
                        "import androidx.compose.runtime.LaunchedEffect\n" +
                        "import androidx.compose.runtime.getValue\n" +
                        "import androidx.compose.ui.Alignment\n" +
                        "import androidx.compose.ui.Modifier\n" +
                        "import androidx.compose.ui.platform.LocalContext\n" +
                        "import androidx.compose.ui.res.stringResource\n" +
                        "import androidx.compose.ui.text.font.FontWeight\n" +
                        "import androidx.compose.ui.tooling.preview.Preview\n" +
                        "import androidx.compose.ui.unit.dp\n" +
                        "import androidx.lifecycle.compose.collectAsStateWithLifecycle\n" +
                        "import androidx.lifecycle.viewmodel.compose.viewModel\n" +
                        "import com.hjw0623.core.domain.product.Product\n" +
                        "import com.hjw0623.core.domain.product.ProductDetailTab\n" +
                        "import com.hjw0623.core.domain.product.Review\n" +
                        "import com.hjw0623.core.mockdata.mockProduct\n" +
                        "import com.hjw0623.core.mockdata.mockReviews\n" +
                        "import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton\n" +
                        "import com.hjw0623.core.presentation.designsystem.components.TopRoundedBackground\n" +
                        "import com.hjw0623.core.presentation.designsystem.components.showToast\n" +
                        "import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme\n" +
                        "import com.hjw0623.core.presentation.ui.ObserveAsEvents\n" +
                        "import com.hjw0623.core.presentation.ui.rememberThrottledOnClick\n" +
                        "import com.hjw0623.presentation.R\n" +
                        "import com.hjw0623.presentation.screen.product.componet.MapTab\n" +
                        "import com.hjw0623.presentation.screen.product.componet.ProductCardDetail\n" +
                        "import com.hjw0623.presentation.screen.product.componet.RatingDistribution\n" +
                        "import com.hjw0623.presentation.screen.product.componet.RatingStars\n" +
                        "import com.hjw0623.presentation.screen.product.componet.ReviewListItem\n" +
                        "import com.hjw0623.presentation.screen.product.componet.TabSection\n" +
                        "\n" +
                        "@Composable\n" +
                        "fun ProductDetailScreenRoot(\n" +
                        "    product: Product,\n" +
                        "    onNavigateToReviewWrite: (Product) -> Unit,\n" +
                        "    modifier: Modifier = Modifier,\n" +
                        ") {\n" +
                        "    val viewModel: ProductDetailViewModel = viewModel()\n" +
                        "    val state by viewModel.state.collectAsStateWithLifecycle()\n" +
                        "    val context = LocalContext.current\n" +
                        "\n" +
                        "    // ✅ '리뷰 작성하기' 버튼에 대한 Throttled 클릭 리스너 생성\n" +
                        "    val throttledWriteReviewClick = rememberThrottledOnClick {\n" +
                        "        viewModel.onWriteReviewClick()\n" +
                        "    }\n" +
                        "\n" +
                        "    LaunchedEffect(key1 = product) {\n" +
                        "        viewModel.loadProductDetails(product)\n" +
                        "    }\n" +
                        "\n" +
                        "    ObserveAsEvents(flow = viewModel.event) { event ->\n" +
                        "        when (event) {\n" +
                        "            is ProductDetailScreenEvent.Error -> showToast(context, event.error)\n" +
                        "            is ProductDetailScreenEvent.NavigateToReviewWrite -> onNavigateToReviewWrite(event.product)\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    ProductDetailScreen(\n" +
                        "        state = state,\n" +
                        "        onTabClick = viewModel::changeTab,\n" +
                        "        // ✅ ViewModel 함수 대신 Throttled 리스너를 전달\n" +
                        "        onWriteReviewClick = throttledWriteReviewClick,\n" +
                        "        modifier = modifier\n" +
                        "    )\n" +
                        "}\n" +
                        "\n" +
                        "@Composable\n" +
                        "fun ProductDetailScreen(\n" +
                        "    state: ProductDetailScreenState,\n" +
                        "    onTabClick: (ProductDetailTab) -> Unit,\n" +
                        "    onWriteReviewClick: () -> Unit,\n" +
                        "    modifier: Modifier = Modifier,\n" +
                        ") {\n" +
                        "    LazyColumn(\n" +
                        "        modifier = modifier.fillMaxSize(),\n" +
                        "        contentPadding = PaddingValues(bottom = 70.dp)\n" +
                        "    ) {\n" +
                        "        item {\n" +
                        "            TopRoundedBackground {\n" +
                        "                state.product?.let { product ->\n" +
                        "                    ProductCardDetail(product = product)\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "\n" +
                        "        item {\n" +
                        "            TabSection(\n" +
                        "                selectedTab = state.selectedTab,\n" +
                        "                onTabClick = onTabClick\n" +
                        "            )\n" +
                        "        }\n" +
                        "\n" +
                        "        when (state.selectedTab) {\n" +
                        "            ProductDetailTab.REVIEW -> {\n" +
                        "                // ... (리뷰 리스트 부분은 동일)\n" +
                        "                items(state.reviewList.size) { index ->\n" +
                        "                    ReviewListItem(\n" +
                        "                        modifier = Modifier.padding(horizontal = 16.dp),\n" +
                        "                        review = state.reviewList[index]\n" +
                        "                    )\n" +
                        "                }\n" +
                        "                item {\n" +
                        "                    PyeonKingButton(\n" +
                        "                        text = stringResource(R.string.action_write_review),\n" +
                        "                        onClick = onWriteReviewClick, // ✅ 전달받은 Throttled 리스너 사용\n" +
                        "                        modifier = Modifier\n" +
                        "                            .fillMaxWidth()\n" +
                        "                            .padding(16.dp)\n" +
                        "                    )\n" +
                        "                }\n" +
                        "            }\n" +
                        "\n" +
                        "            ProductDetailTab.MAP -> {\n" +
                        "                item {\n" +
                        "                    MapTab()\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "\n" +
                        "@Preview(showBackground = true)\n" +
                        "@Composable\n" +
                        "private fun ProductDetailScreenPreview() {\n" +
                        "    PyeonKingTheme {\n" +
                        "        ProductDetailScreen(\n" +
                        "            state = ProductDetailScreenState(\n" +
                        "                product = mockProduct,\n" +
                        "                reviewList = mockReviews,\n" +
                        "                avgRating = 4.3,\n" +
                        "                reviewSum = 125,\n" +
                        "                ratingList = listOf(80, 20, 10, 5, 10)\n" +
                        "            ),\n" +
                        "            onTabClick = {},\n" +
                        "            onWriteReviewClick = {}\n" +
                        "        )\n" +
                        "    }\n" +
                        "}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}