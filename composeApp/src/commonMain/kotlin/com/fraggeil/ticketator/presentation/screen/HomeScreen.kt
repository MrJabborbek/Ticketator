package com.fraggeil.ticketator.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.domain.FormattedDateStyle
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyButtonStyle
import com.fraggeil.ticketator.core.presentation.components.MyCircularButton
import com.fraggeil.ticketator.core.presentation.components.MyInfoButton
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.domain.model.FilterType
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.uzbekistan

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,

) {
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(BG_White)) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
                .background(color = BlueDark, shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 48.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.uzbekistan),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BlueDarkSecondary)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Sizes.horizontal_out_padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier.padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Column(
                   modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ){
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = White
                        )
                        Text(
                            text = Strings.Location.value(),
                            color = White,
                            style = AppTypography().bodyMedium,
                            maxLines = 1,
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
//                        text = "${state.currentLocation?.second?.name}, ${state.currentLocation?.first?.name}",
                        text = "Dhaka, Bangladesh",
                        color = White,
                        style = AppTypography().titleLarge.copy(fontWeight = FontWeight.Medium),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                MyCircularButton(
                    icon = painterResource(Res.drawable.uzbekistan),
                    onClick = {onAction(HomeAction.OnNotificationClicked)}
                )
            }
            Card(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors().copy(containerColor = White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = Sizes.cardElevation)
            ){
                Column(modifier = Modifier.padding(Sizes.horizontal_inner_padding).fillMaxWidth()){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        MyButton(
                            style = MyButtonStyle.SMALL,
                            text = "One-way",
                            onClick = { onAction(HomeAction.OnOneWayClicked) },
                            isSelected = state.filter.type == FilterType.ONE_WAY
                        )
                        MyButton(
                            style = MyButtonStyle.SMALL,
                            text = "Round trip",
                            onClick = { onAction(HomeAction.OnRoundTripClicked) },
                            isSelected = state.filter.type == FilterType.ROUND_TRIP
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = Sizes.vertical_inner_padding)
                            .fillMaxWidth()
                    ){
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Max)
                                    .clip(shape = RoundedCornerShape(Sizes.smallRoundCorner))
                                    .clickable {
                                    }
                                    .background(color = Blue.copy(alpha = 0.05f), shape = RoundedCornerShape(Sizes.smallRoundCorner))
                                    .padding(Sizes.horizontal_inner_padding),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = null,
                                        tint = Blue,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = state.filter.fromDistrict?.abbr?.uppercase() ?:"",
                                        style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.Bold),
                                        color = TextColor,
                                    )
                                }
                                VerticalDivider(modifier = Modifier.padding(horizontal = Sizes.horizontal_inner_padding), color = TextColorLight)
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ){
                                    Text(
                                        text = Strings.From.value(),
                                        style = AppTypography().bodyLarge,
                                        color = TextColorLight
                                    )
                                    Text(
                                        text = "${state.filter.fromDistrict?.name}, ${state.filter.fromRegion?.name}",
                                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Bold),
                                        color = TextColor,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Max)
                                    .clip(shape = RoundedCornerShape(Sizes.smallRoundCorner))
                                    .clickable {
                                    }
                                    .background(color = Blue.copy(alpha = 0.05f), shape = RoundedCornerShape(Sizes.smallRoundCorner))
                                    .padding(Sizes.horizontal_inner_padding),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null,
                                        tint = Blue,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = state.filter.toDistrict?.abbr?.uppercase() ?:"",
                                        style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.Bold),
                                        color = TextColor,
                                    )
                                }
                                VerticalDivider(modifier = Modifier.padding(horizontal = Sizes.horizontal_inner_padding), color = TextColorLight)
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ){
                                    Text(
                                        text = Strings.From.value(),
                                        style = AppTypography().bodyLarge,
                                        color = TextColorLight
                                    )
                                    Text(
                                        text = "${state.filter.toDistrict?.name}, ${state.filter.toRegion?.name}",
                                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Bold),
                                        color = TextColor,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                        IconButton(
                            modifier = Modifier
                                .padding(end = Sizes.horizontal_inner_padding)
                                .align(Alignment.CenterEnd)
                                .size(48.dp)
                                .background(
                                    color = Blue,
                                    shape = CircleShape
                                )
                            ,
                            onClick = {onAction(HomeAction.OnReverseDistrictsClicked)},
                        ){
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = White,


                            )
                        }

                    }
                    MyInfoButton(
                        modifier = Modifier.padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                        label = "Departure",
                        text = state.filter.dateGo?.toFormattedDate(style = FormattedDateStyle.Words) ?: "",
                        onClick = {}
                    )
                    if (state.filter.type == FilterType.ROUND_TRIP){
                        MyInfoButton(
                            modifier = Modifier.padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                            label = "Return",
                            text = state.filter.dateBack?.toFormattedDate(style = FormattedDateStyle.Words) ?: "",
                            onClick = {}
                        )
                    }
                    MyButton(
                        modifier = Modifier.padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                        text = "Search flights",
                        onClick = {onAction(HomeAction.OnSearchClicked)},
                        style = MyButtonStyle.FILLED
                    )

                }

            }
        }

    }
}