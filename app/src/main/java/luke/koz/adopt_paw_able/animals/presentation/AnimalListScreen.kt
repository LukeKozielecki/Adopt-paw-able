package luke.koz.adopt_paw_able.animals.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import luke.koz.adopt_paw_able.R
import luke.koz.adopt_paw_able.animals.domain.model.AnimalEntry
import luke.koz.adopt_paw_able.animals.domain.viewmodel.AnimalListState
import luke.koz.adopt_paw_able.animals.domain.viewmodel.AnimalsViewModel
import luke.koz.adopt_paw_able.utils.DecodedAnimalInfo

@Composable
fun AnimalListCall(modifier: Modifier = Modifier) {
    val animalsViewModel: AnimalsViewModel = viewModel(factory = AnimalsViewModel.Factory)
    AnimalListScreen(
        uiState = animalsViewModel.animalsListUiState,
        retryAction = { /*todo retry action*/ },
        viewModel = animalsViewModel,
        modifier = modifier
    )
}
@Composable
fun AnimalListScreen(
    uiState: AnimalListState,
    viewModel: AnimalsViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is AnimalListState.Loading -> {
            AnimalListLoadingScreen(modifier = modifier.fillMaxSize())
        }
        is AnimalListState.Success -> {
            AnimalListSuccessScreen(
                modifier = modifier.fillMaxSize(),
                filteredData = viewModel.animals,
                decodingFunction = viewModel::decodeAnimalResponse
            )
        }
        is AnimalListState.Error -> {
            AnimalListErrorScreen(
                retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun AnimalListSuccessScreen(modifier: Modifier = Modifier, filteredData: List<AnimalEntry>, decodingFunction : (AnimalEntry) -> DecodedAnimalInfo) {
//    val selectedAnimalEntry by animalsViewModel..collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(1), //todo move this to Viewmodel. connect to adaptive layout after it is implemented
        modifier = modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),//this stopped being top element so no longer requires [contentPadding] here
    ) {
        items(
            items = filteredData,
            key = { item -> item.id }) {
            AnimalCard(
                animalEntry = it,
                modifier = modifier
                    .padding(4.dp)
                    .size(124.dp),
                onEntryClick = {/*todo handle navigation too selected animal*/},
                decodingFunction = decodingFunction
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalCard(
    modifier: Modifier = Modifier,
    animalEntry: AnimalEntry,
    onEntryClick: () -> Unit,
    decodingFunction: (AnimalEntry) -> DecodedAnimalInfo
) {
    Card(
        modifier = modifier.padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {onEntryClick()}
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(animalEntry.photoURL)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_launcher_foreground),
                placeholder = painterResource(R.drawable.loading_img),//todo the size of this should be defined and static
                contentDescription = stringResource(R.string.animal_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(4.dp)
                    .size(124.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column (modifier = Modifier.padding(all = 16.dp)) {
                //todo create custom function to decode this integers to preset categories
                Text(text = decodingFunction(animalEntry).category)
                //Text(text = animalEntry.category.toString().uppercase(), style = MaterialTheme.typography.bodyLarge)
                Text(text = "Id: " + animalEntry.id.toString(), style = MaterialTheme.typography.bodyMedium)
                Text(text = decodingFunction(animalEntry).breed ?: "Unknown Breed")
            }
        }
    }
}
