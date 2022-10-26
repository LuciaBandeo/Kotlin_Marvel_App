package com.example.appmarvel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appmarvel.R
import com.example.appmarvel.adapter.CharacterAdapter
import com.example.appmarvel.adapter.CharacterFragmentListener
import com.example.appmarvel.databinding.ActivityCharactersListBinding
import com.example.appmarvel.fragment.CharacterFragment
import com.example.appmarvel.mvvm.viewmodel.CharactersListViewModel
import com.example.appmarvel.mvvm.viewmodel.CharactersListViewModel.CharactersListState.FETCH_CHARACTERS
import com.example.appmarvel.mvvm.viewmodel.CharactersListViewModel.CharactersListState.ERROR_CHARACTERS_NOT_FOUND
import com.example.appmarvel.mvvm.viewmodel.CharactersListViewModel.CharactersListState.ERROR_OTHER
import com.example.appmarvel.mvvm.viewmodel.CharactersListViewModel.CharactersListState.FETCH_CHARACTER_DETAILS
import com.example.domain.entity.Character
import com.example.domain.utils.Constants.CHARACTER_FRAGMENT
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharactersListActivity : AppCompatActivity(), KoinComponent, CharacterFragmentListener {

    private val viewModel: CharactersListViewModel by inject()
    private lateinit var binding: ActivityCharactersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.fetchCharactersList()
        viewModel.charactersListState.observe({ lifecycle }, ::changeStateCharacterList)
    }

    private fun changeStateCharacterList(charactersListData: CharactersListViewModel.CharactersListData) {
        when (charactersListData.charactersState) {
            FETCH_CHARACTERS -> showCharactersList(charactersListData.data)
            ERROR_CHARACTERS_NOT_FOUND -> showErrorMessage(R.string.error_message_char_not_found)
            ERROR_OTHER -> showErrorMessage(R.string.error_message_not_exception)
            FETCH_CHARACTER_DETAILS -> showCharacterDetails(charactersListData.id)
        }
    }

    private fun showCharactersList(charactersList: List<Character>) {
        binding.errorFetchingCharactersTextView.visibility = View.GONE
        binding.recyclerViewCharactersList.adapter = CharacterAdapter(charactersList, this)
    }

    private fun showErrorMessage(errorId: Int) {
        binding.errorFetchingCharactersTextView.text = getString(errorId)
    }

    override fun setOnClickListener(id: String) {
        viewModel.onCharacterCardPressed(id)
    }

    private fun showCharacterDetails(id: String) {
        val characterFragment = CharacterFragment.newInstance(id)
        characterFragment.show(supportFragmentManager, CHARACTER_FRAGMENT)
    }

    companion object {
        fun getInstance(context: Context) = Intent(context, CharactersListActivity::class.java)
    }
}
