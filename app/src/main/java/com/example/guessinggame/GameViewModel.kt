package com.example.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    val words = listOf("Android", "Activity", "Fragment")
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var liveLeft = 8

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    fun checkLetter(str: String) = when (correctGuesses.contains(str)){
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            correctGuesses += guess
            secretWordDisplay = deriveSecretWordDisplay()
        } else {
            incorrectGuesses += "$guess "
            liveLeft--
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay, true)

    fun isLost() = liveLeft <= 0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }
}