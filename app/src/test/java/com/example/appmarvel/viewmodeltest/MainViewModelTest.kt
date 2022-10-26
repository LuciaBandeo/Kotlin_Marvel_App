package com.example.appmarvel.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appmarvel.mvvm.viewmodel.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun onGoToAppButtonPressedTest() {
        mainViewModel.onGoToAppButtonPressed()
        Assert.assertEquals(
            mainViewModel.listenerState.value?.lState,
            MainViewModel.ListenerState.GO_TO_APP_PRESSED
        )
    }
}
