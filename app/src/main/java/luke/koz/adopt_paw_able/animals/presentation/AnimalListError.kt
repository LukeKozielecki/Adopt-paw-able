package luke.koz.adopt_paw_able.animals.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.adopt_paw_able.R
import luke.koz.adopt_paw_able.ui.theme.AdoptpawableTheme

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun AnimalListErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.content_description_animal_error_screen), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.content_description_animal_error_screen))
        }
    }
}
@Preview
@Composable
private fun ErrorScreenPrev() {
    AdoptpawableTheme {
        AnimalListErrorScreen(
            retryAction = {}
        )
    }
}