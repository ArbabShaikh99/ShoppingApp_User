package com.example.shoppingapp_user.Presentation_layer.Screens.Auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.FaceBook_GoogleTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.MultiColorText
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.MyTextField
import com.example.shoppingapp_user.Presentation_layer.ViewModel.AuthViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor

@Composable
fun LoginScreen(viewModel: AuthViewModel = hiltViewModel(),
 navController: NavController
                ) {

    val context = LocalContext.current
    val loginResponseState = viewModel.loginUserState.collectAsState()
    val loginScreenState = viewModel.userLoginScreenState.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 28.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Login", fontSize = 34.sp,
                fontWeight = FontWeight.W600,
                color = Color.Black
            )

            Canvas(
                modifier = Modifier
                    .size(300.dp)
                    .offset(x = 70.dp, y = -90.dp)
            ) {
                drawCircle(color = darkPink)
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 215.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            MyTextField(
                value = loginScreenState.value.email.value,
                valueChange = { loginScreenState.value.email.value = it },
                placeHolderValue = "Email"
            )


            Spacer(modifier = Modifier.height(24.dp))

            // Password Input
            MyTextField(
                value = loginScreenState.value.password.value,
                valueChange = { loginScreenState.value.password.value = it },
                placeHolderValue = "Password"
            )

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "Forgot Password?",
                modifier = Modifier.padding(start = 100.dp),
                fontSize = 15.sp,
                color = customGray,
                fontWeight = FontWeight.W400
            )

            Spacer(modifier = Modifier.height(26.dp))


            Button(
                onClick = {
                    if (loginScreenState.value.email.value.isNotBlank() &&
                        loginScreenState.value.password.value.isNotBlank()) {

                        viewModel.loginUser(
                            userItems = UserDataModel(
                            email = loginScreenState.value.email.value,
                            password = loginScreenState.value.password.value
                        )
                        )
                    } else {
                        Toast.makeText(
                            context, "Please enter All Fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                colors = ButtonDefaults.buttonColors(containerColor = darkPink), // ✅ Corrected
                shape = RoundedCornerShape(17.dp), // ✅ Corners rounded
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)

            ) {
                Text("Login", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))


            MultiColorText(
                "Don't have an account? ",
                "Sign Up",
                onClick = {
                     navController.navigate(Routes.SignUpScreenRoutes)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))



            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Divider(
                    color = Color.Gray,
                    modifier = Modifier.weight(1f) // ✅ Left side divider
                )

                Text(
                    text = "OR",
                    color = Color.Black, // ✅ Text color
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp) // ✅ Text aur dividers ke beech space
                )

                Divider(
                    color = Color.Gray,
                    modifier = Modifier.weight(1f) // ✅ Right side divider
                )
            }


//
            Spacer(modifier = Modifier.height(20.dp))
//
            FaceBook_GoogleTextField(
                icon = R.drawable.facebook,
                placHolderValue = "Log in with Facebook",
                onClick = {

                }
            )

            Spacer(modifier = Modifier.height(23.dp))

            FaceBook_GoogleTextField(
                icon = R.drawable.google,
                placHolderValue = "Log in with Google",
                onClick = {

                }
            )

        }
        // Bottom-Left Circle
        Canvas(
            modifier = Modifier
                .size(250.dp)
                .offset(x = -60.dp, y = 750.dp)
        ) {
            drawCircle(color = Color(0xFFFF6F6F))
        }
    }

    when {
        loginResponseState.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = notificationColor, modifier = Modifier.size(45.dp))
            }
        }

        loginResponseState.value.error.isNotEmpty() -> {
            Log.d("LoginDebug", "Error: ${loginResponseState.value.error}")  // ✅ Debugging Error
            Toast.makeText(context,"Some thing went wrong!"
                , Toast.LENGTH_SHORT).show()
        }
        loginResponseState.value.data?.isNotEmpty() == true->{

          navController.navigate(Routes.HomeRoutes)
            Toast.makeText(context, loginResponseState.value.data, Toast.LENGTH_SHORT).show()
            viewModel.resetauthState()

        }

    }

}


