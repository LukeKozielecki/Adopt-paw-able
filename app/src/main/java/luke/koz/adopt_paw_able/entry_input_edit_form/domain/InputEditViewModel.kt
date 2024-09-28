package luke.koz.adopt_paw_able.entry_input_edit_form.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import luke.koz.adopt_paw_able.animals.domain.model.AnimalEntry
import luke.koz.adopt_paw_able.utils.DecodeServerResponse

sealed class InputUiState {
    data object ValidInput : InputUiState()
    data object InvalidInput : InputUiState()
}
class InputEditViewModel : ViewModel(), DecodeServerResponse {

//    private val _categoryMapping = MutableLiveData<Map<Int, String>>()
//    private val _animalBreedMapping = MutableLiveData<Map<Int, String>>()
//
//    val categoryMapping : Map<Int, String> = InputEditViewModel().categoryMappings
//    val animalBreedMapping : Map<Int, String> = InputEditViewModel().breedMappings[0] ?: InputEditViewModel().categoryMappings

    private val _animalCategory = MutableStateFlow<Int> (0)

    val animalCategory = _animalCategory.asStateFlow()

    private val _animalBreedMap = MutableStateFlow<Int> (0)

    val animalBreedMap = _animalBreedMap.asStateFlow()

    fun updatedBreedBasedOnCategory (categoryId: Int) : Unit {
//        when (categoryId) {
//            1-> {_selectedAnimalSpecie.value = 1}
//            2 -> {_selectedAnimalSpecie.value = 2}
//            3 -> {_selectedAnimalSpecie.value = 3}
//            4 -> {_selectedAnimalSpecie.value = 4}
//            5 -> {_selectedAnimalSpecie.value = 5}
//            else -> {_selectedAnimalSpecie.value = 0}
//        }
        _animalBreedMap.value = categoryId
        Log.d("selectedAnimalSpecie.value","viewModel.selectedAnimalSpecie.value = ${animalBreedMap.value}, viewModel._selectedAnimalSpecie.value = ${_animalBreedMap.value}")
    }


    private val _animalBreed = MutableStateFlow<Int> (0)

    val animalBreed = _animalBreed.asStateFlow()


    private val _animalSterilized = MutableStateFlow<Boolean?> (false)

    val animalSterilized = _animalSterilized.asStateFlow()

    private val _animalBio = MutableStateFlow<String> ("")
    fun setAnimalBriefBio(string: String){
        _animalBio.value = string
        updateCurrentAnimal()
    }

    private val _animalPrice = MutableStateFlow<String> ("")
    fun setAnimalPrice(string: String){
        _animalPrice.value = string
        updateCurrentAnimal()
    }

    private val _animalPhotoUrl = MutableStateFlow<String> ("")
    fun setAnimalPhotoUrl(string: String){
        _animalPhotoUrl.value = string
        updateCurrentAnimal()
    }
    fun dynamicUpdate (title : String?, selectedInteger: Int) {
        when (title) {
            "Category" -> {_animalCategory.value = selectedInteger}
            "Breed" -> {_animalBreed.value = selectedInteger}
            "Age" -> {_animalAge.value = selectedInteger}
            "Gender" -> {_animalGender.value = selectedInteger}
            //weight input
            "Sterilization" -> {if(selectedInteger == 2) _animalSterilized.value = true else if (selectedInteger == 1) _animalSterilized.value = false else _animalSterilized.value = null }
            "Required Maintenance" -> {_requiredMaintenance.value = selectedInteger}
            "House Trained" -> {
                _animalHouseTrained.value = selectedInteger>0
            }
            "Vaccinated" -> {_animalVaccinated.value = selectedInteger}
            //brief bio input
            "Declawed" -> {_animalDeclawed.value = selectedInteger}
            "Free" -> {_animalFree.value = selectedInteger>0}
            //price input
            //photo url input
            else -> {}
        }
        updateCurrentAnimal()
    }

    /**
     * Holds an integer representing the age of the animal as per [ageMap]
     */
    private val _animalAge = MutableStateFlow<Int?>(null)
    val animalAge: StateFlow<Int?> = _animalAge.asStateFlow()

    /**
     * Gender is represented by ints: (0=Not Selected, 1=Male, 2=Female, 3=unisex for hyenas & some fish)
     */
    private val _animalGender = MutableStateFlow<Int>(0)
    val animalGender: StateFlow<Int> = _animalGender.asStateFlow()

