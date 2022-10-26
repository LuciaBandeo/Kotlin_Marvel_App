package com.example.appmarvel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmarvel.databinding.ActivityMainBinding
import com.example.appmarvel.mvvm.viewmodel.MainViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), KoinComponent {

    private val viewModel: MainViewModel by inject()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        viewModel.listenerState.observe({ lifecycle }, ::changeStateListener)
    }

    private fun changeStateListener(listenerData: MainViewModel.ListenerData) {
        when (listenerData.lState) {
            MainViewModel.ListenerState.GO_TO_APP_PRESSED -> {
                startActivity(CharactersListActivity.getInstance(this))
            }
        }
    }

    private fun setListeners() {
        binding.buttonMainActivity.setOnClickListener { viewModel.onGoToAppButtonPressed() }
    }

    companion object {
        fun getInstance(context: Context) = Intent(context, MainActivity::class.java)
    }
}
