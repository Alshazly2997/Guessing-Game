package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guessinggame.databinding.FragmentGameBinding
import androidx.navigation.findNavController

class GameFragment : Fragment() {

    private var _binding : FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        updateScreen()

        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateScreen()
            if (viewModel.isWon() || viewModel.isLost()) {
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateScreen(){
        binding.word.text = viewModel.secretWord
        binding.lives.text = "You have ${viewModel.liveLeft} lives left."
        binding.incorrectGuesses.text = "Incorrect guesses: ${viewModel.incorrectGuesses}"
    }

}
