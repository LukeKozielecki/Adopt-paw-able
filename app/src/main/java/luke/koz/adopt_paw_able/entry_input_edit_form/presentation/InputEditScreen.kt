package luke.koz.adopt_paw_able.entry_input_edit_form.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import luke.koz.adopt_paw_able.R
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

@Composable
fun AnimalInputForm(
    modifier: Modifier = Modifier,
    categoryMapping: Map<Int, String>,
    dynamicUpdate: (String, Int) -> Unit,
    updatedBreedBasedOnCategory: ((Int) -> Unit)?,
    animalBreedMap : Map<Int, String>,
    animalAgeMap : Map<Int, String>,
    animalGenderMap : Map<Int, String>,
    animalSterilizedMap : Map<Int, String>,
    animalHouseTrainedMap : Map<Int, String>,
    animalDeclawedMap : Map<Int, String>,
    animalFreeMap : Map<Int, String>
) {
    InputDropdown(
        modifier = modifier,
        title = "Category",
        categoryMapping = categoryMapping,
        updatedBreedBasedOnCategory = updatedBreedBasedOnCategory,
        dynamicUpdate = dynamicUpdate
    )
    InputDropdown(
        modifier = modifier,
        title = "Breed",
        categoryMapping = animalBreedMap,
        dynamicUpdate = dynamicUpdate
    )
    InputDropdown(
        modifier = modifier,
        title = "Age",
        categoryMapping = animalAgeMap,
        dynamicUpdate = dynamicUpdate
    )

    InputDropdown(
        modifier = modifier,
        title = "Gender",
        categoryMapping = animalGenderMap,
        dynamicUpdate = dynamicUpdate
    )

    InputDropdown(
        modifier = modifier,
        title = "Sterilization",
        categoryMapping = animalSterilizedMap,
        dynamicUpdate = dynamicUpdate
    )
    InputDropdown(
        modifier = modifier,
        title = "House Trained",
        categoryMapping = animalHouseTrainedMap,
        dynamicUpdate = dynamicUpdate
    )

    InputDropdown(
        modifier = modifier,
        title = "Declawed",
        categoryMapping = animalDeclawedMap,
        dynamicUpdate = dynamicUpdate
    )

    InputDropdown(
        modifier = modifier,
        title = "Free",
        categoryMapping = animalFreeMap,
        dynamicUpdate = dynamicUpdate
    )
}

@Composable
fun AnimalInputNumeralForm(modifier: Modifier = Modifier, placeholder: String, takeUserTypedInput : (String) -> Unit) {
    var textFieldValue by remember {
        mutableStateOf("")
    }
    Row (
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ){
        TextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                takeUserTypedInput(textFieldValue)
                            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(text = placeholder) },
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDropdown(
    modifier: Modifier = Modifier,
    title: String,
    categoryMapping : Map<Int, String>,
    dynamicUpdate : ((String, Int) -> Unit),
    updatedBreedBasedOnCategory : ((Int) -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier
        .wrapContentSize(Alignment.TopStart)
        .padding(vertical = 8.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(text = title)
        Row (modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.weight(1f))
            ExposedDropdownMenuBox(
                modifier = modifier,
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    readOnly = true,
                    placeholder = { Text(text = "Select animal ${title.lowercase()}") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categoryMapping.forEach { (categoryId, categoryName) ->
                        DropdownMenuItem(
                            text = { Text(text = categoryName) },
                            onClick = {
                                dynamicUpdate(title, categoryId)
                                updatedBreedBasedOnCategory?.invoke(categoryId)
                                textFieldValue = categoryName
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = {
                    textFieldValue = ""
                    dynamicUpdate(title, 0)
                },
                modifier = Modifier
                    .size(34.dp)
                    .fillMaxSize(), shape = CircleShape, contentPadding = PaddingValues(8.dp),
            ) {
                Image(painter = painterResource(id = R.drawable.baseline_clear_24), contentDescription = "Clear selection")

            }
            Spacer(modifier = Modifier.weight(1f))
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