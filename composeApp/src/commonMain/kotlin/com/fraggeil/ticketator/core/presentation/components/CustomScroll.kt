package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.fraggeil.ticketator.core.domain.PlatformType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun Modifier.changeScrollStateByMouse(
    isVerticalScroll: Boolean,
    scrollState : ScrollableState,
    isMouseDragEnabled: Boolean = koinInject<PlatformType>() == PlatformType.DESKTOP,
    isLoading: Boolean = false,
    coroutineScope: CoroutineScope? = null
): Modifier{
    val scope = coroutineScope ?: rememberCoroutineScope()
    return this.then(
        if (isMouseDragEnabled && !isLoading){
            Modifier.pointerInput(Unit){
                detectDragGestures { _, dragAmount ->
                    scope.launch {
                        scrollState.scrollBy(
                            if (isVerticalScroll) -dragAmount.y else -dragAmount.x)
                    }
                }
            }
        }else{
            Modifier
        }
    )
}

@Composable
fun Modifier.changePagerScrollStateByMouse(
    pagerState: androidx.compose.foundation.pager.PagerState,
    isMouseDragEnabled: Boolean = koinInject<PlatformType>() == PlatformType.DESKTOP,
    coroutineScope: CoroutineScope? = null,
    isLoading: Boolean = false,
    isVerticalScroll: Boolean = false
): Modifier{
    var mDragAmount by remember { mutableStateOf<Float?>(null) }
    val scope = coroutineScope ?: rememberCoroutineScope()
    return this.then(
        if (isMouseDragEnabled && !isLoading){
            Modifier.pointerInput(Unit){
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        scope.launch {
                            mDragAmount = if (!isVerticalScroll) -dragAmount.x else -dragAmount.y
                            pagerState.scrollBy(mDragAmount!!)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            if (mDragAmount == null) return@launch
                            pagerState.animateScrollToPage(
                                page = if (mDragAmount!! < 0){
                                    if (pagerState.currentPageOffsetFraction < 0 ) pagerState.currentPage else pagerState.currentPage + 1
                                }else{
                                    if (pagerState.currentPageOffsetFraction > 0 ) pagerState.currentPage else pagerState.currentPage - 1
                                }
                            )
                            mDragAmount = null
                        }
                    }
                )
            }
        }else{
            Modifier
        }
    )
}