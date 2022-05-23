package com.cchameyr.tictactoe.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mPlayerTurn = 0

    val mapStatus: MutableLiveData<MutableMap<Int, Int>> by lazy {
        MutableLiveData<MutableMap<Int, Int>>()
    }

    fun getNewMap() {
        val map = mutableMapOf(
            1 to -1,
            2 to -1,
            3 to -1,
            4 to -1,
            5 to -1,
            6 to -1,
            7 to -1,
            8 to -1,
            9 to -1,
        )
//        Log.d("TEST TIC TAC", map[5].toString())
        mapStatus.postValue(map)
    }

    fun onClick(tag: Int) {
        mapStatus.value?.let {
            if (it[tag] != -1)
                return;

            it[tag] = mPlayerTurn

            mapStatus.postValue(it)
            nextPlayer()
        }
    }

    fun nextPlayer() {
        mPlayerTurn = if (mPlayerTurn == 0) 1 else 0
    }


}