package com.example.shoppingapp_user.Data_layer.DI

import android.content.Context
import com.example.shoppingapp_user.Data_layer.repoImpl.PushNotification
//import com.example.shoppingapp_user.Data_layer.repoImpl.PushNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton
import com.example.shoppingapp_user.Common.SUPABASE_URL
import com.example.shoppingapp_user.Common.SUPABASE_KEY



@Module
@InstallIn(SingletonComponent::class)
object DataModle {

    @Provides
    fun provideFirebase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFireBaseStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideSupabaseClien(): SupabaseClient{
        val supabaseUrl = SUPABASE_URL
        val supabaseKey = SUPABASE_KEY

        return createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            // Install the Storage plugin
            install(Storage)
        }
    }

    @Provides
    @Singleton
    fun provideStorage(client: SupabaseClient): Storage{
        return client.storage
    }


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
//    @Provides
//    fun provideFireBaseMessage():FirebaseMessaging{
//        return FirebaseMessaging.getInstance()
//    }

    @Provides
    @Singleton
    fun providePushNotification(
        firebaseFireStore: FirebaseFirestore,
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth
    ): PushNotification {
        return PushNotification(firebaseFireStore,firebaseAuth,context)
    }

}