package org.insbaixcamp.lazycolumnb.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.insbaixcamp.lazycolumnb.Greeting
import org.insbaixcamp.lazycolumnb.data.Horse
import org.insbaixcamp.lazycolumnb.ui.theme.LazyColumnBTheme

@Composable
fun HorseItem(horse: Horse, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = horse.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Speed: ${horse.topSpeed} km/h",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column {
                Text(
                    text = horse.breed,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Weight: ${horse.weight} kg",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column {
                Button(onClick = {
                    val db = Firebase.firestore
                    db.collection("horses").
                        document(horse.id).delete()
                }) {
                    Text(text = "Delete")
                }

            }
        }
    }
}

@Preview
@Composable
fun HorseItemPreview(){
    LazyColumnBTheme {
        val horse = Horse(
            name = "Horse Demo",
            age = 5,
            breed = "Andaluz",
            weight = 400.0,
            topSpeed = 20.0
        )
        HorseItem(horse)
    }
}