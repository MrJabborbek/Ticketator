package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NonLazyVerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (SpanScope.() -> Unit)
) {
    data class GridItem(val span: Int, val content: @Composable () -> Unit)
    
    val children = mutableListOf<GridItem>()
    val scope = object : SpanScope {
        override fun item(span: Int, content: @Composable () -> Unit) {
            children.add(GridItem(span, content))
        }

        override fun <T> items(
            items: List<T>,
            key: ((item: T) -> Any)?,
            span: Int?,
            contentType: (item: T) -> Any?,
            itemContent: @Composable (item: T) -> Unit
        ) {
            items.forEach { item ->
                item(span ?: 1) {
                    itemContent(item)
                }
            }
        }

        override fun items(
            count: Int,
            key: ((index: Int) -> Any)?,
            span: Int?,
            contentType: (index: Int) -> Any?,
            itemContent: @Composable (index: Int) -> Unit
        ) {
            repeat(count) { index ->
                item(span ?: 1) {
                    itemContent(index)
                }
            }
        }


    }
    content(scope)

    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = verticalArrangement
    ) {
        var rowIndex = 0
        while (rowIndex < children.size) {
            var currentSpan = 0
            val rowChildren = mutableListOf<GridItem>()
            
            while (rowIndex < children.size && currentSpan + children[rowIndex].span <= columns) {
                rowChildren.add(children[rowIndex])
                currentSpan += children[rowIndex].span
                rowIndex++
            }
            
            Row(horizontalArrangement = horizontalArrangement) {
                for (item in rowChildren) {
                    Box(modifier = Modifier.weight(item.span.toFloat(), fill = false)) {
                        item.content()
                    }
                }
                repeat(columns - currentSpan) { // Fill empty slots in the last row
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

interface SpanScope {
    fun item(span: Int, content: @Composable () -> Unit)
    fun <T> items(
        items: List<T>,
        key: ((item: T) -> Any)? = null,
        span: Int? = null,
        contentType: (item: T) -> Any? = { null },
        itemContent: @Composable (item: T) -> Unit
    )

    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        span: Int? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable (index: Int) -> Unit
    )
}
