package com.yourcompany.bullseye.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.bullseye.R
import com.yourcompany.bullseye.data.repositories.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random

class GameScreenViewModel(
    private val repository: ScoreRepository
) : ViewModel() {
    private var _alertIsVisible = MutableStateFlow(false)
    val alertIsVisible: StateFlow<Boolean>
        get() = _alertIsVisible

    private var _sliderValue = MutableStateFlow(.5f)
    val sliderValue: StateFlow<Float>
        get() = _sliderValue

    private var _targetValue = MutableStateFlow(newTargetValue())
    val targetValue: StateFlow<Int>
        get() = _targetValue

    private var _totalScore = MutableStateFlow(0)
    val totalScore: StateFlow<Int>
        get() = _totalScore

    private var _currentRound = MutableStateFlow(1)
    val currentRound: StateFlow<Int>
        get() = _currentRound

    val sliderToInt: Int
        get() = (sliderValue.value * 100).toInt()

    private val differenceAmount: Int
        get() = abs(targetValue.value - sliderToInt)

    val alertTitle: Int
        get() =
            if (differenceAmount == 0) {
                R.string.alert_title_1
            } else if (differenceAmount < 5) {
                R.string.alert_title_2
            } else if (differenceAmount <= 10) {
                R.string.alert_title_3
            } else {
                R.string.alert_title_4
            }

    val pointsForCurrentRound: Int
        get() {
            val maxScore = 100
            var bonus = 0

            if (differenceAmount == 0) {
                bonus = 100
            } else if (differenceAmount == 1) {
                bonus = 50
            }

            return (maxScore - differenceAmount) + bonus
        }

    private fun newTargetValue() = Random.nextInt(1, 100)

    fun updateSliderValue(value: Float) {
        _sliderValue.value = value
    }

    fun hideDialog() {
        _alertIsVisible.value = false
    }

    fun startNewGame() {
        saveScore()
        resetGame()
    }

    private fun saveScore() {
        viewModelScope.launch(
            context = Dispatchers.IO
        ) {
            repository.saveScore(
                round = _currentRound.value,
                score = _totalScore.value
            )
        }
    }

    private fun resetGame() {
        _totalScore.value = 0
        _currentRound.value = 1
        _sliderValue.value = 0.5f
        _targetValue.value = newTargetValue()
    }

    fun onHit() {
        _alertIsVisible.value = true
        _totalScore.value += pointsForCurrentRound

        Log.i("ALERT VISIBLE?", alertIsVisible.toString())
    }

    fun onRoundIncrement() {
        _currentRound.value += 1
        _targetValue.value = newTargetValue()
    }

    private fun saveScoreFixed(round: Int, score: Int) {
        viewModelScope.launch(
            context = Dispatchers.IO
        ) {
            repository.saveScore(
                round = _currentRound.value,
                score = _totalScore.value
            )

        }
    }

    fun startNewGameFixed() {
        saveScoreFixed(round = _currentRound.value, score = _totalScore.value)
        resetGame()
    }
}