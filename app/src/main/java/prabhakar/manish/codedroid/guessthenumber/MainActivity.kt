package prabhakar.manish.codedroid.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import prabhakar.manish.codedroid.guessthenumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NumberGuessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(NumberGuessViewModel::class.java)

        viewModel.attempts.observe(this, Observer { attempts ->
            binding.attemptsTextView.text = "Attempts: $attempts"
        })

        binding.submitButton.setOnClickListener {
            val guess = binding.guessEditText.text.toString().toIntOrNull()
            if (guess != null) {
                viewModel.checkGuess(guess)
            }
        }

        binding.restartButton.setOnClickListener {
            viewModel.generateRandomNumber()
            binding.guessEditText.text.clear()
            binding.submitButton.isEnabled = true
            binding.attemptsTextView.visibility = View.GONE
            binding.restartButton.visibility = View.GONE
            binding.timeTextView.visibility = View.GONE
        }

        viewModel.targetNumber.observe(this, Observer { target ->
            binding.instructionsTextView.text = "Guess the number between 1 and 100."
        })

        viewModel.guessResult.observe(this, Observer { result ->
            binding.resultTextView.text = result
            if (result.contains("Congratulations")) {
                binding.submitButton.isEnabled = false
                binding.restartButton.visibility = View.VISIBLE
                binding.attemptsTextView.visibility = View.VISIBLE

            }
        })

        viewModel.timeTaken.observe(this, Observer { time ->
            binding.timeTextView.text = "Time taken: $time seconds"
        })
    }
}
