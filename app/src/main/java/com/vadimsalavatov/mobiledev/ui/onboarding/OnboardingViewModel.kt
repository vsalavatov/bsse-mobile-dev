package com.vadimsalavatov.mobiledev.ui.onboarding

import android.os.SystemClock
import androidx.lifecycle.viewModelScope
import com.vadimsalavatov.mobiledev.data.persistent.LocalKeyValueStorage
import com.vadimsalavatov.mobiledev.di.AppCoroutineScope
import com.vadimsalavatov.mobiledev.di.IoCoroutineDispatcher
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val localKeyValueStorage: LocalKeyValueStorage,
    @AppCoroutineScope private val externalCoroutineScope: CoroutineScope,
    @IoCoroutineDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _videoSoundState: Deferred<MutableStateFlow<Boolean>> =
        externalCoroutineScope.async(context = ioDispatcher, start = CoroutineStart.LAZY) {
            MutableStateFlow(
                localKeyValueStorage.onboardingVideoSoundEnabled ?: false
            )
        }

    suspend fun videoSoundState(): StateFlow<Boolean> = _videoSoundState.await()

    fun toggleVideoSound() {
        viewModelScope.launch {
            val newState = _videoSoundState.await().value.xor(true)
            withContext(ioDispatcher) {
                localKeyValueStorage.onboardingVideoSoundEnabled = newState
            }
            _videoSoundState.await().emit(newState)
        }
    }

    private val _autoscrollFlow = MutableStateFlow<Long>(0)
    val autoscrollFlow = _autoscrollFlow.asStateFlow()
    private val IDLE_PERIOD: Long = 4000
    private var lastInteraction: Long = System.currentTimeMillis()

    fun registerInteraction(timestamp: Long) {
        lastInteraction = timestamp
    }

    suspend fun startAutoscrollNotifier() {
        while (true) {
            if (lastInteraction + IDLE_PERIOD < System.currentTimeMillis()) {
                lastInteraction = System.currentTimeMillis()
                _autoscrollFlow.emit(lastInteraction)
            }
            delay(50)
        }
    }
}