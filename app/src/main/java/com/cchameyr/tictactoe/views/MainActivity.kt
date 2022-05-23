package com.cchameyr.tictactoe.views

import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cchameyr.tictactoe.R
import com.cchameyr.tictactoe.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainViewModel

    lateinit var mCellMap: MutableMap<Int, View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        findView()
        initView()
        initObserver()
        mViewModel.getNewMap()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun findView() {
        mCellMap = mutableMapOf()
        mCellMap[1] = findViewById(R.id.cell_1)
        mCellMap[2] = findViewById(R.id.cell_2)
        mCellMap[3] = findViewById(R.id.cell_3)
        mCellMap[4] = findViewById(R.id.cell_4)
        mCellMap[5] = findViewById(R.id.cell_5)
        mCellMap[6] = findViewById(R.id.cell_6)
        mCellMap[7] = findViewById(R.id.cell_7)
        mCellMap[8] = findViewById(R.id.cell_8)
        mCellMap[9] = findViewById(R.id.cell_9)
    }

    private fun initView() {
        for (i in 1..9) {
            mCellMap[i]?.apply {
                tag = i
                setOnClickListener { v ->
                    mViewModel.onClick(v.tag as Int)
                }
            }
        }
    }

    private fun initObserver() {
        mViewModel.mapStatus.observe(this) {
            for (i in 1..9) {
                if (it[i] == 0) {
                    mCellMap[i]!!.apply {
                        findViewById<View>(R.id.cross).visibility = GONE
                        findViewById<View>(R.id.circle).visibility = GONE
                    }
                }
                if (it[i] == -1) {
                    mCellMap[i]!!.apply {
                        findViewById<View>(R.id.cross).visibility = VISIBLE
                        findViewById<View>(R.id.circle).visibility = GONE
                    }
                }
                if (it[i] == 1) {
                    mCellMap[i]!!.apply {
                        findViewById<View>(R.id.cross).visibility = GONE
                        findViewById<View>(R.id.circle).visibility = VISIBLE
                    }
                }
            }
        }
    }
}