package com.example.appmarvel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.appmarvel.R
import com.example.appmarvel.databinding.FragmentCharacterBinding
import com.example.appmarvel.mvvm.viewmodel.CharacterFragmentViewModel
import com.example.domain.entity.Character
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class CharacterFragment : DialogFragment(), KoinComponent {

    private val viewModel: CharacterFragmentViewModel by inject()
    private lateinit var binding: FragmentCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCharacterBinding.inflate(layoutInflater)
        viewModel.charactersFragmentState.observe({ lifecycle }, ::changeStateCharacter)
        viewModel.fetchCharacterById(arguments?.getString(ID).orEmpty())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    private fun changeStateCharacter(characterFragmentData: CharacterFragmentViewModel.CharacterFragmentData) {
        when (characterFragmentData.characterFragmentState) {
            CharacterFragmentViewModel.CharacterFragmentState.SHOW_CHARACTER_DETAILS -> showCharacter(
                characterFragmentData.data
            )
            CharacterFragmentViewModel.CharacterFragmentState.ERROR_CHARACTER_NOT_FOUND -> showErrorMessage(
                R.string.error_message_character_not_found
            )
            CharacterFragmentViewModel.CharacterFragmentState.ERROR_SHOW_CHARACTER -> showErrorMessage(
                R.string.error_message_not_exception
            )
        }
    }

    private fun showCharacter(character: Character?) {
        character.let {
            binding.errorFetchingCharacterTextView.visibility = View.GONE
            binding.apply {
                this.fragmentCharacterId.text = it?.id
                this.fragmentCharacterName.text = it?.name
                if (it?.description.isNullOrEmpty()) {
                    this.fragmentCharacterDescription.text =
                        resources.getString(R.string.empty_description)
                } else {
                    this.fragmentCharacterDescription.text = it?.description
                }
                Glide.with(this@CharacterFragment)
                    .load(it?.imageURL)
                    .into(this.fragmentCharacterImage)
            }
        }
    }

    private fun showErrorMessage(errorId: Int) {
        binding.errorFetchingCharacterTextView.text = getString(errorId)
    }

    companion object {
        private const val ID = "CHARACTER_ID"
        fun newInstance(characterId: String): CharacterFragment {
            val args = Bundle()
            args.putString(ID, characterId)
            val fragment = CharacterFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
