package luke.koz.adopt_paw_able

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luke.koz.adopt_paw_able.animals.domain.model.AnimalEntry
import luke.koz.adopt_paw_able.ui.theme.AdoptpawableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdoptpawableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Text(text = "Placeholder_text")
                        AnimalsList()
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalsList() {
    //var animals by remember { mutableStateOf<List<AnimalEntry>>(listOf()) }
    val animals = remember { mutableStateListOf<AnimalEntry>() }
    LaunchedEffect(Unit) {
        val supabase = createSupabaseClient(
            supabaseUrl = "https://qsdihmtmasiosykepcwm.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFzZGlobXRtYXNpb3N5a2VwY3dtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjY2NjMyMDYsImV4cCI6MjA0MjIzOTIwNn0.ygESpL0irLfa8o3PAvsDTSqtNKVj_sp33bm5k0HlOso"
        ) {
            install(Postgrest)
        }
        withContext(Dispatchers.IO) {
            val results = supabase.from("animals_table").select().decodeList<AnimalEntry>()
            animals.addAll(results)
        }
    }
    Text(text = "animals list size :${animals.size}")
    LazyColumn {
        items(animals.size) {
            Text(text = animals[it].id.toString())
            Text(text = "animals[0].id: "+animals[0].id.toString())
        }
    }
}