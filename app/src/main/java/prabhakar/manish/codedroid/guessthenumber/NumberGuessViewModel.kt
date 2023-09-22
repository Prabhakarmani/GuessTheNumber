package prabhakar.manish.codedroid.guessthenumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class NumberGuessViewModel : ViewModel() {

    private val _targetNumber = MutableLiveData<Int>()
    val targetNumber: LiveData<Int>
        get() = _targetNumber

    private val _guessResult = MutableLiveData<String>()
    val guessResult: LiveData<String>
        get() = _guessResult

    private val _timeTaken = MutableLiveData<Long>()
    val timeTaken: LiveData<Long>
        get() = _timeTaken

    private val _attempts = MutableLiveData<Int>()
    val attempts: LiveData<Int>
        get() = _attempts

    private var startTime: Long = 0
    private var numberOfAttempts: Int = 0

    init {
        generateRandomNumber()
    }

    fun generateRandomNumber() {
        _targetNumber.value = Random.nextInt(1, 101)
        startTime = System.currentTimeMillis()
        numberOfAttempts = 0
        _attempts.value = numberOfAttempts
    }

    fun checkGuess(guess: Int) {
        val target = _targetNumber.value ?: return
        val currentTime = System.currentTimeMillis()
        val timeElapsed = (currentTime - startTime) / 1000
        numberOfAttempts++

        if (guess < target) {
            _guessResult.value = "Number is bigger"
        } else if (guess > target) {
            _guessResult.value = "Number is smaller"
        } else {
            _guessResult.value =
                "Congratulations! You guessed it correctly in $timeElapsed seconds."
            _timeTaken.value = timeElapsed
            _attempts.value = numberOfAttempts
        }
    }
}
