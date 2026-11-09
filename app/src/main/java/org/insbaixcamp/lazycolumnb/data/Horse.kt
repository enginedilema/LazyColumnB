package org.insbaixcamp.lazycolumnb.data

import com.google.firebase.firestore.DocumentId


data class Horse(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var age: Int = 0,
    var race: String = "",
    var weight: Double = 100.0,
    var topSpeed: Double = 40.0,
    val createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = System.currentTimeMillis()
)