package com.fraggeil.ticketator.presentation.screens.select_seat_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.AppTypography
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.seat_2

@Composable
fun Seats(
    modifier: Modifier = Modifier,
    selected: List<Int>,
    available: List<Int>,
    reserved: List<Int>,
    onClick: (Int) -> Unit,
    isHorizontal: Boolean = false
) {
    val items = (1..65).toList() // 5x13 = 65 items
    val listState = rememberLazyGridState()

    if (!isHorizontal){
        LazyVerticalGrid(
            columns = GridCells.Fixed(5), // 5 columns
            modifier = modifier.widthIn(max = 350.dp) .changeScrollStateByMouse(
                isVerticalScroll = true,
                scrollState = listState,
                isLoading = false
            ),
            state = listState,
            contentPadding = PaddingValues(16.dp),
//            modifier = modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(items) { item ->
                val seatNumber = calculateSeatNumber(item)
                Seat(
                    number = seatNumber,
                    status = when {
                        ((item-3) % 5 == 0 && item != 63) || item == 3 || item == 34 || item == 35 ->  SeatStatus.Hidden
                        reserved.contains(seatNumber) -> SeatStatus.Reserved
                        selected.contains(seatNumber) -> SeatStatus.Selected
                        available.contains(seatNumber) -> SeatStatus.Available
                        else -> SeatStatus.Unavailable
                    },
                    onClick = {
                        onClick(seatNumber)
                    }
                )
            }
            item(span = { GridItemSpan(5) }) {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextWithCount(
                        text = "Available",
                        count = available.size,
                        color = Color(0xFF72c16f)
                    )
                    TextWithCount(
                        text = "Selected",
                        count = selected.size,
                        color = Color(0xFF6ba3ed)
                    )
                }
            }
            item(span = { GridItemSpan(5) }) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextWithCount(
                        text = "Reserved",
                        count = reserved.size,
                        color = Color(0xFFe9b770)
                    )
                    TextWithCount(
                        text = "Unavailable",
                        count = 51 - available.size - reserved.size,
                        color = Color(0xFF818e98)
                    )
                }
            }
        }
    } else{
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            LazyHorizontalGrid(
                rows = GridCells.Fixed(5), // 5 columns
                modifier = modifier.heightIn(max = 350.dp) .changeScrollStateByMouse(
                    isVerticalScroll = false,
                    reverseScroll = true,
                    scrollState = listState,
                    isLoading = false
                ),
                state = listState,
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                reverseLayout = true
            ) {
                items(items) { item ->
                    val seatNumber = calculateSeatNumber(item)
                    Seat(
                        isHorizontal = true,
                        number = seatNumber,
                        status = when {
                            ((item-3) % 5 == 0 && item != 63) || item == 3 || item == 34 || item == 35 ->  SeatStatus.Hidden
                            reserved.contains(seatNumber) -> SeatStatus.Reserved
                            selected.contains(seatNumber) -> SeatStatus.Selected
                            available.contains(seatNumber) -> SeatStatus.Available
                            else -> SeatStatus.Unavailable
                        },
                        onClick = { onClick(seatNumber) }
                    )
                }
            }
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                TextWithCount(
                    text = "Reserved",
                    count = reserved.size,
                    color = Color(0xFFe9b770)
                )
                TextWithCount(
                    text = "Unavailable",
                    count = 51 - available.size - reserved.size,
                    color = Color(0xFF818e98)
                )
                TextWithCount(
                    text = "Available",
                    count = available.size,
                    color = Color(0xFF72c16f)
                )
                TextWithCount(
                    text = "Selected",
                    count = selected.size,
                    color = Color(0xFF6ba3ed)
                )
            }
        }
    }
}

private fun isHiddenSeat(item: Int): Boolean {
    // Logic for hidden seats
    return ((item - 3) % 5 == 0 && item != 63) || item == 3 || item == 34 || item == 35
}

private fun calculateSeatNumber(item: Int): Int {
    // Maps grid item index to a seat number, skipping hidden seats
    var seatNumber = 0
    for (i in 1..item) {
        if (!isHiddenSeat(i)) seatNumber++
    }
    return seatNumber
}

enum class SeatStatus{ Available, Unavailable, Reserved, Selected, Hidden }
@Composable
private fun Seat(
    modifier: Modifier = Modifier,
    number: Int,
    status: SeatStatus,
    onClick: () -> Unit,
    isHorizontal: Boolean = false,
){
    var height by remember { mutableStateOf(60) }
    val density =  LocalDensity.current.density
    Box(
        modifier = modifier.heightIn(max = (60*density).dp)
            .onGloballyPositioned {
                height = it.size.width
            }.scale(height/density/60f),
//            .background(Color.Red),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .then(
                    if (status ==  SeatStatus.Available || status == SeatStatus.Selected) Modifier.clickable(
                        onClick = onClick,
                        interactionSource = null,
                        indication = null
                    )  .pointerHoverIcon(
                        icon = PointerIcon.Hand,
                        true
                    )
                    else Modifier
                )
                .rotate(if (isHorizontal) 90f else 0f)
                .padding(4.dp),
            painter = painterResource(Res.drawable.seat_2),
            tint = when(status){
                SeatStatus.Available -> Color(0xFF72c16f)
                SeatStatus.Selected -> Color(0xFF6ba3ed)
                SeatStatus.Reserved -> Color(0xFFe9b770)
                SeatStatus.Unavailable -> Color(0xFF818e98)
                SeatStatus.Hidden -> Color.Transparent
            },
            contentDescription = null
        )
        Text(
            text = number.toString(),
            color = if (status == SeatStatus.Hidden) Color.Transparent else Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .then(if (isHorizontal) Modifier.padding(start = 8.dp) else Modifier.padding(bottom = 12.dp)),
            style =  AppTypography().titleLarge.copy(
                fontWeight = FontWeight.SemiBold),
//            fontSize = (22f/60*height).sp
        )
    }
}

@Composable
private fun TextWithCount(
    text: String,
    count: Int,
    modifier: Modifier = Modifier,
    color: Color
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
        Text(
            text = "$text $count",
            color = color,
            modifier = Modifier.padding(start = 8.dp),
            style = AppTypography().bodySmall.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}