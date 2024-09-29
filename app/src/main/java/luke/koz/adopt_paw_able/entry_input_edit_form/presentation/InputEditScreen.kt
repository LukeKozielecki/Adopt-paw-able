package luke.koz.adopt_paw_able.entry_input_edit_form.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import luke.koz.adopt_paw_able.entry_input_edit_form.domain.InputEditViewModel
import luke.koz.adopt_paw_able.entry_input_edit_form.domain.InputUiState
import luke.koz.adopt_paw_able.ui.theme.AdoptpawableTheme

@Composable
fun InputEditScreen(
    modifier: Modifier = Modifier,
    viableInput: InputUiState,
) {
    val viewModel: InputEditViewModel = viewModel<InputEditViewModel>()
    val categoryMapping : Map<Int, String> = viewModel.categoryMap
    val selectedAnimalSpecie = viewModel.animalBreedMap.collectAsState()
    val animalBreedMapping = viewModel.breedMap[selectedAnimalSpecie.value]
        ?: viewModel.emptyMap
    val animalAgeMapping = viewModel.ageMap
    val animalGenderMapping = viewModel.genderMap
    val animalSterilizedMap = viewModel.animalSterilizedMap
    val animalHouseTrainedMap = viewModel.animalHouseTrainedMap
    val animalDeclawedMap = viewModel.animalDeclawedMap
    val animalFreeMap = viewModel.animalFreeMap
    Column (
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AnimalInputForm(
            categoryMapping = categoryMapping,
            dynamicUpdate = viewModel::dynamicUpdate,
            updatedBreedBasedOnCategory = viewModel::updatedBreedBasedOnCategory,
            animalBreedMap = animalBreedMapping,
            animalAgeMap = animalAgeMapping,
            animalGenderMap = animalGenderMapping,
            animalSterilizedMap = animalSterilizedMap,
            animalDeclawedMap = animalDeclawedMap,
            animalFreeMap = animalFreeMap,
            animalHouseTrainedMap = animalHouseTrainedMap
        )
        AnimalInputNumeralForm(placeholder = "Brief bio", takeUserTypedInput = viewModel::setAnimalBriefBio)
        AnimalInputNumeralForm(placeholder = "Price", takeUserTypedInput = viewModel::setAnimalPrice)
        AnimalInputNumeralForm(placeholder = "Photo Url", takeUserTypedInput = viewModel::setAnimalPhotoUrl)

        Button(
            onClick = {
                /*todo implement add animal*/
                viewModel.commitAnimal()
//                viewModel.mockDebugFun(1)
            },
            enabled = viableInput==InputUiState.ValidInput,
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Save")
        }
    }

}

@Preview (showBackground = true)
@Composable
private fun InputEditPreview() {
    AdoptpawableTheme {
        InputEditScreen(viableInput = InputUiState.ValidInput)
    }
}