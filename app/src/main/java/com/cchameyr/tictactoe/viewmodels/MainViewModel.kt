package com.cchameyr.tictactoe.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cchameyr.tictactoe.utils.GameState

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mPlayerTurn = -1
    private var mMoveCount = 0

    private var rowsContainer = mutableListOf(0, 0, 0)
    private var columnsContainer = mutableListOf(0, 0, 0)
    private var diagonalContainer = mutableListOf(0, 0, 0)
    private var opoDiagonalContainer = mutableListOf(0, 0, 0)


    val mapStatus: MutableLiveData<MutableMap<Int, Int>> by lazy {
        MutableLiveData<MutableMap<Int, Int>>()
    }

    fun getNewMap() {
        val map = mutableMapOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0,
            7 to 0,
            8 to 0,
            9 to 0,
        )
//        Log.d("TEST TIC TAC", map[5].toString())
        mMoveCount = 0
        rowsContainer = mutableListOf(0, 0, 0)
        columnsContainer = mutableListOf(0, 0, 0)
        mapStatus.postValue(map)
    }

    fun onClick(tag: Int) {
        mapStatus.value?.let {
            if (it[tag] != 0)
                return;

            it[tag] = mPlayerTurn

            mapStatus.postValue(it)
            insertMoveAndReturnGameState(tag)
            nextPlayer()
        }
    }

    private fun insertMoveAndReturnGameState(index: Int) {
        var row = -1
        var column = -1

        if (index < 4) {
            row = 0
            column = index - 1
        } else if (index < 7) {
            row = 1
            column = index - 4
        } else {
            row = 2
            column = index - 7
        }

        rowsContainer[row] += mPlayerTurn

        columnsContainer[column] += mPlayerTurn

        if (row == column)
            diagonalContainer[row] += mPlayerTurn

        if (row + column + 1 == 3) {
            opoDiagonalContainer[row] += mPlayerTurn
        }

        checkVictory(row, column)
    }

    private fun checkVictory(row: Int, column: Int) {

        if (rowsContainer[row] == 3)
            endGame(GameState.PLAYER_1_WIN)
        if (rowsContainer[row] == -3)
            endGame(GameState.PLAYER_0_WIN)
        if (columnsContainer[column] == 3)
            endGame(GameState.PLAYER_1_WIN)
        if (columnsContainer[column] == -3)
            endGame(GameState.PLAYER_0_WIN)

        if (diagonalContainer.sum() == 3)
            endGame(GameState.PLAYER_1_WIN)
        if (diagonalContainer.sum() == -3)
            endGame(GameState.PLAYER_0_WIN)

        if (opoDiagonalContainer.sum() == 3)
            endGame(GameState.PLAYER_1_WIN)
        if (opoDiagonalContainer.sum() == -3)
            endGame(GameState.PLAYER_0_WIN)

        if (mMoveCount == 9) {
            endGame(GameState.END_NO_WINNER)
        }
    }

    private fun endGame(state: GameState) {
        Log.d("TICTAC", "STATE GAME : $state")
    }

    fun nextPlayer() {
        mMoveCount++
        mPlayerTurn = if (mPlayerTurn == -1) 1 else -1
    }


}