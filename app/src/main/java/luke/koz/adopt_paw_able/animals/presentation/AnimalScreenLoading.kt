package luke.koz.adopt_paw_able.animals.presentation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.adopt_paw_able.R
import luke.koz.adopt_paw_able.ui.theme.AdoptpawableTheme

/**
 * The home screen displaying the loading message.
 */
@Composable
fun AnimalListLoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.contentdescription_animalloadingscreen_loading)
    )
}

@Preview
@Composable
private fun LoadingScreenPrev() {
    AdoptpawableTheme {
        AnimalListLoadingScreen()
    }
}