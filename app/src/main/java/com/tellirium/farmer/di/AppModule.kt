package com.tellirium.farmer.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tellirium.farmer.api.FarmerApi
import com.tellirium.farmer.api.FarmerApiHelper
import com.tellirium.farmer.api.FarmerApiHelperImpl
import com.tellirium.farmer.db.FarmerDatabase
import com.tellirium.farmer.util.Constants
import com.tellirium.farmer.util.Constants.BASE_URL
import com.tellirium.farmer.util.Constants.FARMER_DATABASE_NAME
import com.tellirium.farmer.util.Constants.KEY_FIRST_TIME
import com.tellirium.farmer.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FarmerDatabase::class.java,
        FARMER_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideFarmerDao(db: FarmerDatabase) = db.getFarmerDao()


    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BaseURL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BaseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(FarmerApi::class.java)

    @Provides
    @Singleton
    fun provideApiServiceHelper(apiHelper: FarmerApiHelperImpl):
            FarmerApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME, true)
}