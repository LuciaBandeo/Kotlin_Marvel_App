package com.example.appmarvel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmarvel.R
import com.example.appmarvel.databinding.CardViewCharacterBinding
import com.example.domain.entity.Character

interface CharacterFragmentListener {
    fun setOnClickListener(id: String)
}

class CharacterAdapter(
    private val charactersList: List<Character>,
    private val characterFragmentListener: CharacterFragmentListener
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_character, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(charactersList[position], characterFragmentListener)
    }

    override fun getItemCount(): Int = charactersList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CardViewCharacterBinding.bind(itemView)
        fun bind(item: Character, characterFragmentListener: CharacterFragmentListener) {
            item.let {
                binding.apply {
                    this.cardViewCharacter.setOnClickListener {
                        characterFragmentListener.setOnClickListener(
                            item.id
                        )
                    }
                    this.textViewCharacterName.text = item.name
                    Glide.with(itemView.context)
                        .load(item.imageURL)
                        .into(this.imageViewCharacterPicture)
                }
            }
        }
    }
}
