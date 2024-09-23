package luke.koz.adopt_paw_able.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import luke.koz.adopt_paw_able.ui.theme.AdoptpawableTheme

@Composable
fun NavHubScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreen//
    ) {
        composable<AuthScreen> {
            //[AuthScreen()]
        }
        composable<HomeScreen> {
            //[HomeScreen]
        }
        composable<AnimalsListing>{
            //[AnimalListing]
        }
    }
}

@Preview
@Composable
private fun NavHubPreview() {
    AdoptpawableTheme {
        NavHubScreen()
    }
}