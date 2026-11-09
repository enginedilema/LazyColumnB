package org.insbaixcamp.lazycolumnb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.insbaixcamp.lazycolumnb.data.Horse
import org.insbaixcamp.lazycolumnb.ui.components.HorseItem
import org.insbaixcamp.lazycolumnb.ui.theme.LazyColumnBTheme

private const val TAG = "GreetingComponent"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyColumnBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val db = Firebase.firestore
    var horses by remember { mutableStateOf<List<Horse>>(emptyList()) }

    LaunchedEffect(Unit) {
        val horseLuis = Horse(
            name = "Horse Luis",
            age = 5,
            breed = "Andaluz",
            weight = 400.0,
            topSpeed = 20.0
        )

        db.collection("horses")
            .add(horseLuis)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        db.collection("horses").get()
            .addOnSuccessListener { result ->
                val fetchedHorses = result.mapNotNull { document ->
                    document.toObject(Horse::class.java)
                }
                horses = fetchedHorses
                Log.i(TAG, "Loaded ${fetchedHorses.size} horses")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error fetching horses", e)
            }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(16.dp)
        )

        if (!horses.isEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = horses,
                    key = { horse -> horse.id }
                ) { horse ->
                    HorseItem(horse = horse)
                }
            }
        } else {
            Text(
                text = "No horses found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LazyColumnBTheme {
        Greeting("Android")
    }
}
