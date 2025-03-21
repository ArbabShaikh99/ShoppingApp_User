package com.example.shoppingapp_user.Presentation_layer.Screens.Profile

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.SubNavigation
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.MyTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.MyTextField2
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProfileViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor
import com.example.shoppingapp_user.ui.theme.pink
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreenUI (
   profileViewModel: ProfileViewModel = hiltViewModel(),
   firebaseAuth : FirebaseAuth ,
   navController: NavController) {

   val getProfileResponseState = profileViewModel.getUserProfileState.collectAsState()
   val getProfileScreenState = profileViewModel.getProfileUserScreenState.collectAsState().value
   val profileImageResponseState = profileViewModel.insertProfileImageState.collectAsState()
   val context = LocalContext.current

    val updateUserDataResponseState = profileViewModel.updateUserDataState.collectAsState()
    val updateUserImageResponseState = profileViewModel.updateUserImageState.collectAsState()

   LaunchedEffect(Unit){
      profileViewModel.getProfileData(firebaseAuth.currentUser?.uid!!)
   }

   var isEditable by rememberSaveable { mutableStateOf(true) }
   val showDialog = remember { mutableStateOf(false) }

   val pickmedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri->

      if(uri != null){
       // profileViewModel.userProfileImage(uri)
         getProfileScreenState.userimageUri.value = uri
      }
   }

   // User Data
   when {
      getProfileResponseState.value.isloding -> {
         Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
         ) { CircularProgressIndicator(color = darkPink, modifier = Modifier.size(50.dp)) }
      }

      getProfileResponseState.value.error.isNotBlank() -> {
         Toast.makeText(context, getProfileResponseState.value.error, Toast.LENGTH_SHORT).show()
      }

      getProfileResponseState.value.data != null -> {

         LaunchedEffect(getProfileResponseState.value.data) {

            getProfileScreenState.firstname.value = getProfileResponseState.value.data!!.firstName
            getProfileScreenState.lastname.value = getProfileResponseState.value.data!!.lastName
            getProfileScreenState.email.value = getProfileResponseState.value.data!!.email
            getProfileScreenState.phoneNumber.value =
               getProfileResponseState.value.data!!.phoneNumber
            getProfileScreenState.address.value = getProfileResponseState.value.data!!.address
            getProfileScreenState.password.value = getProfileResponseState.value.data!!.password
            getProfileScreenState.confirmpassword.value = getProfileResponseState.value.data!!.confirmPassword
            getProfileScreenState.userimageUrl.value =
               getProfileResponseState.value.data!!.userImage

            Log.d("data ", "User ID :${getProfileResponseState.value.data!!.UserID}")
            Log.d("data ", "Items :${getProfileResponseState.value.data}")

         }


            Box(
               modifier = Modifier
                  .fillMaxSize()
                  .background(lightGray)
            ) {

               Canvas(
                     modifier = Modifier
                        .size(230.dp)
                        .offset(x = 190.dp, y = -100.dp)
                  ) {
                     drawCircle(color = darkPink)
                  }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 90.dp , start = 23.dp),
                    verticalArrangement = Arrangement.Top
                ) {
               Box(
                  modifier = Modifier.size(120.dp)
               ) {
                  SubcomposeAsyncImage(
                     model = getProfileScreenState.userimageUrl.value.ifEmpty {
                        if (getProfileScreenState.userimageUri.value != null) {
                           getProfileScreenState.userimageUri.value
                        } else {
                             R.drawable.userlocalprofile
                        }
                     },
                     contentDescription = null,
                     contentScale = ContentScale.Crop,
                     modifier = Modifier
                        .size(120.dp)
                        .shadow(
                           elevation = 1.dp,
                           shape = CircleShape
                        ).clickable(
                           interactionSource = remember {
                              MutableInteractionSource()
                           },
                           indication = null
                        ) {
                           if (!isEditable) {
                              pickmedia.launch(
                                 PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                              )
                           }
                        },
                     loading = {
                        CircularProgressIndicator(
                           color = darkPink,
                        )
                     }
                  )
                  if(!isEditable) {
                     Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).align(
                           Alignment.BottomEnd
                        ).shadow(
                           elevation = 1.dp,
                           shape = CircleShape
                        ).background(darkPink), tint = lightGray
                     )
                  }
               }




                    Spacer(modifier = Modifier.height(20.dp))
                  Row(
                     modifier = Modifier
                  ) {

                     MyTextField2(
                        value = getProfileScreenState.firstname.value,
                        valueChange = { getProfileScreenState.firstname.value = it },
                        placeHolderValue = "First Name",
                        readOnly = isEditable
                     )
                     Spacer(modifier = Modifier.width(20.dp))

                     MyTextField2(
                        value = getProfileScreenState.lastname.value,
                        valueChange = { getProfileScreenState.lastname.value = it },
                        placeHolderValue = "Last Name",
                        readOnly = isEditable
                     )
                  }

                  Spacer(modifier = Modifier.height(14.dp))


                    MyTextField(
                     value = getProfileScreenState.email.value,
                     valueChange = { getProfileScreenState.email.value = it },
                     placeHolderValue = "Email",
                     readOnly = isEditable
                  )
                  Spacer(modifier = Modifier.height(14.dp))

                    MyTextField(
                     value = getProfileScreenState.phoneNumber.value,
                     valueChange = { getProfileScreenState.phoneNumber.value = it },
                     placeHolderValue = "Contact Number",
                     readOnly = isEditable,
                     keyboardType = KeyboardType.Number
                  )
                  Spacer(modifier = Modifier.height(14.dp))

                    MyTextField(
                     value = getProfileScreenState.address.value,
                     valueChange = { getProfileScreenState.address.value = it },
                     placeHolderValue = "Address",
                  readOnly = isEditable
                  )
                  Spacer(modifier = Modifier.height(17.dp))


                   // logout
                  Button(
                     onClick = {
                    showDialog.value = true
                     },
                     colors = ButtonDefaults.buttonColors(containerColor = darkPink),
                     shape = RoundedCornerShape(17.dp),
                     modifier = Modifier
                        .width(320.dp)
                        .height(50.dp)
                  ) {
                     Text("Log Out", color = Color.White, fontSize = 16.sp)
                  }
                    if(showDialog.value){
                       LogOutAlertDialog(
                          onDismiss = {
                             showDialog.value = false
                          },
                          onConfirm = {
                             firebaseAuth.signOut()
                             navController.navigate(SubNavigation.LoginSignRoutes)
                          },
                          ProfileImage = getProfileScreenState.userimageUrl.value
                       )
                    }

                  Spacer(modifier = Modifier.height(20.dp))

                   // Edit
                  Button(
                     onClick = {

                        isEditable = !isEditable
                        if (isEditable) {
                           if (getProfileScreenState.userimageUri.value != null) {
                              profileViewModel.userProfileImage(
                                 imageUri = getProfileScreenState.userimageUri.value!!
                              )
                           } else {
                              profileViewModel.updateUserData(
                                 userData = UserDataModel(
                                    firstName = getProfileScreenState.firstname.value,
                                    lastName = getProfileScreenState.lastname.value,
                                    email = getProfileScreenState.email.value,
                                    phoneNumber = getProfileScreenState.phoneNumber.value,
                                    userImage = getProfileScreenState.userimageUrl.value,
                                    address = getProfileScreenState.address.value,
                                    UserID = firebaseAuth.currentUser!!.uid,
                                    password = getProfileScreenState.password.value,
                                    confirmPassword = getProfileScreenState.confirmpassword.value
                                 )
                              )
                           }
                        }

                     },
                     colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                     shape = RoundedCornerShape(17.dp),
                     modifier = Modifier
                        .width(320.dp)
                        .height(50.dp)
                        .border(2.dp, darkPink, RoundedCornerShape(17.dp))
                  ) {
                     Text(if (!isEditable) "Save Profile" else "Edit Profile", color = Color.Black, fontSize = 16.sp)
                  }

               }
               // Bottom-Left Circle
               Canvas(
                  modifier = Modifier
                     .size(250.dp)
                     .offset(x = -60.dp, y = 640.dp)
               ) {
                  drawCircle(color = Color(0xFFFF6F6F))
               }
            }
      }
   }


   when{
      updateUserDataResponseState.value.isloding -> {
         Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
         { CircularProgressIndicator(color = darkPink) }
      }

      updateUserDataResponseState.value.error.isNotBlank() -> {
         Toast.makeText(context, updateUserDataResponseState.value.error, Toast.LENGTH_SHORT).show()
      }

      updateUserDataResponseState.value.data != null -> {

         profileViewModel.clearUpdateProfileDataState()
         Toast.makeText(context, updateUserDataResponseState.value.data, Toast.LENGTH_SHORT).show()
      }
   }
   when{
      profileImageResponseState.value.isLoading ->{
         Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
         { CircularProgressIndicator(color = darkPink) }
      }
      profileImageResponseState.value.error.isNotBlank() ->{
         Toast.makeText(context, profileImageResponseState.value.error, Toast.LENGTH_SHORT).show()
      }
      profileImageResponseState.value.data.isNotEmpty() ->{
         LaunchedEffect(Unit) {
            getProfileScreenState.userimageUrl.value = profileImageResponseState.value.data!!

            val userDataModel = UserDataModel(
               firstName = getProfileScreenState.firstname.value,
               lastName = getProfileScreenState.lastname.value,
               email = getProfileScreenState.email.value,
              phoneNumber = getProfileScreenState.phoneNumber.value,
               address = getProfileScreenState.address.value,
               userImage = getProfileScreenState.userimageUrl.value,
               UserID = firebaseAuth.currentUser!!.uid,
               password = getProfileScreenState.password.value,
               confirmPassword = getProfileScreenState.confirmpassword.value
            )
            profileViewModel.updateUserData(userDataModel)
         }
      }
   }
   }


