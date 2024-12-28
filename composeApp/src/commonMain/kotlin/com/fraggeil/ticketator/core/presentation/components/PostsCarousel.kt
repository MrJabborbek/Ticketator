package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.domain.model.Post
import kotlinx.coroutines.delay
import org.koin.compose.koinInject


@Composable
fun PostsCarousel(
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onClick: (Post) -> Unit,
) {
    val infiniteImages = remember(posts) { List(posts.size * 1000) { posts[it % posts.size] } }
    val pagerState = rememberPagerState(pageCount = {infiniteImages.size}, initialPage = posts.size)
    LaunchedEffect(pagerState, isLoading){
        snapshotFlow { pagerState.currentPage }
            .collect{
                if (pagerState.pageCount <= 0 || isLoading) return@collect
                delay(3000)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    animationSpec = spring(stiffness = 100f)
                )
            }
    }
    var width by remember { mutableStateOf(0) }

        HorizontalPager(
//            count = images.size,
            state = pagerState,
            userScrollEnabled = !isLoading,
            modifier = modifier
                .onGloballyPositioned {
                    width = it.size.width
                }
                .fillMaxWidth()
                .changePagerScrollStateByMouse(
                    pagerState = pagerState,
                    isLoading = isLoading
                )

//                .shimmerLoadingAnimation(
//                    modifier = Modifier
//                        .widthIn(max = Constants.MAX_ITEM_WIDTH_IN_DP.dp)
//                        .fillMaxWidth()
//                        .aspectRatio(695/295f),
//                    isLoadingCompleted = !isLoading,
//                    shimmerStyle = ShimmerStyle.Custom
//                ),
                    ,
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = (width*0.05/LocalDensity.current.density).dp),

        ) { page ->
                PostItem(
                    modifier = Modifier,
                    isLoading = isLoading,
                    imageUrl = infiniteImages[page].imageUrl,
                    onClick = { onClick(infiniteImages[page]) }
                )

        }

}

@Composable
private fun PostItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onClick: () -> Unit,
    isLoading: Boolean = false
){
    var imageState by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageState = if (size.height > 1 && size.width > 1){
                Result.success(it.painter)
            }else{
                Result.failure(IllegalArgumentException("Invalid image size"))
            }
        },
        onError = {
            imageState = Result.failure(it.result.throwable)
        },
        imageLoader = koinInject()
    )
    Image(
        painter = painter.takeIf { !isLoading} ?: rememberAsyncImagePainter(""),
        contentDescription = null,
        modifier = modifier
            .widthIn(max = Constants.MAX_ITEM_WIDTH_IN_DP.dp)
            .fillMaxWidth()
            .aspectRatio(695/295f)
            .clip(RoundedCornerShape(12.dp))
            then (
                if (isLoading) Modifier else Modifier.clickable { onClick() }
                )
            .shimmerLoadingAnimation(
                isLoadingCompleted = imageState?.isSuccess == true && !isLoading,
                shimmerStyle = ShimmerStyle.Custom
            ),
        contentScale = ContentScale.Crop
    )
}

//@Composable
//fun ImageCarousel(
//    images: List<String>, // List of image resources
//    modifier: Modifier = Modifier
//) {
//    val infiniteImages = remember(images) { List(images.size * 1000) { images[it % images.size] } }
//    val pagerState = rememberPagerState(pageCount = {infiniteImages.size}, initialPage = images.size)
//    LaunchedEffect(pagerState){
//        snapshotFlow { pagerState.currentPage }
//            .collect{
//                delay(3000)
//                pagerState.animateScrollToPage(
//                    page = (pagerState.currentPage + 1) % (pagerState.pageCount)
//                )
//            }
//    }
//
//    Column (
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        HorizontalPager(
////            count = images.size,
//            state = pagerState,
//            modifier = modifier.fillMaxWidth(),
//            pageSpacing = 16.dp,
//            contentPadding = PaddingValues(horizontal = 64.dp)
//        ) { page ->
//            Card(
//                modifier = Modifier
//                    .graphicsLayer {
//                        // Calculate scale based on page offset
////                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
////                        lerp(
////                            start = 0.85f,
////                            stop = 1f,
////                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
////                        ).also { scale ->
////                            scaleX = scale
////                            scaleY = scale
////                        }
//                    }
//                    .fillMaxWidth()
//                    .aspectRatio(1f),
//                shape = RoundedCornerShape(20.dp)
//            ) {
//                AsyncImage(
//                    model = infiniteImages[page],
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier =
//                    Modifier
//                        .fillMaxSize()
//                )
//            }
//        }
//
////        HorizontalPagerIndicator(
////            pagerState = pagerState,
////            modifier = Modifier
////                .padding(16.dp),
////            activeColor = Color.Blue,
////            inactiveColor = Color.LightGray
////        )
//    }
//}

//val testImages = listOf(
//    "https://picsum.photos/200/300",
//    "https://picsum.photos/400/500",
//    "https://picsum.photos/600/700",
//)