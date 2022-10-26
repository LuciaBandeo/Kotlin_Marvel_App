package com.example.appmarvel.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appmarvel.mvvm.viewmodel.SplashScreenViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashScreenViewModelTest {

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        splashScreenViewModel = SplashScreenViewModel()
    }

    @Test
    fun startAnimationTest() {
        splashScreenViewModel.startAnimation()
        Assert.assertEquals(
            splashScreenViewModel.animationState.value?.state,
            SplashScreenViewModel.SplashState.START
        )
    }

    @Test
    fun finishedAnimationTest() {
        splashScreenViewModel.finishedAnimation()
        Assert.assertEquals(
            splashScreenViewModel.animationState.value?.state,
            SplashScreenViewModel.SplashState.FINISH
        )
    }
}
