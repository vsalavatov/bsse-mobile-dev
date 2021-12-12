package com.vadimsalavatov.mobiledev.di.module

import com.squareup.moshi.Moshi
import com.vadimsalavatov.mobiledev.data.json.AuthTokensAdapter
import com.vadimsalavatov.mobiledev.entity.AuthTokens
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializationModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(AuthTokens::class.java, AuthTokensAdapter().nullSafe())
            .build()
}