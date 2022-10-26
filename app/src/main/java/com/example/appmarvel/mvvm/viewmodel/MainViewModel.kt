package com.example.appmarvel.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _listenerState: MutableLiveData<ListenerData> = MutableLiveData()

    val listenerState: LiveData<ListenerData>
        get() {
            return _listenerState
        }

    fun onGoToAppButtonPressed() {
        _listenerState.value = ListenerData(ListenerState.GO_TO_APP_PRESSED)
    }

    data class ListenerData(
        val lState: ListenerState
    )

    enum class ListenerState {
        GO_TO_APP_PRESSED
    }
}
