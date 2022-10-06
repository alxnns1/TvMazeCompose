package com.alxnns1.tvmazecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alxnns1.tvmazecompose.ui.MainLayout
import com.alxnns1.tvmazecompose.ui.theme.TvMazeComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvMazeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvMazeComposeTheme {
                MainLayout()
            }
        }
    }
}