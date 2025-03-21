package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.UiType
import com.fraggeil.ticketator.core.domain.getUiType
import com.fraggeil.ticketator.core.domain.rememberScreenSizeInfo
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.components.autosizetext.AutoSizeText
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight
import com.fraggeil.ticketator.core.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T, S>SelectItemAndSubItemDialog(
    title: String,
    items: List<T>?,
    selectedItem: T?,
    selectedSubItem: S?,
    getSubItems: (T) -> List<S>?,
    itemToString: (T) -> String,
    subItemToString: (S) -> String,
    onSubItemSelected: (S) -> Unit,
    onItemSelected: (T) -> Unit,
    isLoading: Boolean = false,
    isVisible: MutableState<Boolean>
) {
    if (isVisible.value){
        val uiType = rememberScreenSizeInfo().getUiType()

        var selectedItemM by remember { mutableStateOf(selectedItem) }
        var isMainScreen by remember { mutableStateOf(selectedSubItem == null) }
        BasicAlertDialog(
            modifier = Modifier
                .padding(if (uiType != UiType.EXPANDED) Sizes.default_bottom_padding else Sizes.default_bottom_padding_double)
                .widthIn(max = Constants.MAX_ITEM_WIDTH_IN_DP)
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                )
                .background(color = White, shape = RoundedCornerShape(32.dp)),
//        containerColor = White,
//        onDismissRequest = {},
//        confirmButton = {  },
//        dismissButton =
////            if(isDismissButtonVisible) TextButton(onClick = onCancelled){ Text(text = dismissButtonText, color = LightTextColor) }
//                        },
//        title = { Text(text = title) },
            content = {
                Column(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, top = 32.dp)
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isMainScreen) {
                        Text(
                            modifier = Modifier.align(Alignment.Start),
                            text = title,
                            color = TextColor,
                            style = AppTypography().titleLarge
                        )
                        val state = rememberLazyGridState()
                        MyScrollableContentInvisibleBoundsBox(
                            size = Sizes.vertical_inner_padding,
                            left = false,
                        ){
                            LazyVerticalGrid(
                                modifier = Modifier.wrapContentWidth()
                                    .changeScrollStateByMouse(
                                        isVerticalScroll = true,
                                        scrollState = state
                                    ),
                                state = state,
                                contentPadding = PaddingValues(vertical = Sizes.vertical_inner_padding),
                                horizontalArrangement = Arrangement.spacedBy(Sizes.horizontal_inner_padding),
                                verticalArrangement = Arrangement.spacedBy(Sizes.vertical_inner_padding),
                                columns = GridCells.Fixed(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3)
                            ) {
                                if (isLoading) {
                                    items(8) {
                                        itemBox(
                                            text = "",
                                            onClick = {},
                                            isLoading = true,
                                            false,
                                            style = if (uiType == UiType.COMPACT) Style.Small else Style.Medium
                                        )
                                    }
                                }else{
                                    items?.forEach { item ->
                                        item {
                                            itemBox(
                                                itemToString(item),
                                                onClick = {
                                                    selectedItemM = item
                                                    isMainScreen = false
                                                },
                                                isLoading = isLoading,
                                                isSelected = selectedItemM == item,
                                                style = if (uiType == UiType.COMPACT) Style.Small else Style.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        IconButton(
                            modifier = Modifier.align(Alignment.Start),
                            onClick = {
                                isMainScreen = true
                            }
                        ){
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null,
                                tint = TextColor,
                            )
                        }
                        MyScrollableContentInvisibleBoundsBox(
                            size = Sizes.vertical_inner_padding,
                            left = false,
                        ){
                            val state = rememberLazyGridState()
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .changeScrollStateByMouse(
                                        isVerticalScroll = true,
                                        scrollState = state
                                    ),
                                state = state,
                                contentPadding = PaddingValues(vertical = Sizes.vertical_inner_padding),
                                horizontalArrangement = Arrangement.spacedBy(Sizes.horizontal_inner_padding),
                                verticalArrangement = Arrangement.spacedBy(Sizes.vertical_inner_padding),
                                columns = GridCells.Fixed(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3)
                            ) {
                                if (isLoading) {
                                    items(8) {
                                        itemBox(
                                            text = "",
                                            onClick = {},
                                            isLoading = true,
                                            false,
                                            style = if (uiType == UiType.COMPACT) Style.Small else Style.Medium

                                        )
                                    }
                                }else{
                                    if (selectedItemM != null){
                                        getSubItems(selectedItemM!!)?.forEach { item ->
                                            item {
                                                itemBox(
                                                    subItemToString(item),
                                                    onClick = {
                                                        onSubItemSelected(item)
                                                        onItemSelected(selectedItemM!!)
                                                        isVisible.value = false

                                                    },
                                                    isLoading = isLoading,
                                                    isSelected = selectedSubItem == item,
                                                    style = if (uiType == UiType.COMPACT) Style.Small else Style.Medium

                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }

//                // Month Grid
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    val rows = items!!.chunked(3)
//                    rows.forEach { row ->
//                        Row(
//                            horizontalArrangement = Arrangement.SpaceEvenly
//                        ) {
//                            row.forEach { item ->
//                                Box(
//                                    modifier = Modifier
//                                        .size(60.dp)
//                                        .clickable(
//                                            onClick = { selectedItemM = item }
//                                        )
//                                        .background(Color.Transparent),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    val animatedSize = if (true) animateDpAsState(
//                                        targetValue = if (selectedItemM == item) 60.dp else 0.dp,
//                                        animationSpec = tween(
//                                            durationMillis = 500,
//                                            easing = LinearOutSlowInEasing
//                                        )
//                                    ).value else 60.dp
//                                    Box(
//                                        modifier = Modifier
//                                            .size(animatedSize)
//                                            .background(
//                                                color = if (selectedItemM == item) Blue else Color.Transparent,
//                                                shape = CircleShape
//                                            )
//                                    )
//                                    Text(
//                                        maxLines = 1,
//                                        overflow = TextOverflow.Ellipsis,
//                                        text = itemToString(item).uppercase().take(3),
//                                        color = if (selectedItemM == item) White else Blue,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                }
//                            }
//                        }
//                        Spacer(modifier = Modifier.height(16.dp))
//                    }
//                }
                }


            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true, usePlatformDefaultWidth = false),
            onDismissRequest = {
                isVisible.value = false
            },
        )
    }
}

private enum class Style {Small, Medium}
@Composable
private fun itemBox(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    isSelected: Boolean,
    style: Style = Style.Medium,
    modifier: Modifier = Modifier.wrapContentSize()
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(when(style){
                Style.Small -> 60.dp //
                Style.Medium -> 60.dp
            })
            .clip(RoundedCornerShape(Sizes.smallRoundCorner))
            .then(
                if (isLoading) Modifier else Modifier.clickable { onClick() }
            )
            .background(
                color = if (isSelected) BG_White else White,
                RoundedCornerShape(Sizes.smallRoundCorner)
            )
            .border(
                width = 0.5.dp,
                color = if (isSelected) BlueDark else LightGray,
                shape = RoundedCornerShape(Sizes.smallRoundCorner)
            )
            .shimmerLoadingAnimation(isLoadingCompleted = !isLoading, shimmerStyle = ShimmerStyle.Custom)
            .padding(horizontal = Sizes.horizontal_inner_padding),
        contentAlignment = Alignment.CenterStart
    ) {
//        AutoResizedText(
//            text = text,
//            style = AppTypography().bodyLarge,
//            color = if (isSelected) BlueDark else TextColorLight
//        )
        AutoSizeText(
            text = text,
            style = AppTypography().bodyLarge,
            maxTextSize = AppTypography().bodyLarge.fontSize,
            color = if (isSelected) BlueDark else TextColorLight
        )
//        Text(
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//            text = text,
//            color = if (isSelected) BlueDark else TextColorLight,
//        )
    }
}