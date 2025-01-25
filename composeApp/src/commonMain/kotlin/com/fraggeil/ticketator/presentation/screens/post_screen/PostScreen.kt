package com.fraggeil.ticketator.presentation.screens.post_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.presentation.Sizes.default_bottom_padding
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_inner_padding
import com.fraggeil.ticketator.core.presentation.StatusBarColors
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.TopBar2
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import org.koin.compose.koinInject

@Composable
fun PostScreenRoot(
    viewModel: PostViewModel,
    onBackClicked: () -> Unit
){
    StatusBarColors(isDarkText = true)
    val state by viewModel.state.collectAsStateWithLifecycle()
    PostScreen(
        state = state,
        onAction = {
            when(it){
                PostAction.OnBackClicked -> onBackClicked()
                else -> {}
            }
            viewModel.onAction(it)
        },
    )
}

@Composable
fun PostScreen(
    state: PostState,
    onAction: (PostAction) -> Unit,
){
    var imageState by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = state.post?.imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageState = if (size.height > 1 && size.width > 1){
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image size"))
            }
        },
        onError = {
            imageState = Result.failure(it.result.throwable)
        },
        imageLoader = koinInject()
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar2(
            text = "",
            isLeadingButtonVisible = true,
            onLeadingButtonClick = { onAction(PostAction.OnBackClicked) },
            isLightMode = true
        )
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontal_inner_padding)
                .changeScrollStateByMouse(
                    isVerticalScroll = true,
                    scrollState = scrollState,
                    isLoading = state.isLoading,
                )
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(shape = RoundedCornerShape(12.dp), color = LightGray)
            ) {
                Image(
                    painter = painter,
                    contentDescription = state.post?.title,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                        .shimmerLoadingAnimation(
                            modifier = Modifier
                                .widthIn(max = Constants.MAX_ITEM_WIDTH_IN_DP)
                                .fillMaxWidth()
                                .aspectRatio(695/295f),
                            isLoadingCompleted = imageState?.isSuccess == true && !state.isLoading,
                            shimmerStyle = ShimmerStyle.Custom
                        )
                )
            }

            Text(
                text = state.post?.title ?: "",
                modifier = Modifier.padding(top = 16.dp)
                    .shimmerLoadingAnimation(
                        isLoadingCompleted = !state.isLoading,
                        shimmerStyle = ShimmerStyle.TextTitle,
                        modifier = Modifier.width(150.dp)
                    ),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = TextColor,
            )
            Text(
                text = state.post?.date ?: "",
                modifier = Modifier.padding(top = 8.dp)
                    .shimmerLoadingAnimation(
                        isLoadingCompleted = !state.isLoading,
                        shimmerStyle = ShimmerStyle.TextSmallLabel,
                        modifier = Modifier.width(50.dp)
                    ),
                style = MaterialTheme.typography.titleSmall,
                color = TextColor,
            )
            Text(
                text = state.post?.body ?: "",
                modifier = Modifier.padding(top = 20.dp)
                    .shimmerLoadingAnimation(
                        isLoadingCompleted = !state.isLoading,
                        shimmerStyle = ShimmerStyle.TextBody,
                        linesCount = 10
                    ),
                style = MaterialTheme.typography.bodyLarge,
                color = TextColor,
            )
            Spacer(modifier = Modifier.height(default_bottom_padding))
        }
    }
}