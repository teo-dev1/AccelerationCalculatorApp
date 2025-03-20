package com.example.punchspeedcalculator.repo

import com.example.punchspeedcalculator.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UsersRepository(
    private val firestoreInstance: FirebaseFirestore
) {
    suspend fun getAllUsers(): List<User> {
        val querySnapshot = firestoreInstance
            .collection("Users")
            .get()
            .await()
        return querySnapshot.documents.mapNotNull{
            it.toObject(User::class.java)
        }
    }

    suspend fun addUser(user: User){
        firestoreInstance
            .collection("Users")
            .add(user)
            .await()
    }

    suspend fun deleteUser(userId: String){
        firestoreInstance
            .collection("Users")
            .document(userId)
            .delete()
            .await()
    }

    suspend fun getSpecificUser(userId: String): User? {
        return firestoreInstance
            .collection("Users")
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java)
    }

//private val usersCollection=instance.collection("Users")
}