package com.ort.quotesappgrupo6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ort.quotesappgrupo6.navigation.NavGraph
import com.ort.quotesappgrupo6.ui.theme.QuotesAppGrupo6Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuotesAppGrupo6Theme {
                NavGraph()
            }
        }
    }
}