    /**
     * Vaccinated is represented by ints: (0=Not Selected, 1=not vaccinated, 2=vaccinated)
     */
    private val _isVaccinated = MutableStateFlow<Int>(0)
    val isVaccinated: StateFlow<Int> = _isVaccinated.asStateFlow()

    /**
     * Declawed status is represented by integers (0=Not Selected, 1=not declawed, 2=declawed)
     */
    private val _isDeclawed = MutableStateFlow<Int>(0)
    val isDeclawed: StateFlow<Int> = _isDeclawed.asStateFlow()

    private val _requiredMaintenance = MutableStateFlow<Int>(0)
    val requiredMaintenance: StateFlow<Int> = _requiredMaintenance.asStateFlow()

    private val _animalMaintenance = MutableStateFlow<Int>(0)
    val animalMaintenance = _animalMaintenance.asStateFlow()

    private val _animalHouseTrained = MutableStateFlow<Boolean?>(null)
    val animalHouseTrained = _animalHouseTrained.asStateFlow()

    private val _animalVaccinated = MutableStateFlow<Int>(0)
    val animalVaccinated = _animalVaccinated.asStateFlow()

    private val _animalDeclawed = MutableStateFlow<Int>(0)
    val animalDeclawed = _animalDeclawed.asStateFlow()

    private val _animalFree = MutableStateFlow<Boolean>(false)
    val animalFree = _animalFree.asStateFlow()

    /**
     * the idea behind this variable is to store current animal inside of it. and after filtering
     */
    private val _currentAnimal = MutableStateFlow<AnimalEntry>(
        AnimalEntry(
            id = 0,
            category = _animalCategory.value,
            breed = _animalBreed.value,
            age = _animalAge.value,
            gender = null,//_animalGender.value, todo change this in database to int coz geckos
            isNeutered = _animalSterilized.value,
            location = null,
            weight = null,
            requiredMaintenance = _requiredMaintenance.value,
            isHouseTrained = _animalHouseTrained.value,
            isVaccinated = _isVaccinated.value,
            briefBio = _animalBio.value,
            isDeclawed = _animalDeclawed.value,
            isFree = _animalFree.value,
            price = _animalPrice.value.toFloatOrNull(),
            photoURL = _animalPhotoUrl.value
        )
    )
    val currentAnimal = _currentAnimal

    private fun updateCurrentAnimal() {
        _currentAnimal.value = AnimalEntry(
            id = 0,
            category = _animalCategory.value,
            breed = _animalBreedMap.value,
            age = animalAge.value,
            gender = null,
            isNeutered = _animalSterilized.value,
            location = null,
            weight = null,
            requiredMaintenance = _requiredMaintenance.value,
            isHouseTrained = null,
            isVaccinated = _isVaccinated.value,
            briefBio = null,
            isDeclawed = _isDeclawed.value,
            isFree = false,
            price = null,
            photoURL = null
        )
    }

    fun commitAnimal (){
        printLogsCommitAnimal()
    }

    private fun printLogsCommitAnimal(){
        val animal = _currentAnimal.value
        Log.d("animalEntry", "ID: ${animal.id}")
        Log.d("animalEntry", "Category: ${animal.category}")
        Log.d("animalEntry", "Breed: ${animal.breed}")
        Log.d("animalEntry", "Age: ${animal.age}")
        Log.d("animalEntry", "Gender: ${animal.gender}")
        Log.d("animalEntry", "Is Neutered: ${animal.isNeutered}")
        Log.d("animalEntry", "Location: ${animal.location}")
        Log.d("animalEntry", "Weight: ${animal.weight}")
        Log.d("animalEntry", "Required Maintenance: ${animal.requiredMaintenance}")
        Log.d("animalEntry", "Is House Trained: ${animal.isHouseTrained}")
        Log.d("animalEntry", "Is Vaccinated: ${animal.isVaccinated}")
        Log.d("animalEntry", "Brief Bio: ${animal.briefBio}")
        Log.d("animalEntry", "Is Declawed: ${animal.isDeclawed}")
        Log.d("animalEntry", "Is Free: ${animal.isFree}")
        Log.d("animalEntry", "Price: ${animal.price}")
        Log.d("animalEntry", "Photo URL: ${animal.photoURL}")
    }

    // todo init reset to
    init {
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                InputEditViewModel()
            }
        }
    }
}