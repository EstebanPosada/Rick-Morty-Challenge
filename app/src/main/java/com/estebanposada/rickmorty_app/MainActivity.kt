package com.estebanposada.rickmorty_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.estebanposada.rickmorty_app.ui.screens.list.CharacterListScreenRoot
import com.estebanposada.rickmorty_app.ui.theme.RickMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            RickMortyTheme {
                Scaffold(
                    topBar = { CenterAlignedTopAppBar(title = { Text(text = "Rick & Morty") }) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = CharactersScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<CharactersScreen> {
                            CharacterListScreenRoot(onItemClick = {
                                navController.navigate(DetailCharacterScreen(it))
                            })
                        }
                    }
                }
            }
        }
    }
}
