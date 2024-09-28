package luke.koz.adopt_paw_able.animals.domain.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luke.koz.adopt_paw_able.animals.domain.model.AnimalEntry
import luke.koz.adopt_paw_able.utils.DecodeServerResponse
import java.io.IOException

sealed class AnimalListState {
    object Loading : AnimalListState()
    data class Success(val animals: List<AnimalEntry>) : AnimalListState()
    data class Error(val message: String) : AnimalListState()
}
class AnimalsViewModel : ViewModel(), DecodeServerResponse {
    private val supabase = createSupabaseClient(
        supabaseUrl = "https://qsdihmtmasiosykepcwm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFzZGlobXRtYXNpb3N5a2VwY3dtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjY2NjMyMDYsImV4cCI6MjA0MjIzOTIwNn0.ygESpL0irLfa8o3PAvsDTSqtNKVj_sp33bm5k0HlOso"
    ) {
        install(Postgrest)
    }

    /** The mutable State that stores the status of the most recent request */
    var animalsListUiState: AnimalListState by mutableStateOf(AnimalListState.Loading)
        private set

    private val _animals = mutableStateListOf<AnimalEntry>()
    val animals: List<AnimalEntry> = _animals

    init {
        setAnimalUiState()
    }

    private fun setAnimalUiState(){
        animalsListUiState = AnimalListState.Loading
        fetchAnimals()
        animalsListUiState = try {
            AnimalListState.Success(animals = _animals)
        } catch (e: IOException) {
            AnimalListState.Error("")
        } /*catch (e: HttpException) {
            AnimalListState.Error("")
        } */catch (e: Exception) {
            AnimalListState.Error("")
        }
    }
    private fun fetchAnimals() {
        viewModelScope.launch(Dispatchers.IO) {
            //todo setup exception handling
            // when no internet connection, this function when called throws exception here,
            // calling try catch didn't give expected result of skipping this code
            val results = supabase.from("animals_table").select().decodeList<AnimalEntry>()
            _animals.addAll(results)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AnimalsViewModel()
            }
        }
    }
}