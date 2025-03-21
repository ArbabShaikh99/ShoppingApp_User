package com.example.shoppingapp_user.Domain.DI

//import com.example.shoppingapp_user.Data_layer.repoImpl.PushNotification
import android.content.Context
import com.example.shoppingapp_user.Data_layer.repoImpl.repoImpl
import com.example.shoppingapp_user.Domain.repository.repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.storage.Storage


@InstallIn(SingletonComponent::class)
@Module
object DomainModel {

    @Provides
    fun provideRepo(firestore : FirebaseFirestore, firebaseAuth :FirebaseAuth,
       firebaseStorage: FirebaseStorage ,
                    supabaseStorage : Storage,
                    context :Context
                    //firebaseMessage : FirebaseMessaging,
       //  pushNotification : PushNotification
    ): repo {
        return repoImpl(firestore,firebaseAuth , firebaseStorage  ,supabaseStorage,context )
        ///    , firebaseMessage , pushNotification

    }
}