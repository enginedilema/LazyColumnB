package org.insbaixcamp.lazycolumnb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.insbaixcamp.lazycolumnb.data.Horse
import org.insbaixcamp.lazycolumnb.ui.theme.LazyColumnBTheme

class MainActivity : ComponentActivity() {
    val TAG:String = "MainActivity"
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
    val TAG:String = "GreetingComponent"
    val db = Firebase.firestore
    val horses = ArrayList<Horse>()
    val horseLuis =
        Horse(name = "Horse Luis",
            age = 5,
            race = "Andaluz",
            weight = 400.0,
            topSpeed = 20.0)

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
            for (document in result) {
                val caball: Horse = document.toObject(Horse::class.java)
                horses.add(caball)
                Log.i(TAG, "${document.id} => ${document.data}")
            }
        }



    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    LazyColumn() { }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LazyColumnBTheme {
        Greeting("Android")
    }
}