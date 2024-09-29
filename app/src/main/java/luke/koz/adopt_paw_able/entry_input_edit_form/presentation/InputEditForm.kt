package luke.koz.adopt_paw_able.entry_input_edit_form.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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
