package com.example.appmarvel.activity

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.appmarvel.R
import com.example.appmarvel.databinding.ActivitySplashScreenBinding
import com.example.appmarvel.mvvm.viewmodel.SplashScreenViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashScreenActivity : AppCompatActivity(), KoinComponent {

    val viewModel: SplashScreenViewModel by inject()
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.animationState.observe({ lifecycle }, ::changeState)
        viewModel.startAnimation()
    }

    private fun changeState(splashData: SplashScreenViewModel.SplashData) {
        when (splashData.state) {
            SplashScreenViewModel.SplashState.START -> startAnimation()
            SplashScreenViewModel.SplashState.FINISH -> finishedAnimation()
        }
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation)
        binding.imageViewSplashActivity.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(anim: Animation?) {}
            override fun onAnimationEnd(anim: Animation?) {
                anim?.setAnimationListener(null)
                viewModel.finishedAnimation()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    private fun finishedAnimation() {
        startActivity(MainActivity.getInstance(this))
        finish()
    }
}
