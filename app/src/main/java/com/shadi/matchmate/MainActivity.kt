package com.shadi.matchmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadi.matchmate.presentation.allmatches.AllMatchesListingScreen
import com.shadi.matchmate.ui.theme.MatchMateTheme
import com.shadi.matchmate.ui.viewmodel.MatchMateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatchMateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onSurface
                ){
                NavGraphCustom()
                }
            }
        }
    }
}


@Composable
fun NavGraphCustom() {
    val navController = rememberNavController()
    val matchMateViewModel: MatchMateViewModel = hiltViewModel()
    NavHost (navController = navController, startDestination = "all_match_screen") {
        composable("all_match_screen") {
            AllMatchesListingScreen( navController)
        }
    }
}
