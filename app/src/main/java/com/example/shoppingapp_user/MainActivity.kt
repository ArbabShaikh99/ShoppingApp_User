package com.example.shoppingapp_user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp_user.Presentation_layer.Navigation.BottomNavigation.BottomNavigation
import com.example.shoppingapp_user.Presentation_layer.Screens.HomePage
import com.example.shoppingapp_user.ui.theme.ShoppingApp_UserTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firbaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           // val  viewModel: AppViewModel = hiltViewModel()
            //val navController = rememberNavController()
            ShoppingApp_UserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //SignUpScreen()
//                    HomeScreen()
                  //  AppNavigation(firbaseAut)
                    BottomNavigation(firbaseAuth)
                 //32   ShippingDetailsScreen()
                }
            }
        }
    }
}

