//package com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component
//
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.animateFloat
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.animation.core.*
//
//
//@Composable
//fun ShimmerEffectHomeScreen() {
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        // Search Bar
//        ShimmerBox(height = 50.dp, width = 350.dp, shape = RoundedCornerShape(25.dp))
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Categories
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//            repeat(4) {
//                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
//                    ShimmerBox(size = 60.dp, shape = CircleShape) // Icon
//                    Spacer(modifier = Modifier.height(5.dp))
//                    ShimmerBox(height = 12.dp, width = 50.dp, shape = RoundedCornerShape(4.dp)) // Text
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Banner Section
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            repeat(3) {
//                ShimmerBox(height = 140.dp, width = 110.dp, shape = RoundedCornerShape(8.dp))
//            }
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Flash Sale Section
//        repeat(1) { // Adjust the number of rows
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                repeat(3) {
//                    Column {
//                        ShimmerBox(height = 140.dp, width = 110.dp, shape = RoundedCornerShape(8.dp)) // Product Image
//                        Spacer(modifier = Modifier.height(8.dp))
//                        ShimmerBox(height = 15.dp, width = 100.dp, shape = RoundedCornerShape(4.dp)) // Product Name
//                        Spacer(modifier = Modifier.height(4.dp))
//                        ShimmerBox(height = 12.dp, width = 50.dp, shape = RoundedCornerShape(4.dp)) // Price
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ShimmerBox(
//    height: androidx.compose.ui.unit.Dp,
//    width: androidx.compose.ui.unit.Dp,
//    shape:
//) {
//    val transition = rememberInfiniteTransition()
//    val shimmerColor = listOf(
//        Color.LightGray.copy(alpha = 0.9f),
//        Color.LightGray.copy(alpha = 0.3f),
//        Color.LightGray.copy(alpha = 0.9f)
//    )
//
//    val translateX = transition.animateFloat(
//        initialValue = -300f,
//        targetValue = 300f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(durationMillis = 1000, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        )
//    )
//
//    val shimmerBrush = Brush.linearGradient(
//        colors = shimmerColor,
//        start = Offset(translateX.value, 0f), // startX -> start
//        end = Offset(translateX.value + 300f, 0f) // endX -> end
//    )
//
//    Box(
//        modifier = Modifier
//            .width(width)
//            .height(height)
//            .background(shimmerBrush, shape = shape)
//    )
//}
//
