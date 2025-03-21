package com.example.shoppingapp_user.Presentation_layer.Screens.Auth

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.shoppingapp_user.Presentation_layer.Screens.Auth.Component.RegisterAlertDialog
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.FaceBook_GoogleTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.MultiColorText
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.MyTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.MyTextField2
import com.example.shoppingapp_user.Presentation_layer.ViewModel.AuthViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor

@Composable
fun SignUpScreen(viewModel: AuthViewModel = hiltViewModel(),
  navController: NavController
  ) {

    val context = LocalContext.current
    val userRegisterResonseState = viewModel.userRegisterState.collectAsState()
    val userRegisterScreenState = viewModel.userRegisterScreenState.collectAsState().value

    val showAlertDialog = rememberSaveable {
        mutableStateOf(false)
    }
    if(showAlertDialog.value){
        RegisterAlertDialog(
            showDialog = showAlertDialog,
            onConfirm = {
                showAlertDialog.value =!showAlertDialog.value
                navController.navigate(Routes.HomeRoutes)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 28.dp, bottom = 70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Signup", fontSize = 34.sp,
                fontWeight = FontWeight.W600,
                color = Color.Black
            )

            Canvas(
                modifier = Modifier
                    .size(300.dp)
                    .offset(x = 70.dp, y = -100.dp)
            ) {
                drawCircle(color = darkPink)
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 230.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                MyTextField2(
                    value = userRegisterScreenState.firstName.value,
                    valueChange = { userRegisterScreenState.firstName.value = it },
                    placeHolderValue = "First Name"
                )
                Spacer(modifier = Modifier.width(20.dp))

                MyTextField2(
                    value = userRegisterScreenState.lastName.value,
                    valueChange = { userRegisterScreenState.lastName.value = it },
                    placeHolderValue = "Last Name"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            MyTextField(
                value = userRegisterScreenState.email.value,
                valueChange = { userRegisterScreenState.email.value = it },
                placeHolderValue = "Email"
            )
            Spacer(modifier = Modifier.height(10.dp))

            MyTextField(
                value = userRegisterScreenState.password.value,
                valueChange = { userRegisterScreenState.password.value = it },
                placeHolderValue = "Create Password"
            )
            Spacer(modifier = Modifier.height(10.dp))

            MyTextField(
                value = userRegisterScreenState.confirmPassword.value,
                valueChange = { userRegisterScreenState.confirmPassword.value = it },
                placeHolderValue = "Confirm Password"
            )


            Spacer(modifier = Modifier.height(26.dp))


            Button(
                onClick = {
                    if (userRegisterScreenState.firstName.value.isNotBlank() &&
                        userRegisterScreenState.lastName.value.isNotBlank() &&
                        userRegisterScreenState.email.value.isNotBlank() &&
                        userRegisterScreenState.password.value.isNotBlank() &&
                        userRegisterScreenState.confirmPassword.value.isNotBlank()
                    ) {
                        if (!viewModel.isValidEmail(userRegisterScreenState.email.value)) {
                            Toast.makeText(context, "Invalid email address!", Toast.LENGTH_SHORT).show()
                            return@Button // ❌ Invalid email ho to return kar dega
                        }

                        if (userRegisterScreenState.password.value.length < 6) {
                            Toast.makeText(
                                context, "Password must be at least 6 characters long",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        if (userRegisterScreenState.password.value != userRegisterScreenState.confirmPassword.value) {
                            Toast.makeText(
                                context, "Passwords do not match",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        viewModel.registerUser(
                            userItems = UserDataModel(
                                firstName = userRegisterScreenState.firstName.value,
                                lastName = userRegisterScreenState.lastName.value,
                                email = userRegisterScreenState.email.value,
                                password = userRegisterScreenState.password.value,
                                confirmPassword = userRegisterScreenState.confirmPassword.value,
                                phoneNumber = userRegisterScreenState.phoneNumber.value,
                                address = userRegisterScreenState.address.value
                            )
                        )

                    } else {
                        Toast.makeText(
                            context, "Please enter All Fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = darkPink),
                shape = RoundedCornerShape(17.dp),
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
            ) {
                Text("Signup", color = Color.White, fontSize = 16.sp)
            }


            Spacer(modifier = Modifier.height(20.dp))


            MultiColorText(
                "Already have an account? ",
                "Sign In",
                onClick = {
                   navController.navigate(Routes.loginScreenRoutes)
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

            Spacer(modifier = Modifier.height(17.dp))

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
                .offset(x = -70.dp, y = 790.dp)
        ) {
            drawCircle(color = darkPink)
        }
    }

    when {
        userRegisterResonseState.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = notificationColor, modifier = Modifier.size(45.dp))
            }
        }


        userRegisterResonseState.value.data.isNotEmpty() == true -> {
            LaunchedEffect(Unit) {
                showAlertDialog.value = true
            }
            Toast.makeText(context, userRegisterResonseState.value.data, Toast.LENGTH_SHORT).show()
            viewModel.resetauthState()
        }
    }
    LaunchedEffect(userRegisterResonseState.value.error) {
        if (userRegisterResonseState.value.error.isNotEmpty()) {
            Toast.makeText(
                context,
                "Something went wrong!\n${userRegisterResonseState.value.error}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}
