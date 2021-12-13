package com.vadimsalavatov.mobiledev.di.module

import com.vadimsalavatov.mobiledev.data.network.Api
import com.vadimsalavatov.mobiledev.data.persistent.LocalKeyValueStorage
import com.vadimsalavatov.mobiledev.di.AppCoroutineScope
import com.vadimsalavatov.mobiledev.di.IoCoroutineDispatcher
import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.repository.VerificationCodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        api: dagger.Lazy<Api>,
        localKeyValueStorage: LocalKeyValueStorage,
        @AppCoroutineScope externalCoroutineScope: CoroutineScope,
        @IoCoroutineDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthRepository =
        AuthRepository(api, localKeyValueStorage, externalCoroutineScope, ioDispatcher)

    @Provides
    @Singleton
    fun provideVerificationCodeRepository(api: dagger.Lazy<Api>): VerificationCodeRepository = VerificationCodeRepository(api)
}