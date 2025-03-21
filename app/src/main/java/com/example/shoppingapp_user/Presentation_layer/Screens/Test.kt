package com.example.shoppingapp_user.Presentation_layer.Screens

import androidx.compose.ui.text.font.FontWeight
import com.example.shoppingapp_user.R


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "My Shopping App", fontSize = 24.sp, color = Color.White)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = "Home", color = Color.White)
            Text(text = "Products", color = Color.White)
            Text(text = "About", color = Color.White)
            Text(text = "Contact", color = Color.White)
        }
    }
}

@Composable
fun HeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.LightGray) // Replace with actual image
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to My Shopping App", fontSize = 24.sp, color = Color.Black)
            Text(text = "Explore the best products and services.", fontSize = 18.sp, color = Color.Black)
            Button(onClick = { /* Do something */ }) {
                Text(text = "Shop Now")
            }
        }
    }
}

@Composable
fun ProductGrid() {
    val products = listOf(
        Productt("Product 1", "Description of Product 1", R.drawable.sale1),
        Productt("Product 2", "Description of Product 2", R.drawable.sale3),
        // Add more products as needed
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(products) { product ->
            ProductCard(product)
        }
    }
}

@Composable
fun ProductCard(product: Productt) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Do something */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = product.description, fontSize = 14.sp, textAlign = TextAlign.Justify)
            Button(onClick = { /* Do something */ }) {
                Text(text = "Add to Cart")
            }
        }
    }
}

data class Productt(val name: String, val description: String, val imageRes: Int)

@Composable
fun Footer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "© 2023 My Shopping App. All rights reserved.", color = Color.White)
    }
}

@Composable
fun HomePage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Header()
        HeroSection()
        ProductGrid()
        Footer()
    }
}

