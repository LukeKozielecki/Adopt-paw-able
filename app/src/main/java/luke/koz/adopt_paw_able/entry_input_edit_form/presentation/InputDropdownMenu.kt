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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import luke.koz.adopt_paw_able.R


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
