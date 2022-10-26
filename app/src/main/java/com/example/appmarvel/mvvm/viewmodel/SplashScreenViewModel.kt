package com.example.appmarvel.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashScreenViewModel : ViewModel() {

    private var _animationState: MutableLiveData<SplashData> = MutableLiveData()

    val animationState: LiveData<SplashData>
        get() {
            return _animationState
        }

    fun startAnimation() {
        _animationState.value = SplashData(SplashState.START)
    }

    fun finishedAnimation() {
        _animationState.value = SplashData(SplashState.FINISH)
    }

    data class SplashData(
        val state: SplashState
    )

    enum class SplashState {
        START,
        FINISH
    }
}
