package com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.darkPink
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@Composable
@Preview
fun Banner_PagerSlider() {
    val sliderList = listOf(
        R.drawable.sale1,
        R.drawable.sale7,
        R.drawable.sale5,
    )

    val pagerState = rememberPagerState(pageCount = {
        sliderList.size // 🔹 Dynamically setting page count
    })

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            if (sliderList.isNotEmpty()) { // 🔹 Show only if list is not empty
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(180.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) { page ->
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                val pagerOffset = ((pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction).absoluteValue
                                lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pagerOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                                alpha = lerp(
                                    start = 0.50f,
                                    stop = 1f,
                                    fraction = 1f - pagerOffset.coerceIn(0f, 1f)
                                )
                            },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = sliderList[page]),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(6.dp))

        if (sliderList.isNotEmpty()) { // 🔹 Show indicators only if list is not empty
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) darkPink else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}



//@Composable
//@Preview
//fun Banner_PagerSlider() {
//    val pagerState = rememberPagerState(pageCount = {
//        3
//    })
//
//    val sliderList = listOf(
//        R.drawable.sale1,
//        R.drawable.sale2,
//        R.drawable.sale3,
//    )
//    LaunchedEffect(key1 = Unit) {
//        while (true) {
//            yield()
//            delay(2000)
//            pagerState.animateScrollToPage(
//                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
//                animationSpec = tween(600)
//            )
//        }
//    }
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//    ) {
//        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier
//                    .height(180.dp)
//                    .padding(start = 8.dp , end = 8.dp)
//                    //.fillMaxWidth()
//
//            ) { page ->
//                Card(
//                    modifier = Modifier
//                        .graphicsLayer {
//                            val pagerOffset = ((pagerState.currentPage - page) + pagerState
//                                .currentPageOffsetFraction
//                                    ).absoluteValue
//                            lerp(
//                                start = 0.5f,
//                                stop = 1f,
//                                fraction = 1f - pagerOffset.coerceIn(0f, 1f)
//                            ).also { scale ->
//                                scaleX = scale
//                                scaleY = scale
//                            }
//                            alpha = lerp(
//                                start = 0.50f,
//                                stop = 1f,
//                                fraction = 1f - pagerOffset.coerceIn(0f, 1f)
//                            )
//                        },
//                    shape = RoundedCornerShape(10.dp)
//                ) {
//                    Image(
//                        painter = painterResource(id = sliderList[page]),
//                        contentDescription = "",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//            }
//        }
//        Spacer(Modifier.height(6.dp))

//        Row(
//            Modifier
//                .wrapContentHeight()
//                .fillMaxWidth()
//                .align(Alignment.CenterHorizontally)
//                .padding(bottom = 8.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            repeat(pagerState.pageCount) { iteration ->
//                val color = if (pagerState.currentPage == iteration) darkPink else Color.LightGray
//                Box(
//                    modifier = Modifier
//                        .padding(2.dp)
//                        .clip(CircleShape)
//                        .background(color)
//                        .size(8.dp)
//                )
//            }
//        }
//    }
//}